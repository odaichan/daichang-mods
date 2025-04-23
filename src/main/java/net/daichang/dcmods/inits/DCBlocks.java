package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.blocks.RedSpiderLily;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DCBlocks {
    public static final DeferredRegister<Block> block = DeferredRegister.create(ForgeRegistries.BLOCKS, DCMod.MOD_ID);

    private static RegistryObject<Block> register(String register_id, Supplier<? extends Block> blockClazz) {
       return block.register(register_id, blockClazz);
    }

    public static final RegistryObject<Block> RED_SPIDER_LILY;
    public static final RegistryObject<Block> CurseTheSoil;

    static {
        RED_SPIDER_LILY = register("red_spider_lily", () -> new RedSpiderLily(DCEffects.Bloodshed, 10, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
        CurseTheSoil = register("curse_the_soil", ()->new Block(BlockBehaviour.Properties.of()));
    }
}
