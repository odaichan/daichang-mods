package net.daichang.dcmods.common.item.tools.bow;

import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.common.entities.projectile.DCSuperArrow;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DCBow extends ProjectileWeaponItem {
    public DCBow() {
        super(new Properties().rarity(Rarity.RARE).stacksTo(1).fireResistant());
        SuperItemList.addItem(this);
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return itemStack -> itemStack.getItem().equals(DCItems.DC_ARROW.get());
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity living, int p_40670_) {
        if (living instanceof Player player) {
            DCSuperArrow abstractarrow = new DCSuperArrow(DCEntities.DC_SUPER_ARROW.get(), level);
            abstractarrow.setPos(player.getX(), player.getEyeY(), player.getZ());
            abstractarrow.setPos(player.getX(), player.getEyeY() + 0.2, player.getZ());
            level.addFreshEntity(abstractarrow);
            level.addFreshEntity(abstractarrow);
            int timeCharged = this.getUseDuration(stack) - p_40670_;
            float power = getPowerForTime(timeCharged);
            float velocity = power * 3.0F;
            float rotationPitch = player.getXRot();
            float rotationYaw = player.getYRot();
            float pitch = (float) (-Math.sin(rotationPitch * Math.PI / 180.0) * velocity);
            float yaw = (float) (-Math.sin(rotationYaw * Math.PI / 180.0F) * Math.cos(rotationPitch * Math.PI / 180.0F) * velocity);
            float up = (float) (Math.cos(rotationYaw * Math.PI / 180.0F) * Math.cos(rotationPitch * Math.PI / 180.0F) * velocity);
            abstractarrow.setDeltaMovement(yaw, pitch, up);
        }
        super.releaseUsing(stack, level, living, p_40670_);
    }

    public static float getPowerForTime(int p_40662_) {
        float f = (float)p_40662_ / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 10.0F) f = 10.0F;
        return f;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean flag = !player.getProjectile(itemstack).isEmpty();
        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, level, player, hand, flag);
        if (ret != null) {
            return ret;
        } else if (!player.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 72000;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 500;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCItemFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
