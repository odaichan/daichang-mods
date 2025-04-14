package net.daichang.dcmods.mixins;

import net.daichang.dcmods.inits.DCEnch;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;

import static net.minecraft.world.level.block.LiquidBlock.STABLE_SHAPE;


@Mixin(LiquidBlock.class)
public abstract class MixinLiquidBlock extends Block {
    @Shadow @Final public static IntegerProperty LEVEL;

    public MixinLiquidBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Unique
    boolean daichangmod$isWalkPlayer(Player player) {
        return player.getInventory().armor.get(3).getItem().getAllEnchantments(player.getInventory().armor.get(3).getItem().getDefaultInstance()).containsKey(DCEnch.LIQUID_WALK.get());
    }

    /**
     * @author
     * @reason
     */
    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public @NotNull VoxelShape getCollisionShape(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && daichangmod$isWalkPlayer(mc.player)) {
            return Shapes.block();
        } else {
            return p_54763_.isAbove(STABLE_SHAPE, p_54762_, true) && p_54760_.getValue(LEVEL) == 0 && p_54763_.canStandOnFluid(p_54761_.getFluidState(p_54762_.above()), p_54760_.getFluidState()) ? STABLE_SHAPE : Shapes.empty();
        }
    }
}
