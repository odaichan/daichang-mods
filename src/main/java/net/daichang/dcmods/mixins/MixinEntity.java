package net.daichang.dcmods.mixins;

import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow public abstract void setDeltaMovement(Vec3 pDeltaMovement);

    @Shadow public Vec3 deltaMovement;
    @Unique
    private final Entity daichangmod$entity = (Entity) (Object) this;

    @Inject(method = "getDeltaMovement", at = @At("HEAD"), cancellable = true)
    private void getDeltaMovement(CallbackInfoReturnable<Vec3> cir) {
        if (daichangmod$entity instanceof LivingEntity && EffectHelper.hasEffect((LivingEntity) daichangmod$entity,DCEffects.Freeze.get())) {
            cir.setReturnValue(Vec3.ZERO);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (daichangmod$entity instanceof LivingEntity && EffectHelper.hasEffect((LivingEntity) daichangmod$entity,DCEffects.Freeze.get())) {
            setDeltaMovement(Vec3.ZERO);
            deltaMovement = Vec3.ZERO;
        }
        if (daichangmod$entity instanceof ItemEntity) {
            ItemStack stack = ((ItemEntity) daichangmod$entity).getItem();
            Item item = stack.getItem();
            Level level = daichangmod$entity.level();
            if (Utils.isSuperTool(stack)) {
                level.addParticle(ParticleTypes.WAX_ON, daichangmod$entity.getX(), daichangmod$entity.getY() + 0.895F, daichangmod$entity.getZ(), 0, 0, 0);
                daichangmod$entity.setCustomName(item.getName(stack));
                daichangmod$entity.setCustomNameVisible(true);
            }
            else if (Utils.isCreativeItem(stack) || daichangmod$entity.getPersistentData().contains("isDCItem")) {
                level.addParticle(ParticleTypes.WAX_OFF, daichangmod$entity.getX(), daichangmod$entity.getY() + 0.895F, daichangmod$entity.getZ(), 0, 0, 0);
                daichangmod$entity.setCustomName(Component.literal(item.getName(stack).getString()).withStyle(ChatFormatting.AQUA));
                daichangmod$entity.setCustomNameVisible(true);
                daichangmod$entity.setGlowingTag(true);
                daichangmod$entity.setDeltaMovement(0, 0, 0);
                daichangmod$entity.setNoGravity(true);
                daichangmod$entity.noPhysics = true;
            }
        }
    }

    @Inject(method = "hurt", at = @At("RETURN"), cancellable = true)
    private void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        if (daichangmod$entity instanceof ItemEntity) {
            ItemStack stack = ((ItemEntity) daichangmod$entity).getItem();
            if (Utils.isSuperTool(stack) || Utils.isCreativeItem(stack)) cir.setReturnValue(Boolean.FALSE);
        }
    }

    @Inject(method = "kill", at = @At("HEAD"), cancellable = true)
    private void kill(CallbackInfo ci) {
        if (daichangmod$entity instanceof ItemEntity) {
            ItemStack stack = ((ItemEntity) daichangmod$entity).getItem();
            if (Utils.isSuperTool(stack) || Utils.isCreativeItem(stack)) ci.cancel();
        }
    }
}
