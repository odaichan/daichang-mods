package net.daichang.dcmods.mixins;

import net.daichang.dcmods.utils.Heal2ZList;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Unique
    private final Entity daichangmod$entity = (Entity) (Object) this;

    @Inject(method = "getRemovalReason", at = @At("RETURN"), cancellable = true)
    private void getRemovalReason(CallbackInfoReturnable<Entity.RemovalReason> cir) {
        if (Heal2ZList.isH2Z(daichangmod$entity)) cir.setReturnValue(Entity.RemovalReason.KILLED);
    }
}
