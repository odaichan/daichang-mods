package net.daichang.dcmods.mixins;

import net.daichang.dcmods.inits.DCDamageTypes;
import net.daichang.dcmods.utils.DeathList;
import net.daichang.dcmods.utils.Heal2ZList;
import net.daichang.dcmods.utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow public static EntityDataAccessor<Float> DATA_HEALTH_ID;
    @Unique
    private final LivingEntity daichangmod$living = (LivingEntity) (Object) this;

    public MixinLivingEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "getHealth", at = @At("RETURN"), cancellable = true)
    private void getHealth(CallbackInfoReturnable<Float> cir) {
        if (DeathList.isDeath(daichangmod$living) && !(daichangmod$living instanceof Player)) cir.setReturnValue(0.0F);
    }

    @Inject(method = "hurt" ,at = @At("RETURN"), cancellable = true)
    private void hurt(DamageSource p_21016_, float p_21017_, CallbackInfoReturnable<Boolean> cir) {
        if (p_21016_.is(DCDamageTypes.SUPER_DAMAGE)) {
            daichangmod$living.setHealth(daichangmod$living.getHealth() - p_21017_);
            daichangmod$living.getEntityData().set(DATA_HEALTH_ID, daichangmod$living.getHealth() - p_21017_);
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (Heal2ZList.isH2Z(daichangmod$living)) {
            CompoundTag tag = daichangmod$living.getPersistentData();
            tag.putInt("dc_death", tag.getInt("dc_death")+1);
            if (tag.getInt("dc_death") > 20) Utils.removeEntity(daichangmod$living);
        }
    }
}
