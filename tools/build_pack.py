#!/usr/bin/env python3
"""打包工匠怀古整合包为启动器可导入的 mrpack。

结构与启动器导出的 0.6.135 包一致：模组与光影写在 modrinth.index.json 的
files 里，用 pack-sources.json 记录的下载地址与哈希；没有公开下载来源的
模组放进 client-overrides/mods/；配置、脚本与资源包放进 client-overrides/。

用法:
    python3 tools/build_pack.py --out dist
    python3 tools/build_pack.py --mods /path/to/instance/mods --out dist --version 0.6.135
    python3 tools/build_pack.py --format zip --out dist
"""
import argparse
import hashlib
import json
import os
import subprocess
import sys
import zipfile

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
OVERRIDES = os.path.join(ROOT, "overrides")
PACK_DIRS = ("config", "groovy", "resourcepacks", "shaderpacks")

# 首次启动自动重建的文件，不进入分发包。
EXCLUDED_NAMES = {"splash.properties", "shaders.properties", "cleanroom_load_timings.dat"}
EXCLUDED_SUFFIXES = (".dat",)


def sha256(path):
    h = hashlib.sha256()
    with open(path, "rb") as f:
        for chunk in iter(lambda: f.read(1 << 20), b""):
            h.update(chunk)
    return h.hexdigest()


def sha1(path):
    h = hashlib.sha1()
    with open(path, "rb") as f:
        for chunk in iter(lambda: f.read(1 << 20), b""):
            h.update(chunk)
    return h.hexdigest()


def load_json(path):
    with open(path, encoding="utf-8") as f:
        return json.load(f)


# 固定时间戳，保证同一输入产出相同的 sha256。
ZIP_TIME = (1980, 1, 1, 0, 0, 0)


def add_file(zf, path, arcname):
    info = zipfile.ZipInfo(arcname.replace(os.sep, "/"), ZIP_TIME)
    info.compress_type = zipfile.ZIP_DEFLATED
    info.external_attr = 0o644 << 16
    with open(path, "rb") as f:
        zf.writestr(info, f.read())


def add_bytes(zf, arcname, data):
    info = zipfile.ZipInfo(arcname, ZIP_TIME)
    info.compress_type = zipfile.ZIP_DEFLATED
    info.external_attr = 0o644 << 16
    zf.writestr(info, data)


# 分发包内的许可文件，来自仓库根目录与 docs/licenses。
LICENSE_BUNDLE = (
    ("LICENSE", "LICENSE"),
    ("NOTICE.md", "THIRD-PARTY-NOTICES.md"),
    ("NOTICE.en.md", "THIRD-PARTY-NOTICES.en.md"),
)


def collect_licenses():
    entries = []
    for src, dst in LICENSE_BUNDLE:
        path = os.path.join(ROOT, src)
        if not os.path.exists(path):
            sys.exit(f"缺少许可文件: {path}")
        entries.append((path, dst))
    lic_dir = os.path.join(ROOT, "docs", "licenses")
    if os.path.isdir(lic_dir):
        for name in sorted(os.listdir(lic_dir)):
            path = os.path.join(lic_dir, name)
            if os.path.isfile(path):
                entries.append((path, "licenses/" + name))
    return entries


def is_runtime_file(rel):
    name = os.path.basename(rel)
    return name in EXCLUDED_NAMES or name.endswith(EXCLUDED_SUFFIXES)


def tracked_overrides():
    """用 git 跟踪列表确定要进入分发包的文件，避免把运行时产物打进去。"""
    try:
        out = subprocess.run(
            ["git", "-C", ROOT, "ls-files", "-z", "--"] + [f"overrides/{d}" for d in PACK_DIRS],
            check=True, capture_output=True).stdout.decode()
    except (OSError, subprocess.CalledProcessError):
        return None
    files = [p for p in out.split("\0") if p]
    return [(os.path.join(ROOT, p), os.path.relpath(os.path.join(ROOT, p), OVERRIDES)) for p in files]


def walk_overrides():
    files = []
    for dirname in PACK_DIRS:
        base_dir = os.path.join(OVERRIDES, dirname)
        if not os.path.isdir(base_dir):
            continue
        for base, _dirs, names in os.walk(base_dir):
            for name in names:
                path = os.path.join(base, name)
                files.append((path, os.path.relpath(path, OVERRIDES)))
    return files


def collect_overrides():
    files = tracked_overrides()
    if files is None:
        files = walk_overrides()
    entries = [(p, rel) for p, rel in files if not is_runtime_file(rel)]
    skipped = [rel for _p, rel in files if is_runtime_file(rel)]
    if not entries:
        sys.exit("overrides 目录没有可打包内容")
    return entries, skipped


def collect_embedded(mods_dir, manifest, sources):
    """没有公开下载地址的模组必须提供实例目录，按清单校验后嵌入 overrides。"""
    sourced = {e["path"][len("mods/"):] for e in sources if e["path"].startswith("mods/")}
    embedded = [m for m in manifest.get("mods", []) if m["file"] not in sourced]
    if not embedded:
        return []
    if not mods_dir:
        sys.exit("清单里存在没有下载来源的模组，需要 --mods 指向实例的 mods 目录: "
                 + "、".join(m["file"] for m in embedded))
    if not os.path.isdir(mods_dir):
        sys.exit(f"模组目录不存在: {mods_dir}")
    entries = []
    for m in embedded:
        path = os.path.join(mods_dir, m["file"])
        if not os.path.exists(path):
            sys.exit(f"缺少模组文件: {path}")
        if m.get("sha1") and sha1(path) != m["sha1"]:
            sys.exit(f"模组哈希与清单不符: {m['file']}")
        entries.append((path, "mods/" + m["file"]))
    return entries


