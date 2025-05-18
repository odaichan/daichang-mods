package net.daichang.dcmods.bytes.mixins;

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

    @Shadow public abstract void setTicksFrozen(int pTicksFrozen);

    @Unique
    private final Entity dc_entity = (Entity) (Object) this;

    @Inject(method = "getDeltaMovement", at = @At("HEAD"), cancellable = true)
    private void getDeltaMovement(CallbackInfoReturnable<Vec3> cir) {
        if (dc_entity instanceof LivingEntity && EffectHelper.hasEffect((LivingEntity) dc_entity,DCEffects.Freeze.get())) {
            cir.setReturnValue(Vec3.ZERO);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (dc_entity instanceof LivingEntity && EffectHelper.hasEffect((LivingEntity) dc_entity,DCEffects.Freeze.get())) {
            setDeltaMovement(Vec3.ZERO);
            deltaMovement = Vec3.ZERO;
            setTicksFrozen(20);
        }
        if (dc_entity instanceof ItemEntity) {
            ItemStack stack = ((ItemEntity) dc_entity).getItem();
            Item item = stack.getItem();
            Level level = dc_entity.level();
            if (Utils.isSuperTool(stack)) {
                level.addParticle(ParticleTypes.WAX_ON, dc_entity.getX(), dc_entity.getY() + 0.895F, dc_entity.getZ(), 0, 0, 0);
                dc_entity.setCustomName(item.getName(stack));
                dc_entity.setCustomNameVisible(true);
            }
            else if (Utils.isCreativeItem(stack) || dc_entity.getPersistentData().contains("isDCItem")) {
                level.addParticle(ParticleTypes.WAX_OFF, dc_entity.getX(), dc_entity.getY() + 0.895F, dc_entity.getZ(), 0, 0, 0);
                dc_entity.setCustomName(Component.literal(item.getName(stack).getString()).withStyle(ChatFormatting.AQUA));
                dc_entity.setCustomNameVisible(true);
                dc_entity.setGlowingTag(true);
                dc_entity.setDeltaMovement(0, 0, 0);
                dc_entity.setNoGravity(true);
                dc_entity.noPhysics = true;
            }
        }
    }

    @Inject(method = "hurt", at = @At("RETURN"), cancellable = true)
    private void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        if (dc_entity instanceof ItemEntity) {
            ItemStack stack = ((ItemEntity) dc_entity).getItem();
            if (Utils.isSuperTool(stack) || Utils.isCreativeItem(stack)) cir.setReturnValue(Boolean.FALSE);
        }
    }

    @Inject(method = "kill", at = @At("HEAD"), cancellable = true)
    private void kill(CallbackInfo ci) {
        if (dc_entity instanceof ItemEntity) {
            ItemStack stack = ((ItemEntity) dc_entity).getItem();
            if (Utils.isSuperTool(stack) || Utils.isCreativeItem(stack)) ci.cancel();
        }
    }
}
