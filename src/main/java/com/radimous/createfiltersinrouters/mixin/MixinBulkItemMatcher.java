package com.radimous.createfiltersinrouters.mixin;

import com.simibubi.create.content.logistics.filter.FilterItem;
import me.desht.modularrouters.logic.filter.Filter;
import me.desht.modularrouters.logic.filter.matchers.BulkItemMatcher;
import me.desht.modularrouters.util.SetofItemStack;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BulkItemMatcher.class, remap = false)
public class MixinBulkItemMatcher {
    @Shadow @Final private SetofItemStack stacks;

    @Inject(method = "matchItem", at = @At("HEAD"), cancellable = true)
    public void createItemMatcher(ItemStack stack, Filter.Flags flags, CallbackInfoReturnable<Boolean> cir){
        for (var filter : this.stacks) {
            if (filter.getItem() instanceof FilterItem) {
                if (FilterItem.test(null, stack, filter, !flags.isIgnoreNBT())) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
