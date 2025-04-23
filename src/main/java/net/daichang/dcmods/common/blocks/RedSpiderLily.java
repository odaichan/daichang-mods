package net.daichang.dcmods.common.blocks;

import net.daichang.dcmods.common.entity.DCLoveElaina;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RedSpiderLily extends FlowerBlock {
    public RedSpiderLily(Supplier<MobEffect> effectSupplier, int p_53513_, Properties p_53514_) {
        super(effectSupplier, p_53513_, p_53514_);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos blockPos, RandomSource randomSource) {
        super.animateTick(state, level, blockPos, randomSource);
        VoxelShape $$4 = this.getShape(state, level, blockPos, CollisionContext.empty());
        Vec3 $$5 = $$4.bounds().getCenter();
        double $$6 = (double)blockPos.getX() + $$5.x;
        double $$7 = (double)blockPos.getZ() + $$5.z;
        for(int $$8 = 0; $$8 < 20; ++$$8) {
            if (randomSource.nextBoolean()) {
                level.addParticle(ParticleTypes.ASH, $$6 + randomSource.nextDouble() / (double)5.0F, (double)blockPos.getY() + ((double)0.5F - randomSource.nextDouble()), $$7 + randomSource.nextDouble() / (double)5.0F, 0.0F, 0.0F, 0.0F);
                level.addParticle(ParticleTypes.ASH, $$6 + randomSource.nextDouble() / (double)5.0F, (double)blockPos.getY() + ((double)0.5F - randomSource.nextDouble()), $$7 + randomSource.nextDouble() / (double)5.0F, 0.0F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState p_60572_, @NotNull BlockGetter p_60573_, @NotNull BlockPos p_60574_, @NotNull CollisionContext p_60575_) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(@NotNull BlockState p_60495_, @NotNull Level p_60496_, @NotNull BlockPos p_60497_, @NotNull Entity p_60498_) {
        super.entityInside(p_60495_, p_60496_, p_60497_, p_60498_);
        if (p_60498_ instanceof LivingEntity living) {
            living.addEffect(EffectHelper.addEffect(DCEffects.Bloodshed.get(), 60, 2, true));
            if (!(living instanceof Player) && !(living instanceof DCLoveElaina)) {
                float damageValue = living.getMaxHealth() * 0.1F + 20;
                float healthValue = living.getHealth() - living.getMaxHealth() * 0.1F - 20F;
                living.hurt(EntityHelper.generic_kill_damage(living, living), damageValue);
                living.setHealth(healthValue);
                EntityHelper.forceSetHealth(living,healthValue);
                living.entityData.set(LivingEntity.DATA_HEALTH_ID, healthValue);
                living.setInvulnerable(false);
                living.invulnerableTime = 0;
                living.hurtTime = 0;
                living.hurtDuration = 0;
                living.setDeltaMovement(0 ,0, 0);
            }
            else if (living instanceof Player player) {
                float health = player.getHealth() - 1.0F;
                player.setHealth(health);
                EntityHelper.forceSetHealth(player, health);
                player.setInvulnerable(false);
                player.invulnerableTime = 0;
                player.hurtTime = 0;
                player.hurtDuration = 0;
                player.setDeltaMovement(0 ,0, 0);
            }
            else if (living instanceof DCLoveElaina boss) {
                boss.heal(100);
                boss.invulnerableTime = 20;
            }
        }
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_60576_) {
        return true;
    }
}
