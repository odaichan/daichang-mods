package net.daichang.dcmods.utils;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.inits.DCItems;
import net.minecraft.SharedConstants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

public class Utils {
    public static boolean isBlocking(@NotNull LivingEntity target) {
        return target.getUseItem().getItem() == DCItems.SUPER_WOOD_SWORD.get().getDefaultInstance().getItem() && target.isUsingItem() && target.getUseItem().getItem().getUseAnimation(target.getUseItem()) == Utils.getUseAnim();
    }

    public static boolean isBlocking2(LivingEntity player) {
        return player.getUseItem().getItem() == DCItems.SUPER_WOOD_SWORD.get() && player.isUsingItem();
    }

    public static void removeEntity(Entity target) {
        Level level = target.level;
        Entity.RemovalReason reason = Entity.RemovalReason.KILLED;
        target.remove(reason);
        target.setRemoved(reason);
        target.onClientRemoval();
        target.onRemovedFromWorld();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.entityTickList.remove(target);
            serverLevel.entityManager.visibleEntityStorage.remove(target);
        }
    }

    public static void dataHealthSet(Entity target) {
        DamageSource damageSource = new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC_KILL), target);
        Utils.Override_DATA_HEALTH_ID(target, 0.0F);
        target.setPose(Pose.DYING);
        DeathList.addDeath(target);
        if (target instanceof LivingEntity living) {
            living.setHealth(0.0F);
            Override_DATA_HEALTH_ID(living, 0.0F);
            living.setPose(Pose.DYING);
            living.die(damageSource);
            living.kill();
            target.getPersistentData().putInt("dc_death", 0);
        }
    }

    public static void attackEntity(ItemStack stack, LivingEntity target, Player player, final float dc_super_damage) {
        DamageSource damageSource = new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD), player);
        target.level().broadcastDamageEvent(target, damageSource);
        CompoundTag tag = stack.getTag();
        tag.putInt("dc_attking", tag.getInt("dc_attking") + 1);
        try {
            target.dropAllDeathLoot(damageSource);
        } catch (Exception ignored){}
        target.hurtTime = 0;
        target.hurtDuration = 0;
        target.setDeltaMovement(0, 0,0);
        target.setInvulnerable(false);
        target.invulnerableTime = 0;
        if (tag.getInt("dc_attking") <100) {
            target.hurt(damageSource, dc_super_damage + tag.getInt("dc_attking"));
            target.getEntityData().set(LivingEntity.DATA_HEALTH_ID, target.getHealth() - dc_super_damage);
            target.setHealth(target.getHealth() - dc_super_damage);
        } else {
            target.hurt(damageSource, dc_super_damage + target.getMaxHealth() * 0.7F + 4000 + tag.getInt("dc_attking"));
            target.getEntityData().set(LivingEntity.DATA_HEALTH_ID, target.getHealth() - target.getMaxHealth() - 0.7F + 4000 - tag.getInt("dc_attking") - dc_super_damage);
            target.setHealth(target.getHealth() - target.getMaxHealth() * 0.7F - 4000 - tag.getInt("dc_attking") - dc_super_damage);
        }
        Utils.sweepAttack(target.level(), player, target);
        Random random = new Random();
        float f = random.nextFloat(0, 1);
        if (f == 0.1F) {
            target.kill();
            target.die(damageSource);
            target.setHealth(target.getHealth() - target.getMaxHealth());
            target.hurt(damageSource, Float.MAX_VALUE);
            target.getEntityData().set(LivingEntity.DATA_HEALTH_ID, target.getHealth() - target.getMaxHealth());
            target.dropAllDeathLoot(damageSource);
            target.tickDeath();
            target.isDeadOrDying();
            if (player.level().isClientSide()) player.displayClientMessage(Component.translatable("chat.dc_mods.kill_entity"), false);
        }
        if (tag.getInt("dc_attking") >= 10000) {
            target.hurt(damageSource, 3000);
            target.setHealth(target.getHealth() - 3000);
            target.getEntityData().set(LivingEntity.DATA_HEALTH_ID, target.getHealth() - 3000);
        }
        if (tag.getInt("dc_attking") >= 15000) {
            killEntity(target, damageSource);
        }
        if (target.getHealth() < 5) {
            killEntity(target, damageSource);
        }
    }

    public static void killEntity(LivingEntity target, DamageSource damageSource) {
        target.getPersistentData().putInt("dc_death", 0);
        Heal2ZList.addUUID(target);
        target.hurt(damageSource, Float.POSITIVE_INFINITY);
        target.setHealth(target.getHealth() - target.getMaxHealth());
        target.kill();
        target.die(damageSource);
        target.heal(Float.NEGATIVE_INFINITY);
        target.setPose(Pose.DYING);
        target.getBrain().clearMemories();
    }

    public static UseAnim getUseAnim() {
        return UseAnim.valueOf(DCMod.MOD_ID + ":BLOCK");
    }

    public static void sweepAttack(Level level, LivingEntity livingEntity, Entity victim) {
        if (livingEntity instanceof Player player) {
            for (LivingEntity livingentity : level.getEntitiesOfClass(LivingEntity.class, player.getItemInHand(InteractionHand.MAIN_HAND).getSweepHitBox(player, victim))) {
                double entityReachSq = Mth.square(player.getEntityReach()); // Use entity reach instead of constant 9.0. Vanilla uses bottom center-to-center checks here, so don't update this to use canReach, since it uses closest-corner checks.
                if (!player.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand) livingentity).isMarker()) && player.distanceToSqr(livingentity) < entityReachSq) {
                    livingentity.knockback(0.0F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                    livingEntity.setDeltaMovement(0, 0, 0);
                }
            }
            level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, livingEntity.getSoundSource(), 1.0F, 1.0F);
            double d0 = -Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
            double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
            if (level instanceof ServerLevel serverLevel) serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }

    public static boolean isCreativeItem(Item item) {
        return item.equals(DCItems.DC_CRAFT.get()) || item.equals(DCItems.DESTROY_BLOCK.get()) || item.equals(DCItems.TIME_CLOCK.get()) || item.equals(DCItems.DATA_SET.get());
    }

    public static boolean isSuperTool(Item item) {
        return item.equals(DCItems.SUPER_WOOD_SWORD.get()) || item.equals(DCItems.SUPER_WOOD_PICKAXE.get()) || item.equals(DCItems.SUPER_WOOD_AXE.get()) || item.equals(DCItems.SUPER_WOOD_SHOVEL.get()) || item.equals(DCItems.SUPER_WOOD_INGOT.get()) || item.equals(DCItems.SUPER_WOOD_HOE.get());
    }

    public static boolean isNormalTool(Item item) {
        return item.equals(DCItems.NORMAL_WOOD_SWORD.get()) || item.equals(DCItems.NORMAL_WOOD_HOE.get()) || item.equals(DCItems.NORMAL_WOOD_PICKAXE.get()) || item.equals(DCItems.NORMAL_WOOD_AXE.get()) || item.equals(DCItems.NORMAL_WOOD_SHOVEL.get()) || item.equals(DCItems.WOOD_INGOT.get());
    }

    public static void Override_DATA_HEALTH_ID(LivingEntity livingEntity, final float X) {
        SynchedEntityData data = new SynchedEntityData(livingEntity) {
            @NotNull
            public <T> T get(@NotNull EntityDataAccessor<T> p_135371_) {
                return (p_135371_ == LivingEntity.DATA_HEALTH_ID) ? (T)Float.valueOf(X) : (T)super.get(p_135371_);
            }
        };
        copyProperties(SynchedEntityData.class, livingEntity.entityData, data);
        livingEntity.entityData = data;
    }

    public static void copyProperties(Class<?> clazz, Object source, Object target) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers()))
                    field.set(target, field.get(source));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void Override_DATA_HEALTH_ID(Player player, final float X) {
        SynchedEntityData data = new SynchedEntityData((Entity)player) {
            @NotNull
            public <T> T get(@NotNull EntityDataAccessor<T> p_135371_) {
                return (p_135371_ == LivingEntity.DATA_HEALTH_ID) ? (T)Float.valueOf(X) : (T)super.get(p_135371_);
            }
        };
        copyProperties(SynchedEntityData.class, player.entityData, data);
        player.entityData = data;
    }

    public static void Override_DATA_HEALTH_ID(Entity entity, final float X) {
        SynchedEntityData data = new SynchedEntityData(entity) {
            @NotNull
            public <T> T get(@NotNull EntityDataAccessor<T> p_135371_) {
                return (p_135371_ == LivingEntity.DATA_HEALTH_ID) ? (T)Float.valueOf(X) : (T)super.get(p_135371_);
            }
        };
        copyProperties(SynchedEntityData.class, entity.entityData, data);
        entity.entityData = data;
    }

    public static <T, E> void fieldSetField(T instance, Class<? super T> cls, String fieldName, E val, String srg) {
        String[] remap = new String[]{srg, fieldName};
        String name = SharedConstants.IS_RUNNING_IN_IDE ? remap[1] : remap[0];
        try {
            ObfuscationReflectionHelper.setPrivateValue(cls, instance, val, name);
        } catch (Exception ignored) {
        }
    }

    public static <E> Object getField(E instance, Class<? super E> cls, String fieldName, String srg) {
        String[] remap = new String[]{srg, fieldName};
        String name = SharedConstants.IS_RUNNING_IN_IDE ? remap[1] : remap[0];
        return ObfuscationReflectionHelper.getPrivateValue(cls, instance, name);
    }
}
