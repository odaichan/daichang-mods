package net.daichang.dcmods.addons.curios.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.daichang.dcmods.client.font.DCOceanItemFont;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCUuid;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;
import java.util.function.Consumer;

public class OceanLoce extends Item implements ICurioItem {
    public Multimap<Attribute, AttributeModifier> multimap;

    public OceanLoce() {
        super(new Properties().stacksTo(1).fireResistant());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> mainHand = ImmutableMultimap.builder();
        mainHand.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 23.2D, AttributeModifier.Operation.MULTIPLY_BASE));
        mainHand.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 12.34D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 0.2D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(DCUuid.BASE_SNOW_DAMAGE_UUID, "Item modifier", 5.2D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(DCAttributes.DC_DEFENSE.get(), new AttributeModifier(DCUuid.BASE_SNOW_RESIT_UUID, "Item modifier", 5.20D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(DCAttributes.OCEAN_DAMAGE.get(), new AttributeModifier(DCUuid.BASE_OCEAN_DAMAGE_UUID, "Item modifier", 25.20D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 0.4D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 0.4D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        multimap = mainHand.build();
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return multimap;
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
        if (livingEntity.isAlive() && DataHelper.getHealthDelta(livingEntity) < 0) {
            DataHelper.addHealthDelta(livingEntity, 20.0F);
            EntityHelper.forceHeal(livingEntity, 49.0F);
        }
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return super.isFoil(pStack);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCOceanItemFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
