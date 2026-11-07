// 宝典每页读 getAllTraitsForStats(type)，类型没有专属列表时回落到默认表
// 工具词条只写工具类型的列表，护甲词条只写护甲类型的列表，两套不互相回落到对方

import c4.conarm.common.armor.traits.ArmorTraits
import slimeknights.tconstruct.library.TinkerRegistry
import slimeknights.tconstruct.library.materials.Material
import slimeknights.tconstruct.library.traits.ITrait
import slimeknights.tconstruct.tools.TinkerTraits
import xyz.phanta.tconevo.init.TconEvoTraits

def unknown = TinkerRegistry.getMaterial('unknown')

def attachTo = { Material material, List<ITrait> traits, List<String> types ->
    if (material == null || material == unknown) {
        return
    }
    traits.findAll { it != null }.each { ITrait trait ->
        types.each { String type ->
            boolean present = material.getTraits().contains(trait) || material.getAllTraitsForStats(type).contains(trait)
            if (!present) {
                material.addTrait(trait, type)
            }
        }
    }
    def merged = traits.findAll { it != null }.collect { it.getIdentifier() }
    log.info('词条挂载：' + material.identifier + ' -> ' + merged + ' @ ' + types)
}

def toolTypes = ['head', 'handle', 'extra', 'bow', 'projectile', 'shaft']
def armorTypes = ['core', 'plates', 'trim']

attachTo(TinkerRegistry.getMaterial('netherite'),
        [TinkerTraits.hellish], ['head'])
attachTo(TinkerRegistry.getMaterial('netherite'),
        [TinkerTraits.heavy, TinkerTraits.dense], toolTypes)
attachTo(TinkerRegistry.getMaterial('netherite'),
        [ArmorTraits.infernal], armorTypes)
attachTo(TinkerRegistry.getMaterial('netherite'),
        [ArmorTraits.heavy, ArmorTraits.dense], armorTypes)
attachTo(TinkerRegistry.getMaterial('netherite'),
        [TinkerTraits.aridiculous], ['head', 'projectile'])
attachTo(TinkerRegistry.getMaterial('netherite'),
        [ArmorTraits.aridiculous], ['core'])
attachTo(TinkerRegistry.getMaterial('diamond'),
        [TconEvoTraits.TRAIT_CRYSTALLINE], toolTypes)
attachTo(TinkerRegistry.getMaterial('diamond'),
        [TinkerRegistry.getTrait('diamond_builtin')], toolTypes)
attachTo(TinkerRegistry.getMaterial('diamond'),
        [TinkerRegistry.getTrait('diamond_builtin_armor')], armorTypes)
attachTo(TinkerRegistry.getMaterial('diamond'),
        [TinkerRegistry.getTrait('tconevo.crystalline_armor')], armorTypes)
attachTo(TinkerRegistry.getMaterial('mana_diamond'),
        [TconEvoTraits.TRAIT_CRYSTALLINE, TconEvoTraits.TRAIT_MANA_INFUSED], toolTypes)
attachTo(TinkerRegistry.getMaterial('mana_diamond'),
        [TinkerRegistry.getTrait('tconevo.crystalline_armor'),
         TinkerRegistry.getTrait('tconevo.mana_infused_armor')], armorTypes)
