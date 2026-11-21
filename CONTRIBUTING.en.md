# Contributing

[中文](CONTRIBUTING.md)

## What to submit

- Mod updates: include the new file name, SHA-1 and source link; `mods-manifest.json` and `pack-sources.json` are updated together
- Config fixes: state the changed keys, the in-game behaviour and how it was verified
- GroovyScript changes: state the recipe or trait involved and the in-game verification result
- Resource packs and languages: state the source and license; assets under an incompatible license are not accepted
- Documentation fixes: typos, stale version numbers, dead links

## Flow

1. Open an issue first with environment details and a log excerpt.
2. In the pull request, list the changed files and how they were verified in game.
3. When mods are added or removed, update `mods-list.txt`, `mods-manifest.json`, `pack-sources.json` and `docs/MODS.md` together.

## Not accepted

- Bulk formatting that only changes wording
- Assets without a stated source or license
- Features unrelated to the pack's goal
