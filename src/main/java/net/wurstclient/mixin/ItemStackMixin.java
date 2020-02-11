package net.wurstclient.mixin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

@Mixin(ItemStack.class)
public class ItemStackMixin {

	@Shadow
	Item getItem() {
		return null;
	}

	@Shadow
	CompoundTag getSubTag(String s) {
		return null;
	}

	@Shadow
	List<Text> getTooltip(@Nullable PlayerEntity player, TooltipContext context) {
		return null;
	}
	@Inject(at = { @At("HEAD") }, method = { "getTooltip(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/client/item/TooltopContext;)Ljava/util/List;" }, cancellable = true)
	public void getTooltips(PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir) {
		List<Text> r = new ArrayList<Text>();
		if (getName().asString().split(":").length > 1 && getItem().equals(Items.SHULKER_BOX)) {
			r.add(new LiteralText(getName().asString().split(":")[1].replace("$", "\u00a7")));
			cir.setReturnValue(r);
			cir.cancel();
		}
	}
	@Shadow
	public Text getName() {
		return null;
		
	}
}
