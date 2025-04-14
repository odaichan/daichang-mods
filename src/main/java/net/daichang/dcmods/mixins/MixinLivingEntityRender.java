package net.daichang.dcmods.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRender<T extends MixinLivingEntity> {
    @Shadow protected abstract float getFlipDegrees(LivingEntity p_115337_);

    @Inject(method = "setupRotations", at = @At("HEAD"))
    private void setupRotations(LivingEntity living, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_, CallbackInfo ci) {
        CompoundTag tag = living.getPersistentData();
        if (tag.getInt("dc_death") > 0) {
            float f = ((float)tag.getInt("dc_death") + p_115321_ - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) f = 1.0F;
            p_115318_.mulPose(Axis.ZP.rotationDegrees(f * this.getFlipDegrees(living)));
        }
    }
}
