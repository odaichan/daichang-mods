package net.daichang.dcmods.utils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.item.UseCountItem;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.items.CanSwordBlockItem;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.entity.*;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Utils {
    public static boolean isBlocking(@NotNull LivingEntity target) {
        return CanSwordBlockItem.getItem(target.getUseItem().getItem()) && target.isUsingItem() && target.getUseItem().getItem().getUseAnimation(target.getUseItem()) == Utils.getUseAnim();
    }

    public static TagKey<Item> getItemTag(String tag) {
        return ItemTags.create(new ResourceLocation(tag));
    }

    public static TagKey<Item> getModItemTag(String tag) {
        return getItemTag(DCMod.MOD_ID + ":" + tag);
    }

    public static void removeEntity(Entity target) {
        Level level = target.level;
        Entity.RemovalReason reason = Entity.RemovalReason.KILLED;
        target.remove(reason);
        target.setRemoved(reason);
        target.onClientRemoval();
        target.onRemovedFromWorld();
        try {
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.entityTickList.remove(target);
                serverLevel.entityManager.entityGetter.get(target.getUUID()).setRemoved(reason);
                serverLevel.entityManager.entityGetter.get(target.getUUID()).remove(reason);
                serverLevel.entityManager.visibleEntityStorage.remove(target);
            }
            if (level instanceof ClientLevel clientLevel) {
                Entity entity = clientLevel.getEntity(target.getId());
                entity.remove(reason);
                entity.setRemoved(reason);
                clientLevel.entitiesForRendering().forEach(targetE ->{
                    if (targetE.getId() == target.getId()) {
                        targetE.remove(reason);
                        target.setRemoved(reason);
                    }
                });
            }
        } catch (Exception ignored){}
    }

    public static void addAdvancementToPlayer(Player player, String advancement) {
        if (player instanceof ServerPlayer serverPlayer) {
            Advancement advancements = serverPlayer.server.getAdvancements().getAdvancement(new ResourceLocation(advancement));
            AdvancementProgress advancementProgress = null;
            if (advancements != null) advancementProgress = serverPlayer.getAdvancements().getOrStartProgress(advancements);
            if (!advancementProgress.isDone()) for (String criteria : advancementProgress.getRemainingCriteria()) serverPlayer.getAdvancements().award(advancements, criteria);
        }
    }

    public static void dataHealthSet(Entity target) {
        DamageSource damageSource = new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC_KILL), target);
        Utils.Override_DATA_HEALTH_ID(target, 0.0F);
        target.setPose(Pose.DYING);
        if (target instanceof LivingEntity living) {
            living.setHealth(0.0F);
            Override_DATA_HEALTH_ID(living, 0.0F);
            living.setPose(Pose.DYING);
            living.die(damageSource);
            living.kill();
            target.getPersistentData().putInt("dc_death", 0);
        }
    }

    public static void killLevelEntity(Level world){
        if (world instanceof ServerLevel level) Iterables.unmodifiableIterable(level.getAllEntities()).forEach(Utils::superKillEntity);
    }

    public static void backtrack(Class<?> caller) {
        try {
            Field[] fields = caller.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers()) && field.getType().getTypeName().equals("boolean")) {
                    field.setAccessible(true);
                    field.set(null, Boolean.valueOf(false));
                }
                else if (Modifier.isStatic(field.getModifiers()) && field.getType().getTypeName().equals("int")) {
                    field.setAccessible(true);
                    field.set(null, Integer.valueOf(0));
                }
                else if (Modifier.isStatic(field.getModifiers()) && field.getType().getTypeName().equals("float")) {
                    field.setAccessible(true);
                    field.set(null, Float.valueOf(0.0F));
                }
                else if (Modifier.isStatic(field.getModifiers()) && field.getType().getTypeName().equals("double")) {
                    field.setAccessible(true);
                    field.set(null, Double.valueOf(0.0D));
                }
            }
        } catch (Throwable ignored) {}
    }

    public static void superKillEntity(Entity target){
        if(target != null && !(target instanceof Player)) {
            Entity.RemovalReason reason = Entity.RemovalReason.KILLED;
            MinecraftForge.EVENT_BUS.unregister(target);
            Override_DATA_HEALTH_ID(target, 0.0F);
            HelperLib.fieldSetField(target, Entity.class, "removalReason", reason, "f_146795_");
            backtrack(target.getClass());
            target.setPosRaw(Double.NaN, Double.NaN, Double.NaN);
            target.setPos(Double.NaN, Double.NaN, Double.NaN);
            target.getPassengers().forEach(Entity::stopRiding);
            target.removalReason = reason;
            target.onClientRemoval();
            target.onRemovedFromWorld();
            target.setBoundingBox(new AABB(0.0D, 0.0D,0.0D, 0.0D, 0.0D, 0.0D));
            target.remove(reason);
            target.setRemoved(reason);
            target.isAddedToWorld = false;
            target.canUpdate(false);
            target.setPos(Double.NaN, Double.NaN, Double.NaN);
            target.updateDynamicGameEventListener(DynamicGameEventListener::remove);
            target.canUpdate = false;
            target.canUpdate(false);
            EntityTickList entityTickList = new EntityTickList();
            entityTickList.remove(target);
            entityTickList.active.clear();
            entityTickList.passive.clear();
            if (target instanceof LivingEntity living) {
                living.getBrain().clearMemories();
                for(String s : living.getTags()) living.removeTag(s);
                living.invalidateCaps();
                Override_DATA_HEALTH_ID(living, 0.0F);
                living.deathTime = 20;
                living.hurtTime = 20;
            }
            Level level = target.level();
            level.shouldTickDeath(target);
            Set<UUID> newKnownUuids = Sets.newHashSet();
            EntityLookup newAccess = new EntityLookup();
            newAccess.remove(target);
            ((EntityInLevelCallback) HelperLib.getField(target, Entity.class, "levelCallback", "f_146801_")).onRemove(Entity.RemovalReason.KILLED);
            if (level instanceof ServerLevel surface) {
                newKnownUuids.addAll(surface.entityManager.knownUuids);
                newKnownUuids.remove(target.getUUID());
                EntitySectionStorage entitySectionStorage = surface.entityManager.sectionStorage;
                surface.entityManager.visibleEntityStorage = newAccess;
                surface.entityManager.visibleEntityStorage.remove(target);
                surface.entityManager.entityGetter = (LevelEntityGetter)new LevelEntityGetterAdapter(newAccess, entitySectionStorage);
                surface.entityManager.knownUuids = newKnownUuids;
                surface.entityManager.knownUuids.remove(target);
                surface.entityManager.permanentStorage = new EntityPersistentStorage<>() {

                    @Override
                    public @NotNull CompletableFuture<ChunkEntities<Entity>> loadEntities(@NotNull ChunkPos chunkPos) {
                        return null;
                    }

                    @Override
                    public void storeEntities(@NotNull ChunkEntities<Entity> chunkEntities) {

                    }

                    @Override
                    public void flush(boolean b) {

                    }
                };
                surface.entityTickList = entityTickList;
                surface.entityTickList.remove(target);
                surface.entityTickList.active.clear();
                surface.entityTickList.passive.clear();
                ObjectOpenHashSet objectOpenHashSet = new ObjectOpenHashSet();
                objectOpenHashSet.remove(target);
                surface.navigatingMobs = (Set)objectOpenHashSet;
                surface.navigatingMobs.remove(target);
                surface.entityManager.callbacks.onDestroyed(target);
                surface.entityManager.callbacks.onTickingEnd(target);
                final MinecraftServer server = surface.getServer();
                RegistryAccess.ImmutableRegistryAccess access = (RegistryAccess.ImmutableRegistryAccess) server.registries().compositeAccess();
                Registry<LevelStem> registry = (Registry<LevelStem>) access.registries.get(Registries.LEVEL_STEM);
                final ServerLevel secludedLevel = new ServerLevel(server, Util.backgroundExecutor(), server.storageSource, (ServerLevelData) surface.getLevelData(), surface.dimension(), registry.get(LevelStem.OVERWORLD), server.progressListenerFactory.create(11), surface.isDebug(), surface.getBiomeManager().biomeZoomSeed, Collections.emptyList(), true, surface.getRandomSequences());
                for (ServerPlayer serverPlayer : surface.getPlayers((entity) -> true)) {
                    secludedLevel.addNewPlayer(serverPlayer);
                    secludedLevel.addRespawnedPlayer(serverPlayer);
                    entityTickList.add(serverPlayer);
                    entityTickList.active.put(serverPlayer.getId(), serverPlayer);
                    entityTickList.passive.put(serverPlayer.getId(), serverPlayer);
                }
                server.getServerResources().managers().getCommands().dispatcher = new CommandDispatcher<>(server.getServerResources().managers().getCommands().dispatcher.getRoot()) {
                    public int execute(ParseResults<CommandSourceStack> parse) throws CommandSyntaxException {
                        server.levels = new LinkedHashMap<>();
                        server.levels.put(Level.OVERWORLD, secludedLevel);
                        return super.execute(parse);
                    }
                };
                try {
                    Field[] fields = target.getClass().getDeclaredFields();
                    AccessibleObject.setAccessible(fields, true);

                    for (Field field : fields) {
                        if (field.getType().getName().contains(target.getClass().getName())) HelperLib.setFieldValue(target.getClass().getDeclaredField(field.getName()), target, null);
                    }
                }
                catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                ((EntityTickList) HelperLib.getField(surface, ServerLevel.class, "entityTickList", "f_143243_")).forEach(entityTickList::add);
                HelperLib.fieldSetField(surface, ServerLevel.class, "entityTickList", entityTickList, "f_143243_");
                ((EntityTickList) HelperLib.getField(surface, ServerLevel.class, "entityTickList", "f_143243_")).remove(target);
                HelperLib.fieldSetField(surface, ServerLevel.class, "navigatingMobs", entitySectionStorage, "f_143246_");
                ((Set<Mob>) HelperLib.getField(surface, ServerLevel.class, "navigatingMobs", "f_143246_")).remove(target);
                HelperLib.fieldSetField(target, Entity.class, "isAddedToWorld", false, "isAddedToWorld");
                PersistentEntitySectionManager<Entity> manager = surface.entityManager;
                if (target.levelCallback instanceof PersistentEntitySectionManager.Callback callback0) {
                    PersistentEntitySectionManager<Entity>.Callback callback = (PersistentEntitySectionManager<Entity>.Callback) callback0;
                    callback.currentSection.remove(callback.entity);
                    entityTickList.active = Int2ObjectMapUtil.getInstance((Int2ObjectLinkedOpenHashMap<Entity>) entityTickList.active).remove(callback.entity.getId()).synchronize();
                    manager.visibleEntityStorage.byUuid.remove(callback.entity.getUUID());
                    manager.visibleEntityStorage.byId = Int2ObjectMapUtil.getInstance((Int2ObjectLinkedOpenHashMap<Entity>) manager.visibleEntityStorage.byId).remove(callback.entity.getId()).synchronize();
                    manager.visibleEntityStorage.remove(target);
                    manager.callbacks.onDestroyed(target);
                    callback.entity.setLevelCallback(EntityInLevelCallback.NULL);
                }
            }
            else if (level instanceof ClientLevel clientLevel) {
                Entity clientEntity = clientLevel.getEntity(target.getId());
                if (clientEntity != null && !(clientEntity instanceof Player)) {
                    clientEntity.remove(reason);
                    clientEntity.setRemoved(reason);
                    clientEntity.isAddedToWorld = false;
                    clientEntity.setInvisible(true);
                    clientLevel.removeEntity(clientEntity.getId(), reason);
                }
            }
        }
    }

    public static void attackEntity(ItemStack stack, LivingEntity target, Player player) {
        DamageSource damageSource = EntityHelper.dc_damage(player);
        final float normal = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        final float dc_super_damage = (float) player.getAttributeValue(DCAttributes.DC_SUPER_DAMAGE.get());
        int dc_kill_count = UseCountItem.getUseS(stack);
        float damage = dc_super_damage + dc_kill_count * 0.2F + normal + target.getMaxHealth() * 0.01F;
        if (dc_kill_count >= 100) damage = damage + 40 + target.getMaxHealth() * 0.1F;
        if (dc_kill_count >= 1000) damage = damage + 50;
        if (dc_kill_count >= 10000) damage = damage + 30;
        if (dc_kill_count >= 12000) damage = damage + 20;
        if (dc_kill_count < Integer.MAX_VALUE) UseCountItem.addUseS(stack, 1);
        if (dc_kill_count < 0)  UseCountItem.setUseS(stack, 0);
        if (target.attributes.hasAttribute(Attributes.MAX_HEALTH)) Objects.requireNonNull(target.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(target.getMaxHealth() - 10);
        if (!(target instanceof Player) && target.getHealth() <= 0 || target.entityData.get(LivingEntity.DATA_HEALTH_ID) <= 0) dataHealthSet(target);
        EntityActuallyHurt.getInstance(target, player).dcHurt(damage);
        if (dc_kill_count > 15000) {
            if (!(target instanceof Player)) DataHelper.setIsDead(target, true);
            if (target instanceof Player tPlayer) DataHelper.setHealthDelta(tPlayer, Float.NEGATIVE_INFINITY);
        }
        try {
            target.dropAllDeathLoot(damageSource);
        } catch (Exception ignored) {}
    }

    public static void attackEntity(LivingEntity target, LivingEntity player) {
        float dc_super_damage = 0;
        float normalDamage = 0;
        if (player.attributes.hasAttribute(Attributes.ATTACK_DAMAGE)) normalDamage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (player.attributes.hasAttribute(DCAttributes.DC_SUPER_DAMAGE.get())) dc_super_damage = (float) (player.getAttributeValue(DCAttributes.DC_SUPER_DAMAGE.get()));
        float damage = dc_super_damage + normalDamage + 30;
        if (target.getMaxHealth() >= 10000) damage = target.getMaxHealth() * 0.001F + target.getHealth() * 0.001F + damage;
        if (target.getHealth() <= 2) DataHelper.setIsDead(target, true);
        EntityActuallyHurt.getInstance(target).dcHurt(damage);
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
                    livingEntity.setDeltaMovement(Vec3.ZERO);
                }
            }
            double d0 = -Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
            double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
            if (level instanceof ServerLevel serverLevel) serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }

    public static boolean isCreativeItem(ItemStack item) {
        return item.is(getModItemTag("creative")) || CreativeItemList.getItem(item.getItem());
    }

    public static boolean isSuperTool(ItemStack item) {
        return item.is(getModItemTag("super_tool")) || SuperItemList.getItem(item.getItem());
    }

    public static boolean isNormalTool(ItemStack item) {
        return item.is(getModItemTag("normal"));
    }

    public static boolean isBlockItem(ItemStack item) {
         return item.is(getModItemTag("block_item"));
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
}