package net.daichang.dcmods.common.entities.projectile;

import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.utils.EntityActuallyHurt;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.helpers.ExplodeHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class DCSuperArrow extends AbstractArrow {
    public DCSuperArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    @Override
    protected @NotNull ItemStack getPickupItem() {
        return new ItemStack(DCItems.DC_ARROW.get());
    }

    @Override
    public void tick() {
        super.tick();
        level.addParticle(ParticleTypes.WAX_ON, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult p_36757_) {
        super.onHitEntity(p_36757_);
        Entity entity = p_36757_.getEntity();
        if (!(this.getPersistentData().contains("shootByDC"))) killEntity(entity);
        else shootByDC(entity);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        CompoundTag tag = this.getPersistentData();
        super.onHitBlock(blockHitResult);
        Level level = this.level;
        int x = blockHitResult.getBlockPos().getX();
        int y = blockHitResult.getBlockPos().getY();
        int z = blockHitResult.getBlockPos().getZ();
        if (!tag.contains("shootByDC")) {
            if (!tag.contains("isDCArrowR")) {
                EntityHelper.spawnEntity(this.level, this, DCEntities.DC_SUPER_ARROW.get());
                ExplodeHelper.boom(level, x, y, z, this, 5.0F);
            }
            for (Entity entity : EntityHelper.getEntity(level, x, y, z, 5)) killEntity(entity);
        } else {
            for (Entity entity : EntityHelper.getEntity(level, x, y, z, 5)) shootByDC(entity);
        }
        this.remove(RemovalReason.DISCARDED);
    }

    void killEntity(Entity entity) {
        if (entity instanceof LivingEntity living && !(this.getPersistentData().contains("shootByDC"))) EntityActuallyHurt.getInstance(living).dcHurt(3000 + living.getHealth() * 0.01F);
    }

    void shootByDC(Entity entity) {
        if (entity instanceof LivingEntity living)
            EntityActuallyHurt.getInstance(living).dcHurt(114514 + living.getMaxHealth() * 0.001F + living.getHealth() + 0.01F);
    }
}
