// 魔力钻石补全工具与护甲统计
// 材料由匠魂进化在 preInit 注册，这里只追加统计

import c4.conarm.lib.materials.CoreMaterialStats
import c4.conarm.lib.materials.PlatesMaterialStats
import c4.conarm.lib.materials.TrimMaterialStats
import slimeknights.tconstruct.library.TinkerRegistry
import slimeknights.tconstruct.library.materials.ExtraMaterialStats
import slimeknights.tconstruct.library.materials.HandleMaterialStats
import slimeknights.tconstruct.library.materials.HeadMaterialStats
import slimeknights.tconstruct.library.materials.MaterialTypes
import xyz.phanta.tconevo.init.TconEvoTraits

def manaDiamond = TinkerRegistry.getMaterial('mana_diamond')
if (manaDiamond == null || manaDiamond == TinkerRegistry.getMaterial('unknown')) {
    log.info('魔力钻石：材料不存在，跳过统计补全')
    return
}

TinkerRegistry.addMaterialStats(manaDiamond,
        new HeadMaterialStats(1575, 7.5f, 7.25f, 4),
        new HandleMaterialStats(0.95f, 220),
        new ExtraMaterialStats(220),
        new CoreMaterialStats(23, 16),
        new PlatesMaterialStats(1.05f, 9, 2.5f),
        new TrimMaterialStats(25))

// 词条:晶态与蕴魔，挂到全部工具部件
TinkerRegistry.addMaterialTrait(manaDiamond, TconEvoTraits.TRAIT_CRYSTALLINE, MaterialTypes.HEAD)
TinkerRegistry.addMaterialTrait(manaDiamond, TconEvoTraits.TRAIT_CRYSTALLINE, MaterialTypes.HANDLE)
TinkerRegistry.addMaterialTrait(manaDiamond, TconEvoTraits.TRAIT_CRYSTALLINE, MaterialTypes.EXTRA)
TinkerRegistry.addMaterialTrait(manaDiamond, TconEvoTraits.TRAIT_MANA_INFUSED, MaterialTypes.HEAD)
TinkerRegistry.addMaterialTrait(manaDiamond, TconEvoTraits.TRAIT_MANA_INFUSED, MaterialTypes.HANDLE)
TinkerRegistry.addMaterialTrait(manaDiamond, TconEvoTraits.TRAIT_MANA_INFUSED, MaterialTypes.EXTRA)

log.info('魔力钻石：已补齐工具与护甲统计，并挂载晶态与蕴魔')
