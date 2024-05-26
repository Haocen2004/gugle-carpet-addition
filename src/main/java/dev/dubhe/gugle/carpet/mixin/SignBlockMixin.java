package dev.dubhe.gugle.carpet.mixin;

import dev.dubhe.gugle.carpet.GcaSetting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SignBlock.class)
abstract class SignBlockMixin {
    @Unique
    private final SignBlock gca$self = (SignBlock) (Object) this;

    @Inject(method = "useItemOn", at = @At(value = "INVOKE"), cancellable = true)
    public void useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult,CallbackInfoReturnable<ItemInteractionResult> cir) {
        if (GcaSetting.betterSignInteraction && gca$self instanceof WallSignBlock) {
            Direction direction = state.getValue(WallSignBlock.FACING);
            BlockPos blockPos = pos.relative(direction, -1);
            BlockState blockState = level.getBlockState(blockPos);
            BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(blockPos), direction, blockPos, false);
            if (blockState.getBlock() instanceof WallSignBlock) return ;
            else blockState.useItemOn(itemStack, level, player, hand, hitResult);
            cir.setReturnValue(ItemInteractionResult.SUCCESS);
        }
//        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
