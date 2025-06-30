package net.daichang.dcmods.event;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.daichang.dcmods.Config;
import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.commands.SoftGetHealthCommand;
import net.daichang.dcmods.common.blocks.RedSpiderLily;
import net.daichang.dcmods.common.entities.BossEntity;
import net.daichang.dcmods.common.entities.boss.DCLoveElaina;
import net.daichang.dcmods.common.item.armors.DCSuperArmor;
import net.daichang.dcmods.common.item.tools.creative.DCLoliPickaxe;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.utils.*;
import net.daichang.dcmods.utils.helpers.*;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mod.EventBusSubscriber(modid = DCMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DCForgeEventHandler {

    public static CopyOnWriteArrayList<LivingEntity> bossList = new CopyOnWriteArrayList<>();

    public static final Set<BossEntity> BOSSES = Collections.newSetFromMap(new WeakHashMap<>());

    @SubscribeEvent
    public static void hurtEvent(@NotNull LivingHurtEvent event) {
        LivingEntity hurtEntity = event.getEntity();
        if (hurtEntity.getAttribute(DCAttributes.DC_DEFENSE.get()) != null && hurtEntity.getAttributeValue(DCAttributes.DC_DEFENSE.get()) > 0) {
            float resitValue = (float) hurtEntity.getAttributeValue(DCAttributes.DC_DEFENSE.get()) * 0.8F + 1.0F;
            event.setAmount(event.getAmount() - resitValue);
        }
        if (Utils.isBlocking(hurtEntity)) {
            hurtEntity.playSound(SoundEvents.SHIELD_BLOCK);
            event.setCanceled(true);
        }
        if (DCLoliPickaxe.isHasLoliPickaxe(hurtEntity)) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void leftClickEntity(@NotNull LivingAttackEvent event) {
        LivingEntity living = event.getEntity();
        if (Utils.isBlocking(living)) {
            living.playSound(SoundEvents.SHIELD_BLOCK);
            event.setCanceled(true);
        }
        if (DCLoliPickaxe.isHasLoliPickaxe(living)) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void leftClickBlock(PlayerInteractEvent.LeftClickBlock e){
        Player player = e.getEntity();
        Level level = e.getLevel();
        BlockPos pos = e.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();
        if (player.getMainHandItem().getItem() == DCItems.DESTROY_BLOCK.get() || player.getMainHandItem().getItem() == DCItems.LoliPickaxe.get()){
            ItemEntity item = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), (new ItemStack(level.getBlockState(pos).getBlock())));
            item.setPickUpDelay(0);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
            level.addFreshEntity(item);
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
                    DCLoveElaina dcWitherBoss = new DCLoveElaina(DCEntities.ELAINA.get(), serverLevel);
                    dcWitherBoss.setPos(pos.getX(), pos.getY(), pos.getZ());
                    dcWitherBoss.setTarget(player);
                    serverLevel.addFreshEntity(dcWitherBoss);
                    level.destroyBlock(pos, false);
                }
            }
        }
    }

