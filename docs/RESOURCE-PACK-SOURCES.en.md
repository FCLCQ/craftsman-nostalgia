# Resource pack source audit

`overrides/resourcepacks/CraftsmanNostalgia.zip` includes only assets that are byte-identical to their source files. Counts cover entries under `assets/`.

## Totals

| Item | Count |
|---|---|
| Pack asset entries | 1837 |
| From ModernityExtra main pack (MIT) | 755 |
| From Backport Everything (CC BY-SA 4.0) | 1082 |
| Extra files | `pack.mcmeta`, `LICENSE.md`, `THIRD-PARTY-NOTICES.md` |

## Content

The retained assets cover textures and models for namespaces such as `minecraft`, `tconstruct`, `botania`, `twilightforest`, `taiga` and `conarm`, all copied unchanged from their sources. Backport Everything supplies the `minecraft` namespace with 1.14-style vanilla block, item, entity, GUI and model textures; the main-menu logo and panorama are excluded. Sounds, language files and `sounds.json` come from each mod's own assets and vanilla 1.12.2 resources.

## Planned additions

The following assets are planned for inclusion once permission is granted or a license-compatible replacement is found, to complete the visual, audio and language experience. They are not part of the current pack.

| Source | Author | Status |
|---|---|---|
| FFD Additional Resource Pack 1.1 | xy177 | No license file; permission pending |
| Modernity Adjunct | RuiXuqi and others | LGPL-2.1; permission or a compatible license pending |
| Fabric Chests | anner | GPL-3.0; permission or a compatible license pending |

Modernity-f3 3.10.3 is licensed LGPL-3.0-only, which is incompatible with the pack-wide CC BY-SA 4.0, so it is not included; Backport Everything provides the corresponding vanilla texture coverage.

## License

The pack as a whole is licensed under CC BY-SA 4.0, MIT assets keep their original license, and the included `LICENSE.md` and `THIRD-PARTY-NOTICES.md` carry the full terms. Vanilla Minecraft assets are copyright Mojang Studios. Redistribution requires attribution and share-alike.

## Maintenance

New assets must have a verified license and must either match the source byte-for-byte or document the rewrite. Sources without a license file, GPL, LGPL and CC BY-NC sources are not included until permission is granted or a compatible license is used.