def build_mrpack(args, manifest, sources):
    source_files = sources.get("files", [])
    sourced_mods = {e["path"][len("mods/"):] for e in source_files if e["path"].startswith("mods/")}
    overrides, skipped = collect_overrides()
    licenses = collect_licenses()
    embedded = collect_embedded(args.mods, manifest, source_files) if args.with_offline else []
    embedded_names = {rel[len("mods/"):] for _path, rel in embedded}
    unmapped = sorted({m["file"] for m in manifest.get("mods", [])} - sourced_mods)

    index = {
        "formatVersion": 1,
        "game": "minecraft",
        "versionId": args.version,
        "name": sources.get("name", "[CRL]工匠怀古"),
        "summary": sources.get("summary", ""),
        "dependencies": {"minecraft": sources.get("minecraft", "1.12.2")},
        "files": source_files,
    }

    os.makedirs(args.out, exist_ok=True)
    # GitHub Releases 附件名只接受 ASCII，内部 name 保留中文与方括号。
    pack_name = args.asset or f"CraftsmanNostalgia-{args.version}.mrpack"
    pack_path = os.path.join(args.out, pack_name)
    with zipfile.ZipFile(pack_path, "w", zipfile.ZIP_DEFLATED, compresslevel=9) as zf:
        add_bytes(zf, "modrinth.index.json", (json.dumps(index, ensure_ascii=False, indent=2) + "\n").encode())
        for path, rel in overrides:
            add_file(zf, path, "client-overrides/" + rel)
        for path, rel in licenses:
            add_file(zf, path, "client-overrides/" + rel)
        for path, rel in embedded:
            add_file(zf, path, "client-overrides/" + rel)

    print(f"输出: {pack_path} ({os.path.getsize(pack_path) / 1048576:.1f} MB)")
    print(f"files 条目: {len(source_files)}（模组 {len(sourced_mods)}、光影 "
          f"{sum(1 for e in source_files if e['path'].startswith('shaderpacks/'))}）")
    print(f"client-overrides: {len(overrides) + len(licenses)} 个文件（含许可 {len(licenses)}）"
          + (f"，内嵌模组 {len(embedded)} 个" if embedded else ""))
    if skipped:
        print("跳过的运行时文件：")
        for rel in sorted(skipped):
            print("  -", rel)
    if unmapped:
        print("没有下载来源、不进入分发包的模组：" if not embedded else "没有下载来源的模组：")
        for name in unmapped:
            print("  -", name)
    print(f"sha256: {sha256(pack_path)}")
    return pack_path


def build_zip(args, manifest):
    entries, skipped = collect_overrides()
    mod_entries = []
    if args.mods:
        if not os.path.isdir(args.mods):
            sys.exit(f"模组目录不存在: {args.mods}")
        known = {m["file"] for m in manifest.get("mods", [])}
        for name in sorted(os.listdir(args.mods)):
            if not name.endswith(".jar"):
                continue
            mod_entries.append((os.path.join(args.mods, name), "mods/" + name))
            if name not in known:
                print("清单未记录的 jar:", name)
        entries.extend(mod_entries)

    os.makedirs(args.out, exist_ok=True)
    pack_path = os.path.join(args.out, f"{args.name}-{args.version}.zip")
    with zipfile.ZipFile(pack_path, "w", zipfile.ZIP_DEFLATED, compresslevel=9) as zf:
        for path, rel in entries:
            add_file(zf, path, rel)
        for extra in ("README.md", "mods-list.txt", "mods-manifest.json"):
            src = os.path.join(ROOT, extra)
            if os.path.exists(src):
                add_file(zf, src, extra)
    print(f"输出: {pack_path} ({os.path.getsize(pack_path) / 1048576:.1f} MB)")
    print(f"覆盖文件: {len(entries) - len(mod_entries)}，模组: {len(mod_entries)}")
    if skipped:
        print("跳过的运行时文件：", "、".join(sorted(skipped)))
    print(f"sha256: {sha256(pack_path)}")


def main():
    parser = argparse.ArgumentParser(description="打包工匠怀古整合包")
    parser.add_argument("--mods", help="实例的 mods 目录，用于嵌入没有下载来源的模组")
    parser.add_argument("--out", default="dist", help="输出目录")
    parser.add_argument("--format", choices=("mrpack", "zip"), default="mrpack", help="输出格式")
    parser.add_argument("--asset", help="输出文件名，默认 CraftsmanNostalgia-<版本>.mrpack")
    parser.add_argument("--with-offline", action="store_true",
                        help="把没有下载来源的模组嵌入 client-overrides/mods/，默认不打包")
    parser.add_argument("--name", default="craftsman-nostalgia", help="zip 格式的包名前缀")
    parser.add_argument("--version", default="0.6.135", help="版本号")
    args = parser.parse_args()

    manifest = load_json(os.path.join(ROOT, "mods-manifest.json"))
    if args.format == "zip":
        build_zip(args, manifest)
        return
    sources = load_json(os.path.join(ROOT, "pack-sources.json"))
    build_mrpack(args, manifest, sources)


if __name__ == "__main__":
    main()
