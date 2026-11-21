# 贡献指南

**简体中文** | [English](CONTRIBUTING.en.md)

## 可以提交的内容

- 模组版本更新：附上新版本的文件名、SHA-1 与来源链接，`mods-manifest.json` 与 `pack-sources.json` 会同步更新
- 配置修正：说明改动的配置项、游戏内表现与验证方式
- GroovyScript 脚本：说明脚本对应的配方或词条，附上游戏内验证结果
- 资源包与语言：说明来源与许可，许可不兼容的素材不接收
- 文档修正：错别字、过期版本号、失效链接

## 流程

1. 先开 issue 说明要改什么，附环境信息与日志片段。
2. 改完后在 pull request 中列出改动的文件与验证方式：进入游戏、触发对应内容、说明结果。
3. 涉及模组增删时，同时更新 `mods-list.txt`、`mods-manifest.json`、`pack-sources.json` 与 `docs/MODS.md`。

## 不接收

- 只改文档措辞、不改变信息的批量格式化
- 未说明来源或许可的素材
- 与本整合包目标无关的功能扩展
