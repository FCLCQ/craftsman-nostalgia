# Launcher compatibility

The pack uses a custom version JSON with Cleanroom 0.6.13 and requires Java 25 and LWJGL3. A launcher must support importing a custom version with `libraries` and `mainClass`, selecting a Java 25 path, and disabling game file integrity checks.

| Launcher | Support | Notes |
|---|---|---|
| FCL | Supported | Starts through `caciocavallo17` and `mio.Wrapper`; verified on Android into a world |
| ZL 2 | Supported | Uses the same Pojav core as FCL and bundles a Java runtime |
| HMCL | Supported | Imports MMC archives and custom version JSON; a Java 25 path can be set |
| PCL 2 | Not supported | Cannot import the Outlands MMC archive |
| Prism | Supported | Import the MMC instance archive and point Java at 25; handles LWJGL itself |
| MultiMC | Supported | Same as Prism; Java 25 must be installed manually |
| Modrinth App | Not supported | Only installs standard Forge, Fabric, NeoForge and Quilt setups |
| CurseForge App | Not supported | Same limitation; custom loaders are not recognised |
| Official launcher | Not supported | Cannot load custom main classes or Cleanroom libraries |
| PojavLauncher | Untested | Supports custom versions and Java runtimes |

On Android, FCL is recommended and ZalithLauncher 2 also works;
on desktop, HMCL is recommended, with Prism Launcher and MultiMC also working.
