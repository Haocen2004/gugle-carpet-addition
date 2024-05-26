package dev.dubhe.gugle.carpet.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
abstract class AbstractContainerMenuMixin {
    @Unique
    AbstractContainerMenu gca$self = (AbstractContainerMenu)(Object)this;

    @Inject(method = "doClick", at = @At("HEAD"), cancellable = true)
    private void doClick(int mouseX, int mouseY, ClickType clickType, Player player, CallbackInfo ci) {
        if (mouseX < 0) return;
        Slot slot = gca$self.getSlot(mouseX);
        ItemStack itemStack = slot.getItem();
        if (itemStack.getComponents().get(DataComponents.CUSTOM_DATA) != null) {
            try {

                if (itemStack.getComponents().get(DataComponents.CUSTOM_DATA).contains("GcaClear")) {
                    itemStack.setCount(0);
                }
            } catch (Exception ignore) {}
        }
//        if (itemStack.getTag() != null) {
//            if (itemStack.getTag().get("GcaClear") != null) {
//                if (itemStack.getTag().getBoolean("GcaClear")) {
//                    itemStack.setCount(0);
//                    ci.cancel();
//                }
//            }
//        }
    }
}
