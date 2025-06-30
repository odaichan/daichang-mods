package net.daichang.dcmods.utils.helpers;

import net.daichang.dcmods.common.damage_source.DCDamageSource;
import net.daichang.dcmods.common.damage_source.DCOceanDamageSource;
import net.daichang.dcmods.common.entities.BossEntity;
import net.daichang.dcmods.event.DCForgeEventHandler;
import net.daichang.dcmods.inits.DCOceanDamage;
import net.daichang.dcmods.inits.DCSuperDamage;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;


//一些灵感来源于Omni-mob,部分来源于梦幻终焉
public class EntityHelper extends DataHelper {

    public static void noHurtDuration(LivingEntity living) {
        living.hurtTime = 0;
        living.hurtDuration = 0;
        living.setInvulnerable(false);
        living.invulnerableTime = 0;
        living.invulnerableDuration = 0;
    }

    public static boolean isOnHurt(LivingEntity living) {
        return living.hurtTime > 0 || living.hurtDuration > 0;
    }

    public static List<Entity> getEntity(Level level, double x, double y, double z, double range) {
        AABB aabb = new AABB(x - range, y - range, z - range, x + range, y + range, z + range);
        return new ArrayList<>(level.getEntitiesOfClass(Entity.class, aabb));
    }

    public static DamageSource damageSource(Entity attacked ,ResourceKey<DamageType> damageType) {
        return new DamageSource(attacked.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), attacked);
    }

    public static DamageSource void_damage(Entity attacked) {
        return damageSource(attacked, DamageTypes.FELL_OUT_OF_WORLD);
    }

    public static DamageSource generic_damage(Entity attack) {
        return damageSource(attack,DamageTypes.GENERIC);
    }

    public static DamageSource generic_kill_damage(Entity attack) {
        return damageSource(attack, DamageTypes.GENERIC_KILL);
    }

    public static DamageSource ocean_damage(Entity attack) {
        return new DCOceanDamageSource(attack.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DCOceanDamage.OCEAN_DAMAGE), attack);
    }

    public static DamageSource dc_damage(Entity attack) {
        return new DCDamageSource(attack.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DCSuperDamage.SUPER_DAMAGE), attack);
    }

    public static DamageSource mob_attack_damage(Entity attack) {
        return damageSource(attack, DamageTypes.MOB_ATTACK);
    }

    public static DamageSource player_attack_damage(Entity attack) {
        return damageSource(attack, DamageTypes.PLAYER_ATTACK);
    }

    public static void spawnEntity(Level level, Entity entity, EntityType<?> spawn) {
        if (level instanceof ServerLevel serverLevel) {
            double x = entity.getX();
            double y = entity.getY() + 30;
            double z = entity.getZ();

            double radius = 15.0D;

            for (int i = 0; i < 100; i++) {
                double randomX = Math.round(x - radius + Math.random() * (2 * radius));
                double randomZ = Math.round(z - radius + Math.random() * (2 * radius));

                Entity entityToSpawn = spawn.spawn(serverLevel, BlockPos.containing(randomX, y, randomZ), MobSpawnType.COMMAND);
                entityToSpawn.getPersistentData().putBoolean("isDCArrowR", true);
                entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(randomX, y, randomZ)));
                serverLevel.addFreshEntity(entityToSpawn);
            }
        }
    }

    public static void spawnEntity2(Level level, Entity entity, EntityType<?> spawn) {
        if (level instanceof ServerLevel serverLevel) {
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();

            double radius = 50.0D;

            for (int i = 0; i < 30; i++) {
                double randomX = Math.round(x - radius + Math.random() * (2 * radius));
                double randomZ = Math.round(z - radius + Math.random() * (2 * radius));

                Entity entityToSpawn = spawn.spawn(serverLevel, BlockPos.containing(randomX, y, randomZ), MobSpawnType.EVENT);
                entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(randomX, y, randomZ)));
                serverLevel.addFreshEntity(entityToSpawn);
            }
        }
    }

    public static boolean hasBoss(Level level) {
        if (level == null) return false;
        boolean found = false;
        if (level instanceof ClientLevel clientLevel) {
            for (Entity entity : clientLevel.getEntities().getAll()) {
                if (entity instanceof BossEntity elaina) {
                    if (!DCForgeEventHandler.bossList.contains(elaina))
                        DCForgeEventHandler.bossList.add(elaina);
                    found = true;
                }
            }
        }
        if (level instanceof ServerLevel serverLevel) {
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof BossEntity elaina) {
                    if (!DCForgeEventHandler.bossList.contains(elaina))
                        DCForgeEventHandler.bossList.add(elaina);
                    found = true;
                }
            }
        }
        return found;
    }

    public static void forceKnockBack(LivingEntity target, LivingEntity attacker, float XYValue, float ZValue) {
        Vec3 direction = target.position().subtract(attacker.position()).normalize();
        Vec3 knockbackVec = direction.scale(XYValue);
        target.setDeltaMovement(target.getDeltaMovement().add(knockbackVec.x, knockbackVec.y + ZValue, knockbackVec.z));
    }

    public static void forceHeal(LivingEntity living, float value) {
        if (value >= 0 && living.getHealth() < living.getMaxHealth()) forceSetHealth(living, living.getHealth() + value);
        if (living.getHealth() > living.getMaxHealth()) forceSetHealth(living, living.getMaxHealth());
    }
}
