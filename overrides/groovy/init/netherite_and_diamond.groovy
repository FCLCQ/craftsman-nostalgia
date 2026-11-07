// 下界合金与钻石材料在 init 阶段注册，材料与配方依赖物品注册完成
// 部件贴图依赖匠魂资源加载时已存在的材料，注册完成后触发一次客户端资源重载

import com.cleanroommc.groovyscript.helper.ingredient.ItemsIngredient
import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.Side
import slimeknights.tconstruct.library.TinkerRegistry
import slimeknights.tconstruct.library.client.MaterialRenderInfo
import slimeknights.tconstruct.library.client.texture.TinkerTexture
import slimeknights.tconstruct.library.fluid.FluidMolten
import slimeknights.tconstruct.library.materials.MaterialTypes
import slimeknights.tconstruct.library.modifiers.IToolMod
import c4.conarm.lib.traits.AbstractArmorTrait
import slimeknights.tconstruct.library.traits.AbstractTrait
import slimeknights.tconstruct.shared.TinkerFluids
import slimeknights.tconstruct.tools.TinkerModifiers
import slimeknights.tconstruct.tools.modifiers.ModFortify
import slimeknights.tconstruct.tools.TinkerTraits
import xyz.phanta.tconevo.init.TconEvoTraits

class TraitDiamondBuiltin extends AbstractTrait {

    TraitDiamondBuiltin() {
        super('diamond_builtin', TextFormatting.AQUA)
    }

    @Override
    boolean canApplyTogether(IToolMod other) {
        if (other != null && (other.getIdentifier() == 'diamond' || other.getIdentifier() == 'diamond_armor')) {
            return false
        }
        return super.canApplyTogether(other)
    }
}

class TraitDiamondArmorBuiltin extends AbstractArmorTrait {

    TraitDiamondArmorBuiltin() {
        super('diamond_builtin', TextFormatting.AQUA)
    }

    @Override
    boolean canApplyTogether(IToolMod other) {
        if (other != null && (other.getIdentifier() == 'diamond' || other.getIdentifier() == 'diamond_armor')) {
            return false
        }
        return super.canApplyTogether(other)
    }
}

class VertexColorRenderInfo extends MaterialRenderInfo.AbstractMaterialRenderInfo {

    final int vertexColor

    VertexColorRenderInfo(int vertexColor) {
        this.vertexColor = vertexColor
    }

    @Override
    TextureAtlasSprite getTexture(ResourceLocation baseTexture, String location) {
        return TinkerTexture.loadManually(baseTexture)
    }

    @Override
    boolean useVertexColoring() {
        return true
    }

    @Override
    int getVertexColor() {
        return vertexColor
    }
}

TinkerRegistry.addTrait(new TraitDiamondBuiltin())
TinkerRegistry.addTrait(new TraitDiamondArmorBuiltin())

def moltenNetherite = new FluidMolten('molten_netherite', 0x6B5D5D)
moltenNetherite.setTemperature(1100)
FluidRegistry.registerFluid(moltenNetherite)
FluidRegistry.addBucketForFluid(moltenNetherite)

def moltenAncient = new FluidMolten('molten_ancient_debris', 0x8B6B4A)
moltenAncient.setTemperature(1000)
FluidRegistry.registerFluid(moltenAncient)
FluidRegistry.addBucketForFluid(moltenAncient)

// FFD 下界合金物品的命名空间随内容托管配置变化，注册材料前先解析出真实物品
def netheriteItem = Item.getByNameOrId('farmers_future_delight:netherite_ingot')
if (netheriteItem == null) {
    netheriteItem = Item.getByNameOrId('futuremc:netherite_ingot')
}
if (netheriteItem == null) {
    log.info('下界合金：未找到下界合金锭，跳过材料注册')
    return
}
def netheriteStack = new ItemStack(netheriteItem)

def netherite = mods.tconstruct.materials.materialBuilder('netherite')
        .setDisplayName('下界合金')
        .setColor(0x6B5D5D)
        .setFluid(new FluidStack(moltenNetherite, 144))
        .isCraftable(false)
        .isCastable(true)
        .setRepresentativeItem(new ItemsIngredient(netheriteStack))
        .addRepairItem(new ItemsIngredient(netheriteStack), 1, 1)
        .addHeadStats(1640, 12.0f, 8.0f, 4)
        .addHandleStats(1.25f, 300)
        .addExtraStats(400)
        .addBowStats(0.9f, 1.3f, 4.0f)
        .addArrowShaftStats(1.8f, 25)
        .addCoreStats(32, 22)
        .addPlatesStats(1.2f, 26, 4.0f)
        .addTrimStats(50)
        .addTrait(TinkerTraits.hellish)
        .addTrait(TinkerTraits.heavy)
        .addTrait(TinkerTraits.dense)
        .addArmorTrait('infernal')
        .addArmorTrait('heavy')
        .addArmorTrait('dense')
        .register()

def diamond = mods.tconstruct.materials.materialBuilder('diamond')
        .setDisplayName('钻石')
        .setColor(0x8CF4E2)
        .setFluid(new FluidStack(TinkerFluids.diamond, 666))
        .isCraftable(true)
        .isCastable(true)
        .setRepresentativeItem(new ItemsIngredient(item('minecraft:diamond')))
        .addRepairItem(new ItemsIngredient(item('minecraft:diamond')), 1, 1)
        .addHeadStats(1230, 7.0f, 7.0f, 3)
        .addHandleStats(0.9f, 250)
        .addExtraStats(300)
        .addCoreStats(18, 12)
        .addPlatesStats(1.0f, 8, 1.5f)
        .addTrimStats(22)
        .register()


if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
    if (netherite != null) {
        netherite.setRenderInfo(new VertexColorRenderInfo(0xFF6B5D5D as int))
    }
    if (diamond != null) {
        diamond.setRenderInfo(new VertexColorRenderInfo(0xFF8CF4E2 as int))
    }
    log.info('材料材质：已设置顶点染色渲染')
}

log.info('材料注册：netherite=' + (netherite != null) + '，diamond=' + (diamond != null))
