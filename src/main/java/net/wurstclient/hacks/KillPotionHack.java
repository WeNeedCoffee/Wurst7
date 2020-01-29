/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.hacks;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.packet.CreativeInventoryActionC2SPacket;
import net.minecraft.text.LiteralText;
import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.hack.Hack;
import net.wurstclient.util.ChatUtils;

@SearchTags({ "kill potion", "KillerPotion", "killer potion" })
public final class KillPotionHack extends Hack {
	public KillPotionHack() {
		super("KillPotion", "Generates a potion that can kill anything,\n" + "even players in Creative mode.\n" + "Requires Creative mode.");

		setCategory(Category.ITEMS);
	}

	@Override
	public void onEnable() {
		// check gamemode
		if (!MC.player.abilities.creativeMode) {
			ChatUtils.error("Creative mode only.");
			setEnabled(false);
			return;
		}

		// generate potion
		ItemStack stack = new ItemStack(Items.SPLASH_POTION);
		CompoundTag effect = new CompoundTag();
		effect.putInt("Amplifier", 125);
		effect.putInt("Duration", 2000);
		effect.putInt("Id", 6);
		ListTag effects = new ListTag();
		effects.add(effect);
		CompoundTag nbt = new CompoundTag();
		nbt.put("CustomPotionEffects", effects);
		stack.setTag(nbt);
		String name = "\u00a7rSplash Potion of \u00a74\u00a7lINSTANT DEATH";
		stack.setCustomName(new LiteralText(name));

		// give potion
		if (placeStackInHotbar(stack))
			ChatUtils.message("Potion created.");
		else
			ChatUtils.error("Please clear a slot in your hotbar.");

		setEnabled(false);
	}

	private boolean placeStackInHotbar(ItemStack stack) {
		for (int i = 0; i < 9; i++) {
			if (!MC.player.inventory.getInvStack(i).isEmpty())
				continue;

			MC.player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(36 + i, stack));
			return true;
		}

		return false;
	}
}