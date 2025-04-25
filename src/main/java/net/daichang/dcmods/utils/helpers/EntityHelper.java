package net.daichang.dcmods.utils.helpers;

import net.daichang.dcmods.common.entity.DCLoveElaina;
import net.daichang.dcmods.event.DCForgeEventHandler;
import net.daichang.dcmods.common.damge_type.SuperDamageTypes;
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

public class EntityHelper extends DataHelper {

    public static void noHurtDuration(LivingEntity living) {
        living.hurtTime = 0;
        living.hurtDuration = 0;
        living.setInvulnerable(false);
        living.invulnerableTime = 0;
        living.invulnerableDuration = 0;
    }

    public static List<Entity> getEntity(Level level, double x, double y, double z, double range) {
        AABB aabb = new AABB(x - range, y - range, z - range, x + range, y + range, z + range);
        return new ArrayList<>(level.getEntitiesOfClass(Entity.class, aabb));
    }

    public static DamageSource damageSource(Entity target,Entity attacked ,ResourceKey<DamageType> damageType) {
        return new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), attacked);
    }

    public static DamageSource void_damage(Entity target, Entity attacked) {
        return damageSource(target, attacked, DamageTypes.FELL_OUT_OF_WORLD);
    }

    public static DamageSource generic_damage(Entity target, Entity attack) {
        return damageSource(target, attack, DamageTypes.GENERIC);
    }

    public static DamageSource generic_kill_damage(Entity target, Entity attack) {
        return damageSource(target, attack, DamageTypes.GENERIC_KILL);
    }

    public static DamageSource dc_damage(Entity target, Entity attack) {
        return damageSource(target, attack, SuperDamageTypes.SUPER_DAMAGE);
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

    public static boolean hasElaina(Level level) {
        if (level == null) {
            return false;
        }
        boolean found = false;
        if (level instanceof ClientLevel clientLevel) {
            for (Entity entity : clientLevel.getEntities().getAll()) {
                if (entity instanceof DCLoveElaina elaina) {
                    if (!DCForgeEventHandler.livingEntities.contains(elaina))
                        DCForgeEventHandler.livingEntities.add(elaina);
                    found = true;
                }
            }
        }
        if (level instanceof ServerLevel serverLevel) {
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof DCLoveElaina elaina) {
                    if (!DCForgeEventHandler.livingEntities.contains(elaina))
                        DCForgeEventHandler.livingEntities.add(elaina);
                    found = true;
                }
            }
        }
        return found;
    }

    public static void forceKnockBack(LivingEntity target, LivingEntity attacker, float value) {
        Vec3 direction = target.position().subtract(attacker.position()).normalize();
        Vec3 knockbackVec = direction.scale(value);
        target.setDeltaMovement(target.getDeltaMovement().add(knockbackVec));
    }

    public static void forceHeal(LivingEntity living, float value) {
        if (value >= 0 && living.getHealth() < living.getMaxHealth()) {
            forceSetHealth(living, living.getHealth() + value);
        }
    }
}
