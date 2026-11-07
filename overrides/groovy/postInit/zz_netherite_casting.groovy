// 钻石材料与下界合金部件的钻石模具浇铸
// 钻石部件作为模具会被消耗；下界合金部件回熔只出 1 锭的量

import com.cleanroommc.groovyscript.helper.ingredient.ItemsIngredient
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack
import slimeknights.tconstruct.library.TinkerRegistry
import slimeknights.tconstruct.library.smeltery.CastingRecipe
import slimeknights.tconstruct.library.tools.IToolPart
import slimeknights.tconstruct.shared.TinkerFluids

def unknown = TinkerRegistry.getMaterial('unknown')

def diamond = TinkerRegistry.getMaterial('diamond')
if (diamond == null || diamond == unknown) {
    log.info('钻石材料不存在，跳过模具浇铸配置')
    return
}

def netherite = TinkerRegistry.getMaterial('netherite')
if (netherite == null || netherite == unknown) {
    log.info('下界合金材料不存在，跳过模具浇铸配置')
    return
}

// 钻石原料由匠魂自带配方处理：gemDiamond 按宝石价值 666mB 熔炼，宝石铸模可浇铸回钻石

int castCount = 0

TinkerRegistry.getToolParts().each { IToolPart part ->
    def partName = part instanceof net.minecraft.item.Item ? part.getRegistryName()?.toString() : null
    if (partName != null && (partName.contains('bow_string') || partName.contains('bowstring') || partName.contains('fletching'))) return
    if (!part.canBeCasted() || !part.canUseMaterial(netherite)) return
    def netheritePart = part.getItemstackWithMaterial(netherite)
    def diamondPart = part.getItemstackWithMaterial(diamond)

    // 移除匠魂按材料自动生成的部件浇铸与部件回熔，先确认存在
    if (mods.tconstruct.melting.getRecipes().any { it.matches(netheritePart) }) {
        mods.tconstruct.melting.removeByInput(new ItemsIngredient(netheritePart))
    }
    def fluidForPart = netherite.getFluid()
    boolean hasCasting = false
    try {
        hasCasting = mods.tconstruct.casting_table.getRecipes().any { recipe ->
            def result = recipe.getResult(ItemStack.EMPTY, fluidForPart)
            result != null && result.isItemEqual(netheritePart)
        }
    } catch (Exception e) {
        log.info('下界合金:检查部件浇铸配方失败，跳过 ' + e.message)
    }
    if (hasCasting) {
        mods.tconstruct.casting_table.removeByOutput(netheritePart)
    }

    // 部件回熔固定为 1 锭的量
    mods.tconstruct.melting.add(new ItemsIngredient(netheritePart), new FluidStack(netherite.getFluid(), 144), 800)

    // 钻石部件作模具，每浇铸一次消耗一个
    mods.tconstruct.casting_table.recipeBuilder()
            .output(netheritePart)
            .cast(new ItemsIngredient(diamondPart))
            .consumesCast(true)
            .fluidInput(new FluidStack(netherite.getFluid(), 144))
            .coolingTime(CastingRecipe.calcCooldownTime(netherite.getFluid(), 144))
            .register()
    castCount++
}

log.info('下界合金:钻石模具浇铸已注册 ' + castCount + ' 个部件')

// 钻石工具与盔甲的熔炼：按宝石价值 666mB 折算原版配方中的钻石数量
Map<String, Integer> diamondGear = [
        'minecraft:diamond_sword'      : 2,
        'minecraft:diamond_pickaxe'    : 3,
        'minecraft:diamond_axe'        : 3,
        'minecraft:diamond_shovel'     : 1,
        'minecraft:diamond_hoe'        : 2,
        'minecraft:diamond_helmet'     : 5,
        'minecraft:diamond_chestplate' : 8,
        'minecraft:diamond_leggings'   : 7,
        'minecraft:diamond_boots'      : 4,
]

diamondGear.each { String id, Integer gems ->
    mods.tconstruct.melting.add(new ItemsIngredient(item(id)),
            new FluidStack(TinkerFluids.diamond, gems * 666), 700)
}
