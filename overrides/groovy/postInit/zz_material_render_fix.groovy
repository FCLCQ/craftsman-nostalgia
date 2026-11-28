import net.minecraft.client.Minecraft
import slimeknights.tconstruct.library.TinkerRegistry
import slimeknights.tconstruct.library.client.MaterialRenderInfo

def apply = { String id, int color ->
    def material = TinkerRegistry.getMaterial(id)
    if (material == null || material == TinkerRegistry.getMaterial('unknown')) return
    material.setRenderInfo(new MaterialRenderInfo.Default(color))
}

apply('netherite', 0x6B5D5D)
apply('diamond', 0x8CF4E2)

Minecraft.getMinecraft().refreshResources()
