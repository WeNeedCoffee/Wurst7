package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.dimension.TheNetherDimension;
import net.wurstclient.WurstClient;

@Mixin(TheNetherDimension.class)
public class NetherDimensionMixin {
	@Inject(at = { @At("HEAD") }, method = { "isFogThick(II)Z" })
	public void isFogThick(int x, int z, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(!WurstClient.INSTANCE.getHax().antiNetherFogHack.isEnabled());
	}
}
