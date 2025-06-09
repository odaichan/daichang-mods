package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.blocks.RedSpiderLily;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DCBlocks {
    public static final List<RegistryObject<Block>> list = new ArrayList<>();
    public static final DeferredRegister<Block> block = DeferredRegister.create(ForgeRegistries.BLOCKS, DCMod.MOD_ID);

    private static RegistryObject<Block> register(String register_id, Supplier<? extends Block> blockClazz) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register block " + register_id);
        RegistryObject<Block> object = block.register(register_id, blockClazz);
        list.add(object);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("block " + register_id + " registered in " + executionTime + " ms");
        return object;
    }

    public static final RegistryObject<Block> RED_SPIDER_LILY;
    public static final RegistryObject<Block> CurseTheSoil;

    static {
        RED_SPIDER_LILY = register("red_spider_lily", RedSpiderLily::new);
        CurseTheSoil = register("curse_the_soil", ()->new Block(BlockBehaviour.Properties.of()));
    }
}
