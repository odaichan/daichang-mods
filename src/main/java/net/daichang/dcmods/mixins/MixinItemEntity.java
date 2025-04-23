package net.daichang.dcmods.mixins;

import net.daichang.dcmods.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {
    @Shadow public abstract boolean dampensVibrations();

    @Unique
    private final ItemEntity daichangmod$entity = (ItemEntity) (Object) this;

    public MixinItemEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public boolean hurt(DamageSource p_19946_, float p_19947_) {
        ItemStack stack = daichangmod$entity.getItem();
        Item item = stack.getItem();
        if (Utils.isSuperTool(item) || Utils.isCreativeItem(item)) return false;
        else return super.hurt(p_19946_, p_19947_);
    }

    @Override
    public void kill() {
        ItemStack stack = daichangmod$entity.getItem();
        Item item = stack.getItem();
        if (Utils.isSuperTool(item) || Utils.isCreativeItem(item)) return;
        super.kill();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        ItemStack stack = daichangmod$entity.getItem();
        Item item = stack.getItem();
        Level level = daichangmod$entity.level();
        if (Utils.isSuperTool(item)) {
            level.addParticle(ParticleTypes.WAX_ON, daichangmod$entity.getX(), daichangmod$entity.getY() + 0.895F, daichangmod$entity.getZ(), 0, 0, 0);
            daichangmod$entity.setCustomName(item.getName(stack));
            daichangmod$entity.setCustomNameVisible(true);
        }
        else if (Utils.isCreativeItem(item) || daichangmod$entity.getPersistentData().contains("isDCItem")) {
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
