// 材料在 init 阶段注册时，首次纹理缝合已经结束，匠魂不会再为它们生成部件贴图。
// 这里补设材质渲染信息并重载一次资源，让 TextureStitchEvent 重建材质贴图。
// 魔力钻石的材料来自匠魂进化，重载时会按 assets/tconevo/materials/mana_diamond.json 恢复多色渲染。

import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.relauncher.Side
import slimeknights.tconstruct.library.TinkerRegistry
import slimeknights.tconstruct.library.client.MaterialRenderInfo

if (FMLCommonHandler.instance().getSide() != Side.CLIENT) {
    return
}

def unknown = TinkerRegistry.getMaterial('unknown')

def setColor = { String id, int color ->
    def material = TinkerRegistry.getMaterial(id)
    if (material == null || material == unknown) {
        log.info('部件颜色：材料不存在 ' + id)
        return
    }
    material.setRenderInfo(new MaterialRenderInfo.Default(color))
    log.info('部件颜色：' + id + ' -> #' + Integer.toHexString(color))
}

setColor('netherite', 0x6B5D5D)
setColor('diamond', 0x8CF4E2)
setColor('mana_diamond', 0xA0F8FF)

Minecraft.getMinecraft().refreshResources()
log.info('部件颜色：资源已重载，材质贴图重建完成')
