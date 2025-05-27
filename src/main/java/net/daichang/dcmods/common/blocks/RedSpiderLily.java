package net.daichang.dcmods.common.blocks;

import net.daichang.dcmods.common.entities.boss.DCLoveElaina;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedSpiderLily extends FlowerBlock {
    public RedSpiderLily() {
        super(DCEffects.Bloodshed, 10, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY));
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
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
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        player.addEffect(EffectHelper.addEffect(MobEffects.DARKNESS, 3, 5));
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void entityInside(@NotNull BlockState p_60495_, @NotNull Level p_60496_, @NotNull BlockPos p_60497_, @NotNull Entity p_60498_) {
        super.entityInside(p_60495_, p_60496_, p_60497_, p_60498_);
        if (p_60498_ instanceof LivingEntity living) {
            if (!(living instanceof Player) && !(living instanceof DCLoveElaina)) {
                float damageValue = living.getMaxHealth() * 0.01F + 20;
                int duration = 3;
                int level = 2;
                living.addEffect(EffectHelper.addEffect(DCEffects.Bloodshed.get(), duration, level, true));
                living.addEffect(EffectHelper.addEffect(MobEffects.DARKNESS, duration, level));
                living.addEffect(EffectHelper.addEffect(MobEffects.HUNGER, duration, level));
                float healthValue = living.getHealth() - living.getMaxHealth() * 0.1F - 20F;
                living.hurt(EntityHelper.dc_damage(living), damageValue);
                living.setHealth(healthValue);
                EntityHelper.forceSetHealth(living,healthValue);
                living.entityData.set(LivingEntity.DATA_HEALTH_ID, healthValue);
                living.setDeltaMovement(0 ,0, 0);
                EntityHelper.noHurtDuration(living);
            }
            else if (living instanceof Player player) {
                float health = player.getHealth() - 1.0F;
                player.setHealth(health);
                EntityHelper.forceSetHealth(player, health);
                EntityHelper.noHurtDuration(living);
                DataHelper.addHealthDelta(player, -1.0F);
                Utils.addAdvancementToPlayer(player, "dc_m:red_spider_lily");
                player.addEffect(EffectHelper.addEffect(MobEffects.DARKNESS));
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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