//    @OnlyIn(value=Dist.CLIENT)
//    @SubscribeEvent
//    public static void onRenderGUI(RenderGuiEvent.Pre event) {
//        GuiGraphics graphics = event.getGuiGraphics();
//        MultiBufferSource source = graphics.bufferSource();
//        if (Minecraft.getInstance().player == null) return;
//        if (EntityHelper.hasBoss(Minecraft.getInstance().player.level())) {
//            int w = event.getWindow().getGuiScaledWidth();
//            int h = event.getWindow().getGuiScaledHeight();
//            int posX = w / 2;
//            int posY = h / 2;
//            int offset = 0;
//            RenderSystem.disableDepthTest();
//            RenderSystem.depthMask(false);
//            RenderSystem.enableBlend();
//            RenderSystem.setShader(GameRenderer::getPositionTexShader);
//            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//            Iterator<LivingEntity> iterator = bossList.iterator();
//            while (iterator.hasNext()) {
//                synchronized (iterator) {
//                    LivingEntity entity = iterator.next();
//                    if (entity == null) continue;
//                    if (entity instanceof BossEntity boss) {
//                        Font font = boss.getBossBarFont();
//                        float maxHealth = boss.getMaxHealth();
//                        float health = boss.getHealth();
//                        int barWidth = (int)(health / maxHealth * 190.0f);
//                        int prevBarWidth = prevBarWidthMap.getOrDefault(boss, barWidth);
//
//                        float transitionSpeed = 0.1F;
//                        int interpolatedBarWidth = (int) ((1 - transitionSpeed * event.getPartialTick()) * prevBarWidth + transitionSpeed * event.getPartialTick() * barWidth);
//                        prevBarWidthMap.put(boss, barWidth);
//
//                        String displayName = boss.getDisplayName().getString();
//                        String displayHealth = String.format("%.1f/%.1f", health, maxHealth);
//                        int displayNameWidth = font.width(displayName);
//                        int healthWidth = font.width(displayHealth);
//
//                        graphics.blit(boss.getBossBar(), posX - 97, posY - 116 + offset, 0.0f, 0.0f, 256, 256, 256, 256);
//                        if (boss.isHasMask()) graphics.blit(boss.getBossBarOverlay(), posX - 97, posY - 148 + offset, 0.0f, 0.0f, interpolatedBarWidth, 256, 256, 256);
//                        graphics.blit(boss.getBossBarOn(), posX - 97, posY - 148 + offset, 0.0f, 0.0f, barWidth, 256, 256, 256);
//                        source.getBuffer(RenderType.endPortal());
//                        graphics.drawString(font, Component.literal(displayName), posX - (displayNameWidth / 2), posY + -92 + offset, -26368, false);
//                        graphics.drawString(font, Component.literal(displayHealth), posX - (healthWidth / 2), posY + -82 + offset, -26368, false);
//                        offset += 30;
//                    }
//                }
//            }
//            RenderSystem.depthMask(true);
//            RenderSystem.defaultBlendFunc();
//            RenderSystem.enableDepthTest();
//            RenderSystem.disableBlend();
//            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//        }
//    }

//
//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
//    public static void renderTooltipEventPre(RenderTooltipEvent.Pre event) {
//        Random random = new Random(Util.getMillis());
//        UseCountItem stack = event.getItemStack();
//        Item item = stack.getItem();
//        GuiGraphics graphics = event.getGraphics();
//        PoseStack poseStack = graphics.pose();
//        Color rgb = new Color(random.nextInt(0, 255), random.nextInt(0,255), random.nextInt(0, 255));
//        int x = event.getX();
//        int y = event.getY();
//        if (Utils.isNormalTool(item)) {
//            Render2DHelper.drawRound(poseStack,x, y, 100, 100, 9, Color.WHITE);
//        }
//        else if (Utils.isSuperTool(item)){
//            Render2DHelper.drawRound(poseStack,x, y, 100, 100, 9, rgb);
//        }
//        else if (Utils.isCreativeItem(item)) {
//            Render2DHelper.drawBlurredShadow(poseStack, x, y, 100, 100, 15, rgb);
//        }
//        else if (item.equals(DCTestItem.DC_ENTITY_REMOVE.getUse())) {
//            Render2DHelper.drawGradientRound(poseStack, x, y, 100, 100, 15, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
//        }
//    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderTooltipEventColor(RenderTooltipEvent.Color event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        int c = ColorHelper.rainBowColor();
        if (Config.Client.tool_tip_render.get()) {
            if (Utils.isSuperTool(stack) || Utils.isLightItem(stack)){
                event.setBorderStart(c);
                event.setBorderEnd(c);
                if (Config.Client.tool_tip_background_color.get()) {
                    int color;
                    ToolTipColor eum = Config.Client.tool_tip_background_color_get.get();
                    color = switch (eum) {
                        case BLUE -> Color.BLUE.getRGB();
                        case RED -> Color.RED.getRGB();
                        case GREEN -> Color.GREEN.getRGB();
                        case CYAN -> Color.CYAN.getRGB();
                        case WHITE -> Color.WHITE.getRGB();
                        case ORANGE -> Color.ORANGE.getRGB();
                        case YELLOW -> Color.YELLOW.getRGB();
                        case GRAY -> Color.GRAY.getRGB();
                        case PINK -> Color.PINK.getRGB();
                        case RAINBOW -> ColorHelper.rainBowColor();
                    };
                    event.setBackgroundEnd(color);
                    event.setBackgroundStart(color);
                }
            }
            else if (Utils.isCreativeItem(stack)) {
                event.setBorderStart(Color.CYAN.getRGB());
                event.setBorderEnd(Color.CYAN.getRGB());
            }
            else if (Utils.isNormalTool(stack)) {
                event.setBorderStart(Color.WHITE.getRGB());
                event.setBorderEnd(Color.WHITE.getRGB());
            }
            else if (item.equals(DCItems.DC_ENTITY_REMOVE.get())) {
                event.setBorderStart(new Random(Util.getMillis()).nextInt(0, 255));
                event.setBorderEnd(new Random(Util.getMillis()).nextInt(0,255));
            }
            else if (Utils.isBlockItem(stack)) {
                event.setBorderStart(Color.BLUE.getRGB());
                event.setBorderStart(Color.BLUE.getRGB());
            }
            else if (ModUtil.isDCLoad() && FontUtil.isCanRenderFont(stack)) {
                event.setBorderStart(c);
                event.setBorderEnd(c);
                }
            }
    }

