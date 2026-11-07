// 下界合金配方：熔炼、浇铸与合金，物品来自 Farmer's Future Delight
// 材料与流体在 init 阶段的 netherite_and_diamond.groovy 注册

import com.cleanroommc.groovyscript.helper.ingredient.ItemsIngredient
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import slimeknights.tconstruct.library.smeltery.CastingRecipe
import slimeknights.tconstruct.shared.TinkerFluids
import slimeknights.tconstruct.smeltery.TinkerSmeltery

def moltenNetherite = FluidRegistry.getFluid('molten_netherite')
def moltenAncient = FluidRegistry.getFluid('molten_ancient_debris')
if (moltenNetherite == null || moltenAncient == null) {
    log.info('下界合金：熔融流体不存在，跳过配方注册')
    return
}

// 物品命名空间由 FFD 的内容托管配置决定，默认 farmers_future_delight，检测到 futuremc 时改用后者
def ffdItem = { String name ->
    def found = Item.getByNameOrId('farmers_future_delight:' + name)
    if (found == null) {
        found = Item.getByNameOrId('futuremc:' + name)
    }
    if (found == null) {
        log.info('下界合金：未找到物品 ' + name)
        return null
    }
    return new ItemStack(found)
}

def addMelting = { String name, fluid, int amount, int temperature ->
    def stack = ffdItem(name)
    if (stack == null) {
        return
    }
    mods.tconstruct.melting.add(new ItemsIngredient(stack), new FluidStack(fluid, amount), temperature)
}

addMelting('netherite_scrap', moltenAncient, 144, 700)
addMelting('ancient_debris', moltenAncient, 144, 700)
addMelting('netherite_ingot', moltenNetherite, 144, 800)
addMelting('netherite_block', moltenNetherite, 1296, 800)
addMelting('netherite_sword', moltenNetherite, 144, 800)
addMelting('netherite_pickaxe', moltenNetherite, 144, 800)
addMelting('netherite_axe', moltenNetherite, 144, 800)
addMelting('netherite_shovel', moltenNetherite, 144, 800)
addMelting('netherite_hoe', moltenNetherite, 144, 800)
addMelting('netherite_helmet', moltenNetherite, 144, 800)
addMelting('netherite_chestplate', moltenNetherite, 144, 800)
addMelting('netherite_leggings', moltenNetherite, 144, 800)
addMelting('netherite_boots', moltenNetherite, 144, 800)

// 4 熔融远古残骸 + 4 熔融金 = 1 熔融下界合金
mods.tconstruct.alloying.add(new FluidStack(moltenNetherite, 144),
        new FluidStack(moltenAncient, 576), new FluidStack(TinkerFluids.gold, 576))

def scrap = ffdItem('netherite_scrap')
if (scrap != null) {
    mods.tconstruct.casting_table.recipeBuilder()
            .output(scrap)
            .fluidInput(new FluidStack(moltenAncient, 144))
            .coolingTime(CastingRecipe.calcCooldownTime(moltenAncient, 144))
            .register()
}

def ingot = ffdItem('netherite_ingot')
if (ingot != null) {
    mods.tconstruct.casting_table.recipeBuilder()
            .output(ingot)
            .cast(new ItemsIngredient(TinkerSmeltery.castIngot))
            .fluidInput(new FluidStack(moltenNetherite, 144))
            .coolingTime(CastingRecipe.calcCooldownTime(moltenNetherite, 144))
            .register()
}

def block = ffdItem('netherite_block')
if (block != null) {
    mods.tconstruct.casting_basin.recipeBuilder()
            .output(block)
            .fluidInput(new FluidStack(moltenNetherite, 1296))
            .coolingTime(CastingRecipe.calcCooldownTime(moltenNetherite, 1296))
            .register()
}

log.info('下界合金：熔炼、浇铸与合金配方注册完成')
