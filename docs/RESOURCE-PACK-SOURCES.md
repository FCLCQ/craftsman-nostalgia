# 资源包来源审计

`overrides/resourcepacks/CraftsmanNostalgia.zip` 只收录与来源文件逐字节一致的素材，统计基于包内 `assets/` 条目。

## 总量

| 项目 | 数量 |
|---|---|
| 资源包素材条目 | 1837 |
| 来自 ModernityExtra 主包（MIT） | 755 |
| 来自 Backport Everything（CC BY-SA 4.0） | 1082 |
| 包内附带文件 | `pack.mcmeta`、`LICENSE.md`、`THIRD-PARTY-NOTICES.md` |

## 内容构成

保留的素材覆盖 `minecraft`、`tconstruct`、`botania`、`twilightforest`、`taiga`、`conarm` 等命名空间的贴图与模型，均为源包内原样文件。`minecraft` 命名空间由 Backport Everything 提供 1.14 风格的原版方块、物品、实体、GUI 与模型贴图，主菜单的 logo 与全景图不纳入。声音、语言文件与 `sounds.json` 由各模组自带资源与 1.12.2 原版资源提供。

## 计划引入的素材

以下素材计划在取得授权或改用许可兼容的替代素材后引入，用于补齐观感、音效与语言，不列入当前包。

| 来源 | 作者 | 当前状态 |
|---|---|---|
| FFD Additional Resource Pack 1.1 | xy177 | 未附许可文件，待授权 |
| Modernity Adjunct | RuiXuqi 等 | LGPL-2.1，待授权或改用兼容许可 |
| Fabric Chests | anner | GPL-3.0，待授权或改用兼容许可 |

Modernity-f3 3.10.3 采用 LGPL-3.0-only，与整包 CC BY-SA 4.0 不兼容，未引入；对应的原版贴图覆盖由 Backport Everything 提供。

## 授权

资源包整体采用 CC BY-SA 4.0，MIT 素材保留原许可，随包的 `LICENSE.md` 与 `THIRD-PARTY-NOTICES.md` 给出完整说明。Minecraft 原版素材版权归 Mojang Studios。再分发时需要署名并以相同方式共享。

## 后续维护

新增素材必须先确认来源许可，并保证与来源文件一致或明确记录改写范围；未附许可文件、GPL、LGPL 与 CC BY-NC 系列来源在取得授权或改用兼容许可前不纳入。
