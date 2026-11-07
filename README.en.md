# Craftsman Nostalgia

[中文](README.md)

A Minecraft 1.12.2 Cleanroom modpack for experiencing high-version content, with Tinkers' Antique (Tinkers' Construct 2) as the core gameplay. Aimed at players who want to stay on Tinkers' Construct 2 while experiencing modern blocks, mobs, villages and Nether content.  
The pack replaces unmaintained or outdated mods with maintained branches and modern optimizations.

## Mod list

The full categorized list is in [MODS.md](docs/MODS.en.md), covering loader and libraries, Tinkers' Construct, backports, farming and food, world and adventure, interface and information, visuals and audio, optimization and fixes, and others, each with version and role.

## Repository layout

- `overrides/config/` mod configuration, limited to the 62 files the pack changes; defaults generated at first launch are not stored
- `overrides/groovy/` GroovyScript scripts
- `overrides/resourcepacks/CraftsmanNostalgia.zip` resource pack
- `mods-list.txt`, `mods-manifest.json` mod list and hashes
- `pack-sources.json` download URLs and hashes for mods and the shader pack
- `tools/build_pack.py` packaging script
- The shader pack is referenced by URL and hash in `pack-sources.json` rather than stored here; see [NOTICE.en.md](NOTICE.en.md) for the Mellow 3.5 source
- Migration board from Cleanroom to Outlands:  
  [Cleanroom to Outlands](https://github.com/users/FCLCQ/projects/1)

## Installation

Requires Minecraft 1.12.2 and Cleanroom 0.6.13.  
Supported launchers are listed in [LAUNCHERS.md](docs/LAUNCHERS.en.md).

1. Download the `.mrpack` from [Releases](https://github.com/FCLCQ/craftsman-nostalgia/releases) and import it in your launcher. `modrinth.index.json` lists 92 mods plus Mellow 3.5 by URL, and `client-overrides/` holds the configuration, scripts and resource pack; `teamglowing`, `TAIGARR` and `clearmybackground` are not included, see "Mods not bundled" below.
2. Switch the instance to Cleanroom 0.6.13 after import. An mrpack `dependencies` block can only declare `minecraft`, so Cleanroom is not installed automatically.
3. Build manually: create a 1.12.2 Cleanroom instance, install every mod in `mods-list.txt`, then copy the directories under `overrides/` into the instance root.

## Local packaging

```bash
python3 tools/build_pack.py --mods /path/to/instance/mods --out dist --version 0.6.135
```

Produces `dist/CraftsmanNostalgia-<version>.mrpack` with the same layout as the launcher export; the internal name stays `[CRL]工匠怀古` while the file name is ASCII so it can be attached to a Release. Mods and the shader pack go into `files` in `modrinth.index.json`, with URLs and hashes from `pack-sources.json`; mods missing from that file are left out by default; add `--with-offline` to verify them against the manifest SHA-1 in the `--mods` directory and embed them under `client-overrides/mods/`. Configuration, scripts and resource packs come from git-tracked files only, so `splash.properties`, `shaders.properties`, `cleanroom_load_timings.dat` and `*.dat` caches stay out of the archive. Add `--format zip` for a plain archive. The archive places `LICENSE`, both language versions of the third-party notice and the license texts from `docs/licenses/` in the instance root and under `licenses/`.

## Mods not bundled

The following mods have no CurseForge or Modrinth project page. Obtain them separately.

| Mod | Role |
|---|---|
| teamglowing | Team glowing display |
| TAIGARR | Tinkers alloying addon |
| clearmybackground | Clear GUI background |

## Compatibility

Fugue 0.24.4, Pulsar 0.6.2, Celeritas Extra 0.9.3  
cause a startup crash when updated; keep the current versions.

## Scripts

`overrides/groovy/` is split into `preInit/`, `init/` and `postInit/`. Run `/gs check`, then `Ctrl + R` or `/gs reload` for a hot reload; material registration in `init/` cannot be reloaded and requires a client restart.

## License

Scripts, tools and documentation written by the author are MIT licensed (see `LICENSE`). The resource pack is CC BY-SA 4.0; see [NOTICE.en.md](NOTICE.en.md) for third-party sources and terms.

Pack author: 枫川瑠初晴/FCLCQ(KH)  
Development assistance: DeepSeek Harness  
Mods and resource packs remain the property of their respective authors.
