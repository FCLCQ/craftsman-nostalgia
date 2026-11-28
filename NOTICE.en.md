# Third-party asset notice

[中文](https://github.com/FCLCQ/craftsman-nostalgia/blob/main/NOTICE.md)

`overrides/resourcepacks/CNFullTextures-CC.zip` is the distributable merged pack currently used by the instance, released under CC BY-SA 4.0. Its `CREDITS.txt` lists attribution, and `LICENSES/MIT.txt` plus `LICENSES/CC-BY-SA-4.0.txt` carry the license texts and redistribution terms. Sources are Backport Everything 1.12-1.12.2 v1.8 (CC BY-SA 4.0) and the main, addition and fix subpacks of ModernityExtra (MIT); Modernity, the ModernityExtra adjunct subpack and the FFD add-on pack are not included.


`assets/futuremc/textures/block/netherite_block.png` is a compatibility texture for `ironchest-netherite`, taken from the same asset in Farmer's Future Delight; it is used only where that mod references `futuremc:block/netherite_block`.

## Included sources

| Resource pack | Author | License | Entries |
|---|---|---|---|
| ModernityExtra | RuiXuqi | MIT | 755 |
| Backport Everything 1.12-1.12.2 v1.8 | samfortyseven | CC BY-SA 4.0 | 1082 |

MIT assets keep their original license. CC BY-SA 4.0 assets require attribution and share-alike terms on redistribution. License texts are stored under `docs/licenses/`: `ModernityExtra-MIT.txt` and `Backport-Everything-CC-BY-SA-4.0.txt`. Part of Backport Everything comes from Qwuiblington's actually correct 1.14 texture backport. Vanilla Minecraft assets are copyright Mojang Studios and are distributed only for in-game display.

## Excluded assets

The following assets are not included because of incompatible or missing licenses, and there is no plan to add them; using them would require permission or a license-compatible replacement.

| Resource pack | Author | Reason |
|---|---|---|
| FFD Additional Resource Pack 1.1 (mc1.12.2) | xy177 | No license file |
| Modernity Adjunct fork | RuiXuqi and others | LGPL-2.1, incompatible with the pack-wide CC BY-SA 4.0 |
| Fabric Chests textures | anner | GPL-3.0, incompatible with the pack-wide CC BY-SA 4.0 |

Modernity-f3 3.10.3 is licensed LGPL-3.0-only, which is incompatible with the pack-wide CC BY-SA 4.0, so it is not included; Backport Everything provides the corresponding vanilla texture coverage.

## Shader pack

| Shader pack | Source | License |
|---|---|---|
| Mellow 3.5 | https://modrinth.com/shader/BUxf36AP | MIT |

Mellow 3.5 is not distributed in the repository; the launcher export includes it from the instance's `shaderpacks/`. SHA-1 `9c309a5170ae555990cf2adfde71c7ac755f560c` identifies that release; it is MIT-licensed, with the license text in `docs/licenses/Mellow-MIT.txt`. It is disabled by default; select it on the shaders page in video settings to enable it. Actinium provides the shader pipeline on the mod side.

## Mods embedded in the pack

The following mods are bundled as jars under `client-overrides/mods/`:

| Mod | Version | License | Source |
|---|---|---|---|
| Clear My Background | 1.0.4-1.12.2 | MIT | https://github.com/RuiXuqi/ClearMyBackground |
| Tinkers Alloying Addon Replant (TAIGARR) | beta-1.0.2 | GPL-3.0 | https://gitee.com/peter20050526/taigarr |
| Key Binding API shim | 1.3.3.3 | MIT (built for this pack, no Key Binding Patch code) | shipped with the pack |
| Tinkers' Evolution fixed build | 1.1.9 | MIT variant (Good, not Evil) | modified from https://github.com/phantamanta44/tinkers-evolution |

License texts for Clear My Background and TAIGARR are in `docs/licenses/ClearMyBackground-MIT.txt` and `docs/licenses/TAIGARR-GPL-3.0.txt`. TAIGARR is distributed under GPL-3.0; the corresponding source is available from its Gitee repository and the jar is unmodified. The Tinkers' Evolution build only changes integration injection and field references, documented on the project page.
