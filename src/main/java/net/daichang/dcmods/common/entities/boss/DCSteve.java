package net.daichang.dcmods.common.entities.boss;

import net.daichang.dcmods.Config;
import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.client.PacketHandler;
import net.daichang.dcmods.client.network.S2CElainaPacket;
import net.daichang.dcmods.common.entities.BossEntity;
import net.daichang.dcmods.common.item.tools.creative.DCLoliPickaxe;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.inits.DCSounds;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

public class DCSteve extends BossEntity {
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState attackAnimationState_1 = new AnimationState();
    public final AnimationState attackAnimationState_2 = new AnimationState();
    public DCSteve(EntityType<DCSteve> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 200905;
    }

    public DCSteve(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(DCEntities.DC_STEVE.get(), world);
        this.xpReward = 200905;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity pEntity) {
        if (isHerobrine()) {
            DCLoliPickaxe.killEntity(pEntity, this);
            return true;
        }
        if (level.isClientSide()) this.swing(InteractionHand.MAIN_HAND);
        return super.doHurtTarget(pEntity);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true) {
            @Override
            protected double getAttackReachSqr(@NotNull LivingEntity entity) {
                return 64.0D;
            }

            @Override
            protected void resetAttackCooldown() {
                super.resetAttackCooldown();
            }
        });
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, false){
            @Override
            protected double getFollowDistance() {
                return 64.0D;
            }
        });
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (isHerobrine()) return false;
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.tickCount % 20 == 0) {
            EntityHelper.forceHeal(this, 4);
            if (Config.Server.steve_health_boost.get()) EntityHelper.forceHeal(this, 30);
        }
    }

    @Override
    public float getHealth() {
        if (isHerobrine()) return getMaxHealth();
        return super.getHealth();
    }

    @Override
    public boolean isAlive() {
        if (isHerobrine()) return true;
        return super.isAlive();
    }

    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
    }

    @Override
    public void dropAllDeathLoot(DamageSource pDamageSource) {
        super.dropAllDeathLoot(pDamageSource);
        try {
            ItemEntity item = new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(DCItems.HEART_OF_THE_OCEAN.get()));
            level.addFreshEntity(item);
        } catch (Exception ignored){}
    }

    @Override
    public void remove(RemovalReason pReason) {
        if (!isHerobrine()) super.remove(pReason);
    }

    @Override
    public void onRemovedFromWorld() {
        if (!isHerobrine()) super.onRemovedFromWorld();
    }

    @Override
    public void onClientRemoval() {
        if (!isHerobrine()) super.onClientRemoval();
    }

    @Override
    public void setRemoved(RemovalReason pRemovalReason) {
        if (!isHerobrine()) super.setRemoved(pRemovalReason);
    }

    @Override
    public boolean isRemoved() {
        if (isHerobrine()) return false;
        return super.isRemoved();
    }

    @Override
    public void dropCustomDeathLoot(DamageSource pDamageSource, int pLooting, boolean pHitByPlayer) {
        super.dropCustomDeathLoot(pDamageSource, pLooting, pHitByPlayer);
    }

    @Override
    public void tickDeath() {
        super.tickDeath();
        if (isHerobrine()) {
            for (Entity entity : EntityHelper.getEntity(level, getX(), getY(), getZ(), 49)) {
                if (entity != this) {
                    if (entity instanceof LivingEntity living && !(living instanceof Player)) DCLoliPickaxe.killEntity(living, this);
                    if (entity instanceof ServerPlayer player && player.gameMode.isSurvival()) {
                        player.hurt(EntityHelper.dc_damage(this), 5);
                        PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(()->player), new S2CElainaPacket(new Vec3(entity.getX(), entity.getY(), entity.getZ())));
                    }
                }
            }
        }
    }

    private int currentAttackAnimation = 0;
    private int animationDelay = 0;

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide()) {
            if (this.swinging && animationDelay == 0) {
                if (currentAttackAnimation == 0) {
                    attackAnimationState.start(tickCount);
                    currentAttackAnimation = 1;
                } else if (currentAttackAnimation == 1) {
                    attackAnimationState_1.start(tickCount);
                    currentAttackAnimation = 2;
                } else if (currentAttackAnimation == 2) {
                    attackAnimationState_2.start(tickCount);
                    currentAttackAnimation = 0;
                }
                animationDelay = 10;
            }
            if (animationDelay > 0) animationDelay--;
        }
    }

    @Override
    public boolean isDeadOrDying() {
        if (isHerobrine()) return false;
        return super.isDeadOrDying();
    }

    @Override
    public float getMaxHealth() {
        if (Config.Server.steve_health_boost.get()) return 150.0F;
        return 50.0F;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 19.2)
                .add(Attributes.ARMOR_TOUGHNESS, 4.7D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 32.1D)
                .add(Attributes.ARMOR, 7.3D);
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    public boolean isHerobrine() {
        return this.getName().getString().equals("Herobrine");
    }

    @Override
    public float getMaxDamageHurt() {
        return 5;
    }

    @Override
    public SoundEvent getBossMusic() {
        return DCSounds.Recollection.get();
    }

//    @Override
//    public ResourceLocation getBossBar() {
//        return new ResourceLocation("dc_m:textures/entities/healthbar/steve/health_bar_1.png");
//    }
//
//    @Override
//    public ResourceLocation getBossBarOn() {
//        return new ResourceLocation("dc_m:textures/entities/healthbar/steve/health_bar_2.png");
//    }
//
//    @Override
//    public boolean isHasMask() {
//        return true;
//    }

//    @Override
//    public ResourceLocation getBossBarOverlay() {
//        return new ResourceLocation("dc_m:textures/entities/healthbar/steve/health_bar_3.png");
//    }


    @Override
    public BossEvent.BossBarColor getBossBarColor() {
        return BossEvent.BossBarColor.BLUE;
    }

    @Override
    public ResourceLocation getBossBarOverlay() {
        return ResourceLocation.fromNamespaceAndPath(DCMod.MOD_ID, "textures/gui/steve_bar.png");
    }
}
