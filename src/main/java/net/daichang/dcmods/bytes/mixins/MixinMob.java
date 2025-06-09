package net.daichang.dcmods.bytes.mixins;

import net.daichang.dcmods.utils.asm.MethodUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MixinMob extends LivingEntity {

    @Unique
    private final Mob daichangmod$mob = (Mob) (Object) this;

    public MixinMob(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "isNoAi", at = @At("RETURN"), cancellable = true)
    private void isNoAi(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(MethodUtil.isNoAi(daichangmod$mob, cir.getReturnValue()));
    }
}