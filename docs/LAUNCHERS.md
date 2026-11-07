# 启动器兼容性

整合包使用自定义版本 JSON 与 Cleanroom 0.6.13，运行需要 Java 25 与 LWJGL3。启动器必须支持导入带 `libraries` 与 `mainClass` 的自定义版本、能够指定 Java 25 路径，并且可以关闭游戏文件完整性校验。

| 启动器 | 支持情况 | 说明 |
|---|---|---|
| FCL | 支持 | 通过 `caciocavallo17` 与 `mio.Wrapper` 启动，Android 端已实测进入世界 |
| ZL 2 | 支持 | 与 FCL 同样基于 Pojav 启动核心并提供 Java 运行时 |
| HMCL | 支持 | 可导入 MMC 压缩包与自定义版本 JSON，能单独指定 Java 25 |
| PCL 2 | 不支持 | 无法导入 Outlands 的 MMC 安装包 |
| Prism | 支持 | 导入 MMC 实例包后把 Java 指向 25，自带 LWJGL 管理 |
| MultiMC | 支持 | 与 Prism 同源，导入方式一致，需手动安装 Java 25 |
| Modrinth App | 不支持 | 只支持 Forge、Fabric、NeoForge、Quilt 的标准安装 |
| CurseForge App | 不支持 | 同上，无法识别自定义加载器 |
| 官方启动器 | 不支持 | 无法加载自定义主类与 Cleanroom 库 |
| PojavLauncher | 未验证 | 支持自定义版本与 Java 运行环境 |

手机端推荐 FCL，ZalithLauncher 2 同样可用；
桌面端推荐 HMCL，Prism Launcher 与 MultiMC 同样可用。
