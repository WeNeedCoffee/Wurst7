/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.gui.screen.ingame.ContainerProvider;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.container.Container;
import net.minecraft.container.GenericContainer;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.wurstclient.WurstClient;
import net.wurstclient.hacks.AutoStealHack;
import net.wurstclient.hacks.AutoStoreHack;

@Mixin(GenericContainerScreen.class)
public abstract class ContainerScreen54Mixin extends ContainerScreen<GenericContainer> implements ContainerProvider<GenericContainer> {
	@Shadow
	@Final
	private int rows;

	private final AutoStealHack autoSteal = WurstClient.INSTANCE.getHax().autoStealHack;
	private final AutoStoreHack autoStore = WurstClient.INSTANCE.getHax().autoStoreHack;
	private int mode;
	private boolean working = false;

	public ContainerScreen54Mixin(WurstClient wurst, GenericContainer container, PlayerInventory playerInventory, Text name) {
		super(container, playerInventory, name);
	}

	@Override
	protected void init() {
		super.init();

		if (!WurstClient.INSTANCE.isEnabled())
			return;

		if (autoSteal.areButtonsVisible()) {
			addButton(new ButtonWidget(x + containerWidth - 108, y + 4, 50, 12, "Steal", b -> steal()));

			addButton(new ButtonWidget(x + containerWidth - 56, y + 4, 50, 12, "Store", b -> store()));
		}

		if (!working && autoSteal.isEnabled()) {
			steal();
		}
		
		if (!working && autoStore.isEnabled()) {
			store();
		}
	}

	private void runInThread(Runnable r) {
		new Thread(() -> {
			try {
				r.run();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void shiftClickSlots(int from, int to, int mode) {
		this.mode = mode;
		working = true;
		for (int i = from; i < to; i++) {
			Slot slot = container.slots.get(i);
			if (slot.getStack().isEmpty()) {
				continue;
			}
			boolean can = false;
			for (int e = (mode == 1 ? rows * 9 : 0); e < (mode == 1 ? rows * 9 + 36 : rows * 9); e++) {
				if (Container.canInsertItemIntoSlot(container.slots.get(e), slot.getStack(), true)) { 
					can = true;
					break;
				}
			}
			if (!can) {
				continue;
			}
			waitForDelay();
			if (this.mode != mode || minecraft.currentScreen == null) {
				break;
			}

			onMouseClick(slot, slot.id, 0, SlotActionType.QUICK_MOVE);
		}
		if (autoStore.forced) {
			autoStore.forced = false;
			autoStore.setEnabled(false);
		}
		working = false;
	}

	private void steal() {
		runInThread(() -> shiftClickSlots(0, rows * 9, 1));
	}

	private void store() {
		runInThread(() -> shiftClickSlots(rows * 9, rows * 9 + 27, 2));
	}

	private void waitForDelay() {
		try {
			long delay = autoSteal.isEnabled() ? autoSteal.getDelay() : autoStore.getDelay();
			System.out.println(delay);
			Thread.sleep(delay);

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
