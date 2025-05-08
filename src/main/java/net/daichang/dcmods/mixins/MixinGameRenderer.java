package net.daichang.dcmods.mixins;

import net.daichang.dcmods.common.entities.boss.DCLoveElaina;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {
    @Shadow public Minecraft minecraft;

    @Shadow public abstract void loadEffect(ResourceLocation pResourceLocation);

    @Shadow @Nullable public PostChain postEffect;

    @Shadow public abstract void shutdownEffect();

    @Inject(method = "loadEffect", at = @At("HEAD"), cancellable = true)
    public void loadEffect(ResourceLocation p_109129_, CallbackInfo ci) {
        if (minecraft.getCameraEntity() instanceof DCLoveElaina) {
            p_109129_ = new ResourceLocation("shaders/post/art.json");
        }
        if (postEffect != null && postEffect.getName().endsWith("art.json"))
            ci.cancel();
    }

    @Inject(method = "shutdownEffect", at = @At("HEAD"), cancellable = true)
    public void se(CallbackInfo ci) {
        if (minecraft.getCameraEntity() instanceof DCLoveElaina && minecraft.level != null && postEffect != null && postEffect.getName().endsWith("art.json"))
            ci.cancel();
    }


    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
    }
}
