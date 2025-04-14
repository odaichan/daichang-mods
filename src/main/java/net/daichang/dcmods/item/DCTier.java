package net.daichang.dcmods.item;

import net.daichang.dcmods.inits.DCItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum DCTier implements Tier {
    NORMAL(4, 2031, 17.0F, 4.0F, 15, () -> Ingredient.of(DCItems.WOOD_INGOT.get())),
    DC_INGOT(37, 12842, 129.0F, 42.0F, 98, () -> Ingredient.of(DCItems.DC_CRAFT.get())),
    SUPERS(6, 4982, 25.3F, 7.0F, 20, ()-> Ingredient.of(DCItems.SUPER_WOOD_INGOT.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    DCTier(int p_43332_, int p_43333_, float p_43334_, float p_43335_, int p_43336_, Supplier<Ingredient> p_43337_) {
        this.level = p_43332_;
        this.uses = p_43333_;
        this.speed = p_43334_;
        this.damage = p_43335_;
        this.enchantmentValue = p_43336_;
        this.repairIngredient = new LazyLoadedValue(p_43337_);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
