package net.daichang.dcmods.event;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.client.font.DCEntityFont;
import net.daichang.dcmods.commands.SoftGetHealthCommand;
import net.daichang.dcmods.common.blocks.RedSpiderLily;
import net.daichang.dcmods.common.damge_type.SuperDamageTypes;
import net.daichang.dcmods.common.entity.DCLoveElaina;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.utils.AnviUtil;
import net.daichang.dcmods.utils.FontUtil;
import net.daichang.dcmods.utils.ModUtil;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.helpers.FileHelper;
import net.daichang.dcmods.utils.helpers.Render2DHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Mod.EventBusSubscriber(modid = DCMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DCForgeEventHandler {

    public static CopyOnWriteArrayList<LivingEntity> livingEntities = new CopyOnWriteArrayList();

    private static Map<DCLoveElaina, Integer> prevBarWidthMap = new HashMap<>();

    @SubscribeEvent
    public static void hurtEvent(@NotNull LivingHurtEvent event) {
        LivingEntity living = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity entity = damageSource.getEntity();
        if (entity instanceof LivingEntity attker) {
            if (attker.attributes.hasAttribute(DCAttributes.DC_SUPER_DAMAGE.get())) event.setAmount(event.getAmount() + ((float) attker.getAttribute(DCAttributes.DC_SUPER_DAMAGE.get()).getValue()));
            if (attker.attributes.hasAttribute(DCAttributes.DC_DEFENSE.get())) event.setAmount((float) (event.getAmount() - (((float) attker.getAttribute(DCAttributes.DC_DEFENSE.get()).getValue()) + 10 * 0.2F -0.3)));
        }
        if (Utils.isBlocking(living)) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void leftClickEntity(@NotNull LivingAttackEvent event) {
        LivingEntity living = event.getEntity();
        DamageSource damageSource = event.getSource();
        Entity entity = damageSource.getEntity();
        if (damageSource.is(SuperDamageTypes.SUPER_DAMAGE)) {
            event.setCanceled(false);
            float normalDamage = 0;
            if (entity instanceof LivingEntity attacker) {
                if (attacker.attributes.hasAttribute(Attributes.ATTACK_DAMAGE)) normalDamage = normalDamage +  (float) attacker.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
                if (attacker.attributes.hasAttribute(DCAttributes.DC_SUPER_DAMAGE.get())) normalDamage = normalDamage + (float) attacker.getAttribute(DCAttributes.DC_SUPER_DAMAGE.get()).getValue();
            }
            float newHealth =  living.getHealth() - event.getAmount() - normalDamage;
            EntityHelper.forceSetHealth(living, newHealth);
            EntityHelper.noHurtDuration(living);
            living.getEntityData().set(LivingEntity.DATA_HEALTH_ID, newHealth);
            living.setHealth(newHealth);
            living.dropAllDeathLoot(EntityHelper.dc_damage(living, living));
            living.playHurtSound(damageSource);
        }
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
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock e) {
        Player player = e.getEntity();
        Level level = e.getLevel();
        BlockPos pos = e.getPos();
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof RedSpiderLily) {
            if (player.getInventory().countItem(DCItems.SUPER_WOOD_INGOT.get()) > 9) {
                if (level instanceof ServerLevel serverLevel) {
                    DCLoveElaina dcWitherBoss = new DCLoveElaina(DCEntities.DC_WITHER.get(), serverLevel);
                    dcWitherBoss.setPos(pos.getX(), pos.getY(), pos.getZ());
                    dcWitherBoss.setTarget(player);
                    serverLevel.addFreshEntity(dcWitherBoss);
                }
            }
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    @SubscribeEvent
    public static void onRenderGUI(RenderGuiEvent.Pre event) {
        GuiGraphics graphics = event.getGuiGraphics();
        PoseStack poseStack = graphics.pose();
        MultiBufferSource source = graphics.bufferSource();
        if (Minecraft.getInstance().player == null) {
            return;
        }
        if (EntityHelper.hasElaina(Minecraft.getInstance().player.level())) {
            int w = event.getWindow().getGuiScaledWidth();
            int h = event.getWindow().getGuiScaledHeight();
            int posX = w / 2;
            int posY = h / 2;
            int offset = 0;
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            Iterator<LivingEntity> iterator = livingEntities.iterator();
            while (iterator.hasNext()) {
                synchronized (iterator) {
                    LivingEntity entity = iterator.next();
                    if (entity == null) {
                        continue;
                    }
                    if (entity instanceof DCLoveElaina elaina) {
                        float maxHealth = elaina.getMaxHealth();
                        float health = elaina.getHealth();
                        int barWidth = (int)(health / maxHealth * 190.0f);
                        int prevBarWidth = prevBarWidthMap.getOrDefault(elaina, barWidth);

                        float transitionSpeed = 0.1F; // 变化速率
                        int interpolatedBarWidth = (int) ((1 - transitionSpeed * event.getPartialTick()) * prevBarWidth + transitionSpeed * event.getPartialTick() * barWidth);
                        prevBarWidthMap.put(elaina, barWidth);

                        String displayName = elaina.getDisplayName().getString();
                        String displayHealth = String.format("%.1f/%.1f", health, maxHealth);
                        int displayNameWidth = DCEntityFont.getFont().width(displayName);
                        int healthWidth = DCEntityFont.getFont().width(displayHealth);

                        graphics.blit(new ResourceLocation("dc_m:textures/entities/health_bar_1.png"), posX - 97, posY - 116 + offset, 0.0f, 0.0f, 256, 256, 256, 256);
                        graphics.blit(new ResourceLocation("dc_m:textures/entities/health_bar_3.png"), posX - 97, posY - 148 + offset, 0.0f, 0.0f, interpolatedBarWidth, 256, 256, 256);
                        graphics.blit(new ResourceLocation("dc_m:textures/entities/health_bar_2.png"), posX - 97, posY - 148 + offset, 0.0f, 0.0f, barWidth, 256, 256, 256);
                        source.getBuffer(RenderType.endPortal());
                        graphics.drawString(DCEntityFont.getFont(), Component.literal(displayName), posX - (displayNameWidth / 2), posY + -92 + offset, -26368, false);
                        graphics.drawString(DCEntityFont.getFont(), Component.literal(displayHealth), posX - (healthWidth / 2), posY + -82 + offset, -26368, false);
                        offset += 30;
                    }
                }
            }
            RenderSystem.depthMask(true);
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderTooltipEventPre(RenderTooltipEvent.Pre event) {
        Random random = new Random(Util.getMillis());
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        GuiGraphics graphics = event.getGraphics();
        PoseStack poseStack = graphics.pose();
        Color rgb = new Color(random.nextInt(0, 255), random.nextInt(0,255), random.nextInt(0, 255));
        int x = event.getX();
        int y = event.getY();
        if (Utils.isNormalTool(item)) {
            Render2DHelper.drawRound(poseStack,x, y, 100, 100, 9, Color.WHITE);
        }
        else if (Utils.isSuperTool(item)){
            Render2DHelper.drawRound(poseStack,x, y, 100, 100, 9, rgb);
        }
        else if (Utils.isCreativeItem(item)) {
            Render2DHelper.drawBlurredShadow(poseStack, x, y, 100, 100, 15, rgb);
        }
        else if (item.equals(DCItems.DC_ENTITY_REMOVE.get())) {
            Render2DHelper.drawGradientRound(poseStack, x, y, 100, 100, 15, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        }
    }

    @SubscribeEvent
    public static void renderTooltipEvent(RenderTooltipEvent.Color event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        float index = 0.5F;
        float hueOffset = (float) Util.getMillis() / 16000.0F;
        float hue = hueOffset + index * index;
        float saturation = 1.0F;
        float brightness = 1.0F;
        int c = Color.HSBtoRGB((((hue * 720.0F + index) % 720.0F >= 360.0F) ? (720.0F - (hue * 720.0F + index) % 720.0F) : ((hue * 720.0F + index) % 720.0F)) / 256.0F, saturation, brightness);
        if (Utils.isSuperTool(item)){
            event.setBorderStart(c);
            event.setBorderEnd(c);
        }
        else if (Utils.isCreativeItem(item)) {
            event.setBorderStart(Color.CYAN.getRGB());
            event.setBorderEnd(Color.CYAN.getRGB());
        }
        else if (Utils.isNormalTool(item)) {
            event.setBorderStart(Color.WHITE.getRGB());
            event.setBorderEnd(Color.WHITE.getRGB());
        }
        else if (item.equals(DCItems.DC_ENTITY_REMOVE.get())) {
            event.setBorderStart(new Random().nextInt());
            event.setBorderEnd(new Random().nextInt());
        }
        else if (Utils.isBlockItem(item)) {
            event.setBorderStart(Color.BLUE.getRGB());
            event.setBorderStart(Color.BLUE.getRGB());
        }
        else if (ModUtil.isDCLoad() && FontUtil.isCanRenderFont(stack)) {
            event.setBorderStart(c);
            event.setBorderEnd(c);
        }
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher()
                .register(Commands.literal("dc_mods")
                        .then(Commands.literal("super_set_health")
                                .executes(cs->{
                                    Entity entity = cs.getSource().getEntity();
                                    SoftGetHealthCommand.killed(entity);
                                    return 2;
                                })
                                .then(Commands.argument("uuid", EntityArgument.entity()).executes(cs->{
                                    for (Entity entity : EntityArgument.getEntities(cs, "entity")) {
                                        SoftGetHealthCommand.killed(entity);
                                    }
                                    return 2;
                                })))
                        .then(Commands.literal("add_def_entity")
                                .executes(cs->{
                                    Entity entity = cs.getSource().getEntity();
                                    FileHelper.defaultWriteYouItem(entity);
                                    return 2;
                                })
                                .then(Commands.argument("uuid", EntityArgument.entity()).executes(cs->{
                                    for (Entity entity : EntityArgument.getEntities(cs, "entity")) {
                                        FileHelper.defaultWriteYouItem(entity);
                                    }
                                    return 2;
                                })))
                        .then(Commands.literal("remove_def_player")
                                .executes(cs->{
                                    Entity entity = cs.getSource().getEntity();
                                    FileHelper.removeDefaultItem(entity);
                                    return 2;
                                })
                                .then(Commands.argument("uuid", EntityArgument.entity()).executes(cs->{
                                    for (Entity entity : EntityArgument.getEntities(cs, "entity")) {
                                        FileHelper.removeDefaultItem(entity);
                                    }
                                    return 2;
                                })))
                );
    }

    @SubscribeEvent
    public static void sendMessageOfPlayer(PlayerEvent.PlayerLoggedInEvent e){
        Player player = e.getEntity();
        player.displayClientMessage(Component.translatable("chat.dc_mods.world_loading").withStyle(ChatFormatting.AQUA), false);
    }

    @SubscribeEvent
    public static void anviUpdate(AnvilUpdateEvent event) {
        AnviUtil.addAnviUpdate(event, Items.IRON_INGOT, Items.DIAMOND, DCItems.SUPER_WOOD_INGOT.get());
    }

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        DamageSource source = event.getSource();
        Item mainHand = living.getMainHandItem().getItem();
        Item offHand = living.getOffhandItem().getItem();
        boolean isHasItem = mainHand == DCItems.WOOD_TOTEM.get() || offHand == DCItems.WOOD_TOTEM.get();
        if (isHasItem) {
            EntityHelper.forceHeal(living, 1.0F);
            living.heal(1.0F);
            event.setCanceled(true);
            living.playSound(SoundEvents.TOTEM_USE);
            if (living instanceof Player player) {
                player.respawn();
                Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(DCItems.WOOD_TOTEM.get()));
            }
        }
    }
}
