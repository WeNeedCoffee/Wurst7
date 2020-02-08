package coffee.weneed.minecraft.client.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.wurstclient.WurstClient;
import net.wurstclient.mixinterface.IMinecraftClient;

public class InventoryUtil {

	private static final MinecraftClient MC = WurstClient.MC;
	private static final IMinecraftClient IMC = WurstClient.IMC;

	public static ItemStack getMainHandItem() {
		return MC.player.getMainHandStack();
	}

	public static ItemStack getOffHandItem() {
		return MC.player.getOffHandStack();
	}

	public static boolean hasItem(Item item) {
		return getItemSlot(item) != -1;
	}

	public static PlayerInventory getInventory() {
		return MC.player.inventory;
	}

	public static int getItemSlot(Item item) {
		for (int i = 0; i < getInventory().main.size(); ++i) {
			if (!((ItemStack) getInventory().main.get(i)).isEmpty() && item.equals(getInventory().main.get(i))) {
				return i;
			}
		}
		return -1;
	}
}
