// 晶态的护甲版本：按护甲剩余耐久提供减伤，满耐久 20%，耐久耗尽为 0

import c4.conarm.lib.traits.AbstractArmorTrait
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.event.entity.living.LivingHurtEvent
import slimeknights.tconstruct.library.TinkerRegistry

class TraitCrystallineArmor extends AbstractArmorTrait {

    private static final float MAX_REDUCTION = 0.20f

    TraitCrystallineArmor() {
        super('tconevo.crystalline', TextFormatting.AQUA)
    }

    @Override
    float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent event) {
        if (armor == null || armor.isEmpty() || armor.getMaxDamage() <= 0) {
            return newDamage
        }
        float durabilityRatio = 1.0f - (armor.getItemDamage() / (float) armor.getMaxDamage())
        return newDamage * (1.0f - MAX_REDUCTION * durabilityRatio)
    }
}

def trait = new TraitCrystallineArmor()
TinkerRegistry.addTrait(trait)
log.info('晶态护甲：已注册 ' + trait.getIdentifier() + '，满耐久减伤 20%')
