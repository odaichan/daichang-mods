package net.daichang.dcmods.mixins;

import net.daichang.dcmods.common.item.armors.DCSuperArmor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentItem.class)
public abstract class MixinTrident extends Item {
    public MixinTrident(Properties pProperties) {
        super(pProperties);
    }

    @Inject(method = "releaseUsing", at = @At("HEAD"))
    private void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft, CallbackInfo ci) {
        if (pEntityLiving instanceof Player pPlayer && DCSuperArmor.power(pPlayer) > 0) {
            float $$8 = pPlayer.getYRot();
            float $$9 = pPlayer.getXRot();
            float $$10 = -Mth.sin($$8 * ((float)Math.PI / 180F)) * Mth.cos($$9 * ((float)Math.PI / 180F));
            float $$11 = -Mth.sin($$9 * ((float)Math.PI / 180F));
            float $$12 = Mth.cos($$8 * ((float)Math.PI / 180F)) * Mth.cos($$9 * ((float)Math.PI / 180F));
            float $$13 = Mth.sqrt($$10 * $$10 + $$11 * $$11 + $$12 * $$12);
            float $$14 = 3.0F * ((1.0F + (float)DCSuperArmor.power(pPlayer)) / 4.0F);
            $$10 *= $$14 / $$13;
            $$11 *= $$14 / $$13;
            $$12 *= $$14 / $$13;
            pPlayer.push($$10, $$11, $$12);
            pPlayer.startAutoSpinAttack(20);
            float $$15 = 1.1999999F;
            pPlayer.move(MoverType.SELF, new Vec3(0.0F, 1.1999999F, 0.0F));
            SoundEvent $$16;
            if (DCSuperArmor.power(pPlayer) >= 3) {
                $$16 = SoundEvents.TRIDENT_RIPTIDE_3;
            } else if (DCSuperArmor.power(pPlayer) == 2) {
                $$16 = SoundEvents.TRIDENT_RIPTIDE_2;
            } else {
                $$16 = SoundEvents.TRIDENT_RIPTIDE_1;
            }
            pLevel.playSound(null, pPlayer, $$16, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}
