# 工匠怀古

[English](README.en.md)

在 Minecraft 1.12.2 上体验高版本内容的 Cleanroom 整合包，匠魂怀古(匠魂2)作为玩法核心。适用于希望继续留在匠魂2、同时想体验高版本方块、生物、村庄与下界内容的玩家。  
整合包使用维护的分支与新式优化，代替停止维护或已经过时的模组。

## 模组清单

完整分类清单见 [MODS.md](docs/MODS.md)，按加载器与前置、匠魂体系、向下移植、农业与食物、世界与冒险、界面与信息、视觉与音效、优化与修复、其他九类列出，每项包含版本与作用。

## 仓库内容

- `overrides/config/` 模组配置，只保留改动过的 62 个文件；首次启动由模组生成的默认配置不入库
- `overrides/groovy/` GroovyScript 脚本
- `overrides/resourcepacks/CraftsmanNostalgia.zip` 资源包
- `mods-list.txt`、`mods-manifest.json` 模组清单与哈希
- `pack-sources.json` 模组与光影的下载地址和哈希
- `tools/build_pack.py` 打包脚本
- 光影包不存原文件，`pack-sources.json` 里以下载地址与哈希引用；Mellow 3.5 来源见 [NOTICE.md](NOTICE.md)
- [从 Cleanroom 迁移至 Outlands](https://github.com/users/FCLCQ/projects/1)

## 安装

需要 Minecraft 1.12.2 与 Cleanroom 0.6.13。  
可用启动器见 [LAUNCHERS.md](docs/LAUNCHERS.md)。

1. 从 [Releases](https://github.com/FCLCQ/craftsman-nostalgia/releases) 下载 `.mrpack` 并导入启动器。`modrinth.index.json` 列出 92 个模组与 Mellow 3.5 的下载地址，`client-overrides/` 内含配置、脚本与资源包；`teamglowing`、`TAIGARR`、`clearmybackground` 不在包内，见下文「不随包分发的模组」。
2. 导入后把实例版本切到 Cleanroom 0.6.13。mrpack 的 `dependencies` 只能声明 `minecraft`，不会自动安装 Cleanroom。
3. 手动搭建：新建 1.12.2 Cleanroom 实例，按 `mods-list.txt` 安装模组，把 `overrides/` 下的目录复制到实例根目录。

## 本地打包

```bash
python3 tools/build_pack.py --mods /path/to/instance/mods --out dist --version 0.6.135
```

生成 `dist/CraftsmanNostalgia-<版本>.mrpack`，结构与启动器导出的包一致；包内名称仍是 `[CRL]工匠怀古`，文件名用 ASCII 以便作为 Releases 附件。模组与光影写进 `modrinth.index.json` 的 `files`，下载地址与哈希取自 `pack-sources.json`；该文件没有覆盖的模组默认不进入分发包，需要时加 `--with-offline`，脚本会从 `--mods` 指向的实例目录按 `mods-manifest.json` 的 SHA-1 校验后嵌入 `client-overrides/mods/`。配置、脚本与资源包只取 git 跟踪的文件，`splash.properties`、`shaders.properties`、`cleanroom_load_timings.dat` 与 `*.dat` 缓存不进入分发包。需要整体 zip 时加 `--format zip`。分发包把 `LICENSE`、第三方声明中英文两份与 `docs/licenses/` 下的许可原文放在实例根目录与 `licenses/`。

## 不随包分发的模组

以下模组没有 CurseForge 与 Modrinth 项目页，请自行获取。

| 模组 | 用途 |
|---|---|
| teamglowing | 队伍发光显示 |
| TAIGARR | 匠魂合金扩展 |
| clearmybackground | 清除界面背景 |

## 兼容性

Fugue 0.24.4、Pulsar 0.6.2、Celeritas Extra 0.9.3  
更新后会导致启动崩溃，保持当前版本。

## 脚本

`overrides/groovy/` 按 `preInit/`、`init/`、`postInit/` 分阶段。改完先 `/gs check`，再用 `Ctrl + R` 或 `/gs reload` 热重载；`init/` 里的材料注册无法热重载，需要重启客户端。

## 许可

作者编写的脚本、工具与文档采用 MIT 许可证，见 `LICENSE`。资源包采用 CC BY-SA 4.0，第三方材质来源与条款见 [NOTICE.md](NOTICE.md)。

整合包作者：枫川瑠初晴/FCLCQ(KH)  
开发协助：DeepSeek Harness  
模组与资源包版权归各自作者。
