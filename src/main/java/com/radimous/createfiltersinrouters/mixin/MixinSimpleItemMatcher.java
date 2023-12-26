package com.radimous.createfiltersinrouters.mixin;

import com.simibubi.create.content.logistics.filter.FilterItem;
import me.desht.modularrouters.logic.filter.Filter;
import me.desht.modularrouters.logic.filter.matchers.SimpleItemMatcher;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SimpleItemMatcher.class, remap = false)
public class MixinSimpleItemMatcher {
    @Shadow
    @Final
    private ItemStack filterStack;

    @Inject(method = "matchItem", at = @At("HEAD"), cancellable = true)
    public void createFilterMatcher(ItemStack stack, Filter.Flags flags, CallbackInfoReturnable<Boolean> cir) {
        if (filterStack.getItem() instanceof FilterItem) {
            cir.setReturnValue(FilterItem.test(null, stack, this.filterStack, !flags.isIgnoreNBT()));
        }
    }
}
