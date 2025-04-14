package net.daichang.dcmods.event;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCDamageTypes;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.inits.DCTabs;
import net.daichang.dcmods.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = DCMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventHandler {
    @SubscribeEvent
    public static void hurtEvent(LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity entity = damageSource.getEntity();
        if (Utils.isBlocking(living) && isNotKillDamage(damageSource)) event.setCanceled(true);
        if (entity instanceof LivingEntity livingEntity && damageSource.is(DCDamageTypes.SUPER_DAMAGE)) {
            livingEntity.setHealth((float) (livingEntity.getHealth() - livingEntity.getAttribute(DCAttributes.DC_SUPER_DAMAGE.get()).getValue()));
            livingEntity.getEntityData().set(LivingEntity.DATA_HEALTH_ID, (float) (livingEntity.getHealth() - livingEntity.getAttribute(DCAttributes.DC_SUPER_DAMAGE.get()).getValue()));
        }
    }

    public static boolean isNotKillDamage(DamageSource damageSource) {
        return !(damageSource.is(DamageTypes.FELL_OUT_OF_WORLD) && damageSource.is(DamageTypes.GENERIC_KILL) && damageSource.is(DamageTypes.GENERIC));
    }

    @SubscribeEvent
    public static void leftClickBlock(PlayerInteractEvent.LeftClickBlock e){
        Player player = e.getEntity();
        Level level = e.getLevel();
        BlockPos pos = e.getPos();
        if (player.getMainHandItem().getItem() == DCItems.DESTROY_BLOCK.get()){
            ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), (new ItemStack(level.getBlockState(pos).getBlock())));
            level.addFreshEntity(item);
            item.setPickUpDelay(0);
            level.destroyBlock(pos, false, player);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        }
    }

    @SubscribeEvent
    public static void renderTooltipEvent(RenderTooltipEvent.Color event) {
        Item item = event.getItemStack().getItem();
        float index = 0.5F;
        float hueOffset = (float) net.minecraft.Util.getMillis() / 16000.0F;
        float hue = hueOffset + index * index;
        float saturation = 1.0F;
        float brightness = 1.0F;
        int c = Color.HSBtoRGB((((hue * 720.0F + index) % 720.0F >= 360.0F) ? (720.0F - (hue * 720.0F + index) % 720.0F) : ((hue * 720.0F + index) % 720.0F)) / 256.0F, saturation, brightness);
        if (Utils.isSuperTool(item)){
            event.setBorderStart(c);
            event.setBorderEnd(c);
        } else if (Utils.isCreativeItem(item)) {
            event.setBorderStart(Color.CYAN.getRGB());
            event.setBorderEnd(Color.CYAN.getRGB());
        } else if (Utils.isNormalTool(item)) {
            event.setBorderStart(Color.WHITE.getRGB());
            event.setBorderEnd(Color.WHITE.getRGB());
        }
    }

    @SubscribeEvent
    public static void register(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == DCTabs.DC_MOD_CREATIVE_TAB.getKey()) {
            ItemStack stack = new ItemStack(DCItems.SUPER_WOOD_SWORD.get());
            stack.getTag().putInt("dc_attking", 10000);
            event.accept(stack.getItem());
        }
    }
}
