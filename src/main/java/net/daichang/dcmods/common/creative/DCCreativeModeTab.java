package net.daichang.dcmods.common.creative;

import net.daichang.dcmods.utils.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import org.jetbrains.annotations.NotNull;

import static net.daichang.dcmods.DCMod.MOD_ID;

public class DCCreativeModeTab extends CreativeModeTab {
    private final ResourceLocation backGround = new ResourceLocation(MOD_ID, "textures/gui/tab_items.png");
    private final ResourceLocation tabsImage = new ResourceLocation(MOD_ID, "textures/gui/tabs.png");

    protected DCCreativeModeTab(Builder builder) {
        super(builder);
    }

    @Override
    public @NotNull ResourceLocation getBackgroundLocation() {
        return backGround;
    }

    @Override
    public @NotNull ResourceLocation getTabsImage() {
        return tabsImage;
    }

    @Override
    public boolean hasSearchBar() {
        return false;
    }

    @Override
    public boolean isAlignedRight() {
        return false;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return TextUtils.rainbow(super.getDisplayName());
    }

    public static class DCBuilder extends Builder {
        public DCBuilder(Row pRow, int pColumn) {
            super(pRow, pColumn);
        }   

        @Override
        public Builder alignedRight() {
            return super.alignedRight();
        }

        @Override
        public Builder backgroundSuffix(String pBackgroundSuffix) {
            return super.backgroundSuffix(pBackgroundSuffix);
        }
    }
}