package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.container.AnvilContainer;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.text.Text;
import net.wurstclient.WurstClient;
import net.wurstclient.util.BlockUtils;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin  extends ContainerScreen<AnvilContainer>{

	public AnvilScreenMixin(AnvilContainer container, PlayerInventory playerInventory, Text name) {
		super(container, playerInventory, name);
	}

	@Shadow
	private TextFieldWidget nameField;

	
	@Inject(at = { @At("HEAD") }, method = { "onRenamed(Ljava/lang/String;)V" }, cancellable = true)
	private void onRenamed(String name, CallbackInfo ci) {
		if (!name.isEmpty()) {
			String string = name;
			Slot slot = ((AnvilContainer) this.getContainer()).getSlot(0);
			if (slot.hasStack() && slot.getStack().getItem().equals(Items.SHULKER_BOX)) {
				if (slot != null && slot.hasStack() && !slot.getStack().hasCustomName() && name.equals(slot.getStack().getName().getString())) {
					string = "";
				}
				String ee = string.replace(" ", "_").split(":")[0].replace("[^a-zA-Z0-9/-]+", "").toLowerCase();
				Item t = BlockUtils.getItemOrBlock(ee).asItem();
				if (!t.equals(Items.AIR)) {
					String e = BlockUtils.getID(t);
					if (!e.equalsIgnoreCase("0i")) {
						int l = string.split(":")[0].length();
						String f = string.substring(l);
						string = e + (f.isEmpty() ? "" : ":" + f);
						 this.nameField.setChangedListener(null);
						nameField.setText(string);
						 this.nameField.setChangedListener(this::onRenamed);
					}
				}
				((AnvilContainer) this.getContainer()).setNewItemName(string);
				WurstClient.MC.player.networkHandler.sendPacket(new RenameItemC2SPacket(string));
			}
		}
		ci.cancel();
	}
	
	@Shadow
	private void onRenamed(String s) {
		
	}
}
