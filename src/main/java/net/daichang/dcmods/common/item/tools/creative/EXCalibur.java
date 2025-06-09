package net.daichang.dcmods.common.item.tools.creative;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.daichang.dcmods.common.item.DCTier;
import net.daichang.dcmods.common.item.DCTierItem;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCParticle;
import net.daichang.dcmods.utils.EntityActuallyHurt;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.ExplodeHelper;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EXCalibur extends DCTierItem {
    public Multimap<Attribute, AttributeModifier> modifierMultimap;
    public EXCalibur() {
        super(DCTier.OCEAN_HEART, new Properties());
        CreativeItemList.addItem(this);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> mainHand = ImmutableMultimap.builder();
        mainHand.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 132.1D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ARMOR, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 1.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -2.4D, AttributeModifier.Operation.ADDITION));
        modifierMultimap = mainHand.build();
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker.getAttribute(DCAttributes.OCEAN_DAMAGE.get()) != null) EntityActuallyHurt.getInstance(pTarget, pAttacker).dcHurt((float) pAttacker.getAttributeValue(DCAttributes.OCEAN_DAMAGE.get()));
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity living) hurtEnemy(stack, living, player);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pSlot) {
        return pSlot == EquipmentSlot.MAINHAND ? modifierMultimap : super.getDefaultAttributeModifiers(pSlot);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

//    @Override
//    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
//        list.add(Component.translatable("tooltip.dc_m.excalibur"));
//        super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
//    }

    @Override
    public void onUseTick(@NotNull Level pLevel, @NotNull LivingEntity pLivingEntity, @NotNull ItemStack pStack, int timesleft) {
        super.onUseTick(pLevel, pLivingEntity, pStack, timesleft);
        int counts = getUseDuration(pStack) - timesleft;
        if (pLivingEntity instanceof Player player) {
            if (counts == 40) Utils.sendMsgToPlayerChat(player, Component.translatable("chat.dc_m.ex_calibur_1"));
            if (counts == 80) Utils.sendMsgToPlayerChat(player, Component.translatable("chat.dc_m.ex_calibur_2"));
            if (counts == 120) Utils.sendMsgToPlayerChat(player, Component.translatable("chat.dc_m.ex_calibur_3"));


            @NotNull SimpleParticleType types = DCParticle.SABER_PARTICLE.get();

            RandomSource random = RandomSource.create();
            for (int i = 0; i < 60; i++) {
                float radius = 1.5f + random.nextFloat() * 5f;
                float angle = random.nextFloat() * Mth.TWO_PI;
                float x = Mth.cos(angle) * radius;
                float z = Mth.sin(angle) * radius;

                float speedX = (random.nextFloat() - 0.5f) * 0.02f;
                float speedY = 0.1f + random.nextFloat() * 0.3f;
                float speedZ = (random.nextFloat() - 0.5f) * 0.02f;

                pLevel.addParticle(
                        types,
                        player.position().x + x,
                        player.position().y  - 0.5,
                        player.position().z  + z,
                        speedX,
                        speedY,
                        speedZ
                );
            }
            if (counts == 160) {
                Utils.sendMsgToPlayerChat(player, Component.translatable("chat.dc_m.ex_calibur_press"));
                int radius = 160;// 破坏半径
                float angle = 50;// 角度
                int height = 60;// 高度
                damageEntitiesInSector(player, radius, angle, height, 50);
                destroyBlocksInSector(player, radius, angle, height);
                ExplodeHelper.boom(pLevel, player.position(), player, 2);
                player.stopUsingItem();
                Utils.addCooldown(player, this, 10);
            }
        }
    }

    /**
     * 在玩家面前创建一个圆锥形的破坏区域。
     * 锥尖大致在玩家面前，底部向外展开。
     *
     * @param player 触发者
     * @param radius 圆锥的长度（从玩家面前开始计算的深度）
     * @param angleDegrees 圆锥底部的最大宽度 (X方向直径)
     * @param height 圆锥底部的最大高度 (Y方向直径)
     */
    public static void destroyBlocksInSector(LivingEntity player, int radius, float angleDegrees, int height) {
        Level level = player.level;

        // 获取玩家位置和朝向
        Vec3 playerPos = player.position();
        Vec3 lookAngle = player.getLookAngle().normalize();

        // 计算扇形角度范围
        float halfAngle = angleDegrees / 2;
        double angleRad = Math.toRadians(halfAngle);
        double cosThreshold = Math.cos(angleRad);

        // 获取玩家所在的方块位置
        BlockPos playerBlockPos = player.blockPosition();

        // 遍历高度范围内的每一层
        for (int yOffset = 0; yOffset <= height; yOffset++) {
            // 计算当前层的半径（随着高度增加而减大）
            double currentRadius = radius * (1 + (double)yOffset / height);
            int currentRadiusInt = (int)Math.ceil(currentRadius);

            // 遍历当前层的圆形区域
            for (int xOffset = -currentRadiusInt; xOffset <= currentRadiusInt; xOffset++) {
                for (int zOffset = -currentRadiusInt; zOffset <= currentRadiusInt; zOffset++) {
                    // 跳过超出当前层半径的方块
                    if (xOffset * xOffset + zOffset * zOffset > currentRadius * currentRadius) {
                        continue;
                    }

                    BlockPos targetPos = playerBlockPos.offset(xOffset, yOffset, zOffset);

                    // 计算从玩家到方块的向量
                    Vec3 toBlock = new Vec3(
                            targetPos.getX() + 0.5 - playerPos.x,
                            0,
                            targetPos.getZ() + 0.5 - playerPos.z
                    ).normalize();

                    // 计算点积
                    double dotProduct = lookAngle.x * toBlock.x + lookAngle.z * toBlock.z;

                    if (dotProduct >= cosThreshold) {
                        // 只破坏可破坏的固体方块
                        BlockState state = level.getBlockState(targetPos);
                        if (state.getDestroySpeed(level, targetPos) >= 0 && !state.isAir()) {
                            level.destroyBlock(targetPos, false, player);
                        }
                    }
                }
            }
        }
    }

    public static void damageEntitiesInSector(Player player, int radius, float angleDegrees, int height, float damage) {
        Level level = player.level;
        Vec3 playerPos = player.position();
        Vec3 lookAngle = player.getLookAngle().normalize();
        float halfAngle = angleDegrees / 2;
        double angleRad = Math.toRadians(halfAngle);
        double cosThreshold = Math.cos(angleRad);

        AABB area = new AABB(
                playerPos.x - radius, playerPos.y, playerPos.z - radius,
                playerPos.x + radius, playerPos.y + height, playerPos.z + radius
        );

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (target == player) continue; // Skip the player themselves
            ExplodeHelper.boom(level, target.position(), target, 2);

            Vec3 toEntity = target.position().subtract(playerPos).normalize();

            double dotProduct = lookAngle.x * toEntity.x + lookAngle.z * toEntity.z;
            if (dotProduct >= cosThreshold) {
                double verticalRatio = (target.getY() - playerPos.y) / height;
                if (Math.abs(verticalRatio) <= 1.0) {
                    double distance = playerPos.distanceTo(target.position());
                    if (distance <= radius)
                        EntityActuallyHurt.getInstance(target, player).actuallyHurt(player.damageSources().playerAttack(player), damage + target.getMaxHealth() * 0.001F + target.getHealth() * 0.001F);
                }
            }
        }
    }
}