//    @OnlyIn(Dist.CLIENT)
//    @SubscribeEvent
//    public static void renderTooltipEventPre(RenderTooltipEvent.Pre event) {
//        addRotate(1);
//        UseCountItem stack = event.getItemStack();
//        GuiGraphics graphics = event.getGraphics();
//        MultiBufferSource bufferSource = graphics.bufferSource();
//        PoseStack pose = graphics.pose();
//        Matrix4f matrix4f = pose.last().pose();
//        if (Utils.isSuperTool(stack)) {
//            graphics.blit(DCMod.getDCGUILocation("star.png"), event.getX(), event.getY(), 200, 200, 200, 200);
//            matrix4f.rotateY(getRotate());
//        }
//    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher()
                .register(Commands.literal("dc_mods")
                        .then(Commands.literal("setTarget")
                                .then(Commands.argument("attacker", EntityArgument.entity())
                                        .then(Commands.argument("target", EntityArgument.entity())
                                                .executes(cs ->{
                                                    Entity attacker = EntityArgument.getEntity(cs, "attacker");
                                                    Entity target = EntityArgument.getEntity(cs, "target");
                                                    if (attacker instanceof Monster monster && target instanceof LivingEntity living) monster.setTarget(living);
                                                    cs.getSource().sendSuccess(()-> Component.translatable("dc_mod.command.set_target", attacker.getDisplayName(), target.getDisplayName()), true);
                                                    return 2;
                                                })
                                        )
                                )
                        )
                        .then(Commands.literal("forceGetHealthSet")
                                .executes(cs->{
                                    Entity entity = cs.getSource().getEntity();
                                    SoftGetHealthCommand.killed(entity);
                                    return 2;
                                })
                                .then(Commands.argument("entities", EntityArgument.entity()).executes(cs->{
                                    for (Entity entity : EntityArgument.getEntities(cs, "entities")) SoftGetHealthCommand.killed(entity);
                                    return 2;
                                })))
                        .then(Commands.literal("forceKillEntity")
                                .executes(cs->{
                                    Entity entity = cs.getSource().getEntity();
                                    DCLoliPickaxe.killEntity(entity, entity);
                                    cs.getSource().sendSuccess(()->Component.translatable("dc_mod.command.force_kill_entity", entity.getDisplayName()), true);
                                    return 2;
                                })
                                .then(Commands.argument("radius", FloatArgumentType.floatArg(0, Float.MAX_VALUE))
                                        .executes(cs->{
                                            int killCount = 0;
                                            ServerPlayer p = cs.getSource().getPlayer();
                                            double x = p.getX();
                                            double y = p.getY();
                                            double z = p.getY();
                                            for (Entity entity : EntityHelper.getEntity(cs.getSource().getLevel(),x,y,z, FloatArgumentType.getFloat(cs, "radius") * 10)) {
                                                if (entity != p) {
                                                    DCLoliPickaxe.killEntity(entity, entity);
                                                    entity.kill();
                                                    killCount++;
                                                }
                                            }
                                            int finalKillCount = killCount;
                                            cs.getSource().sendSuccess(()->Component.translatable("dc_mod.command.force_kill_entity_range", finalKillCount), true);
                                            return 4;
                                        }))
                                .then(Commands.argument("entities", EntityArgument.entities()).executes(cs->{
                                    int killCount = 0;
                                    for (Entity entity : EntityArgument.getEntities(cs, "entities")) {
                                        DCLoliPickaxe.killEntity(entity, entity);
                                        entity.kill();
                                        killCount++;
                                    }
                                    int finalKillCount = killCount;
                                    cs.getSource().sendSuccess(()->Component.translatable("dc_mod.command.force_kill_entity_range", finalKillCount), true);
                                    return 2;
                                })))
                        .then(Commands.literal("forceHurtEntity")
                                .then(Commands.argument("target", EntityArgument.entities())
                                        .then(Commands.argument("value", FloatArgumentType.floatArg(0, Float.MAX_VALUE))
                                                .executes(cs->{
                                                    int count = 0;
                                                    float value = FloatArgumentType.getFloat(cs, "value");
                                                    for (Entity target : EntityArgument.getEntities(cs, "target")) {
                                                        if (target instanceof LivingEntity living) {
                                                            EntityActuallyHurt.getInstance(living, living).dcHurt(value);
                                                            if (value >= living.getMaxHealth()) DCLoliPickaxe.killEntity(living, living);
                                                        }
                                                        else target.hurt(EntityHelper.dc_damage(target), value);
                                                        ++count;
                                                    }
                                                    int finalCount = count;
                                                    cs.getSource().sendSuccess(() -> Component.translatable("dc_mod.command.hurt_entity", value, finalCount), true);
                                                    return 0;
                                                })
                                        )))
                        .then(Commands.literal("actuallyHurt")
                                .then(Commands.argument("target", EntityArgument.entities())
                                        .then(Commands.argument("damageType", ResourceArgument.resource(event.getBuildContext(), Registries.DAMAGE_TYPE))
                                                .then(Commands.argument("value", FloatArgumentType.floatArg(0, Float.POSITIVE_INFINITY))
                                                        .executes(cs->{
                                                            int count = 0;
                                                            float value  = FloatArgumentType.getFloat(cs, "value");
                                                            DamageSource source = new DamageSource(ResourceArgument.getResource(cs, "damageType", Registries.DAMAGE_TYPE));
                                                            for (Entity target : EntityArgument.getEntities(cs, "target")) {
                                                                if (target instanceof LivingEntity living) EntityActuallyHurt.getInstance(living).actuallyHurt(source, value);
                                                                else target.gameEvent(GameEvent.ENTITY_DIE);
                                                                count++;
                                                            }
                                                            int finalCount = count;
                                                            cs.getSource().sendSuccess(() -> Component.translatable("dc_mod.command.hurt_entity", value, finalCount), true);
                                                            return 2;
                                                        })
                                                )
                                        )
                                )
                        )
                );
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        event.getEntity().addEffect(EffectHelper.addEffect(DCEffects.EnchantressMercy.get(), 4, 1));
    }

    @SubscribeEvent
    public static void sendMessageOfPlayer(PlayerEvent.PlayerLoggedInEvent e){
        Player player = e.getEntity();
        player.displayClientMessage(Component.translatable("chat.dc_mods.world_loading"), false);
        player.displayClientMessage(Component.translatable("chat.dc_mods.player_loading_mod"), false);
        if (ModUtil.isTconstructLoad() && !ModUtil.isEtstLoad()) player.displayClientMessage(Component.translatable("chat.dc_mods.etst_load_faile"), true);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderTooltipEvent(ItemTooltipEvent tooltipEvent){
        if (tooltipEvent.getItemStack().getItem() == DCItems.LoliPickaxe.get()) {
            List<Component> tooltip = tooltipEvent.getToolTip();
            int size = tooltip.size();
            MutableComponent mutableComponent1 = Component.translatable("attribute.name.generic.attack_damage");
            MutableComponent mutableComponent2 = Component.translatable("attribute.name.generic.attack_speed");
            MutableComponent mutableComponent3 = Component.literal(ChatFormatting.DARK_GREEN + "(TREE)3 " +  mutableComponent1.getString());
            MutableComponent mutableComponent4 = Component.literal(ChatFormatting.DARK_GREEN + "(TREE)3 " + mutableComponent2.getString());
            for (int i = 0; i < size; i++) {
                Component line = tooltip.get(i);
                if (line.contains(mutableComponent1)) tooltip.set(i, mutableComponent3);
                if (line.contains(mutableComponent2)) tooltip.set(i, mutableComponent4);
            }
        }
    }

    @SubscribeEvent
    public static void anviUpdate(AnvilUpdateEvent event) {
        AnviUtil.addAnviUpdate(event, Items.IRON_INGOT, Items.DIAMOND, DCItems.SUPER_WOOD_INGOT.get());
    }

    @SubscribeEvent
    public static void livingDeathEvent(LivingDeathEvent event) {
        LivingEntity living = event.getEntity();
        Level level = living.level();
        double x = living.getX();
        double y = living.getY();
        double z = living.getZ();
        if (living.getType() == EntityType.TROPICAL_FISH && Config.Server.ocean_heart.get()) {
            double random = MathHelper.getRandomDouble(0.00D, 1.00D);
            if (random == 0.01D) {
                ItemEntity item = new ItemEntity(living.level(), x, y, z, new ItemStack(DCItems.HEART_OF_THE_OCEAN.get()));
                item.setPickUpDelay(0);
                level.addFreshEntity(item);
            }
        }
        if (DCLoliPickaxe.isHasLoliPickaxe(living)) event.setCanceled(true);
        if (living instanceof Player player && DCSuperArmor.hasAllArmor(player) && player.isUnderWater()) {
            event.setCanceled(true);
            player.playSound(SoundEvents.TOTEM_USE);
        }
    }

    @SubscribeEvent
    public static void livingTickEvent(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();
        if (living.tickCount % 100 == 0 && DataHelper.getHealthDelta(living) < 0 && living.isAlive() && EntityHelper.isOnHurt(living) && !DataHelper.isDead(living))
            DataHelper.addHealthDelta(living, 1.0F);
    }

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (Utils.isBlocking(player) && player.tickCount % 100 == 0 && DataHelper.getHealthDelta(player) < 0 && player.isAlive() && !DataHelper.isDead(player))
            DataHelper.addHealthDelta(player, Config.Common.heal_count.get());
    }

    @SubscribeEvent
    public static void totemUse(LivingUseTotemEvent event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player) {
            ItemEntity item = new ItemEntity(living.level(), living.getX(), living.getY(), living.getZ(), new ItemStack(Items.PLAYER_HEAD));
            item.setPickUpDelay(0);
            try {
                living.level.addFreshEntity(item);
            } catch (Exception ignored){}
        }
    }


    @OnlyIn(Dist.CLIENT)
    public enum ToolTipColor {
        WHITE,
        ORANGE,
        BLUE,
        RED,
        YELLOW,
        CYAN,
        GREEN,
        PINK,
        GRAY,
        RAINBOW
    }
}
