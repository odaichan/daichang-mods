package net.daichang.dcmods.common.entities.projectile;

import net.daichang.dcmods.common.entities.boss.DCLoveElaina;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.helpers.ExplodeHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;

public class DCWitherSkull extends WitherSkull {
    public int age = 0;
    public DCWitherSkull(EntityType<DCWitherSkull> p_37598_, Level p_37599_) {
        super(p_37598_, p_37599_);
    }


    @Override
    protected void onHitEntity(@NotNull EntityHitResult p_36757_) {
        Entity entity = p_36757_.getEntity();
        killEntity(entity);
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        Level level = this.level;
        int x = blockHitResult.getBlockPos().getX();
        int y = blockHitResult.getBlockPos().getY();
        int z = blockHitResult.getBlockPos().getZ();
        ExplodeHelper.boom(level, x, y, z, this, 5.0F);
        for (Entity entity : EntityHelper.getEntity(level, x, y, z, 5)) killEntity(entity);
        this.remove(RemovalReason.DISCARDED);
    }

    void killEntity(Entity entity) {
        if (entity instanceof LivingEntity living && !(living instanceof DCLoveElaina) && isSuPlayer(living)) {
            float damage = living.getMaxHealth() * 0.1F + 47;
            living.hurt(EntityHelper.dc_damage(this), damage);
        }
    }

    boolean isSuPlayer(LivingEntity living) {
        return living instanceof ServerPlayer player && player.gameMode.isSurvival() && player.isAlive();
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        if (age > 180) {
            double x = getX();
            double y = getY();
            double z = getZ();
            this.discard();
            ExplodeHelper.boom(level, x, y, z, this, 5.0F);
            for (Entity entity : EntityHelper.getEntity(level, x, y, z, 5)) killEntity(entity);
        }
    }
}
