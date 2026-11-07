import c4.conarm.lib.materials.CoreMaterialStats
import slimeknights.tconstruct.library.TinkerRegistry

Map<String, Float> defenseTiers = [
        'paper'      : 1.0f,
        'sponge'     : 2.0f,
        'wood'       : 3.0f,
        'flint'      : 5.0f,
        'stone'      : 6.0f,
        'netherrack' : 6.0f,
        'bone'       : 7.0f,
        'slime'      : 8.0f,
        'cactus'     : 8.0f,
        'bloodbone'  : 9.0f,
        'endstone'   : 10.0f,
        'blueslime'  : 12.0f,
        'firewood'   : 13.0f,
        'obsidian'   : 14.0f,
        'prismarine' : 15.0f,
        'magmaslime' : 16.0f,
        'copper'     : 14.0f,
        'alumite'    : 14.0f,
        'silver'     : 14.0f,
        'lead'       : 15.0f,
        'iron'       : 15.0f,
        'bronze'     : 16.0f,
        'electrum'   : 16.0f,
        'alubrass'   : 16.0f,
        'pigiron'    : 16.0f,
        'cobalt'     : 18.0f,
        'ardite'     : 18.0f,
        'knightslime': 18.0f,
        'steel'      : 18.5f,
        'manyullyn'  : 20.0f
]

def unknownMaterial = TinkerRegistry.getMaterial('unknown')

defenseTiers.each { String name, Float defense ->
    def material = TinkerRegistry.getMaterial(name)
    if (material == null || material == unknownMaterial) {
        log.info('盔甲分级跳过 ' + name + '：匠魂中不存在该材料')
        return
    }
    def coreStats = material.getStats('core') as CoreMaterialStats
    if (coreStats == null) {
        log.info('盔甲分级跳过 ' + name + '：没有 core 护甲统计')
        return
    }
    material.addStats(new CoreMaterialStats(coreStats.durability, defense))
    log.info('盔甲分级 ' + name + ' 护甲值改为 ' + defense)
}
