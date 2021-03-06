/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.options;

import org.lwjgl.glfw.GLFW;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ListWidget;
import net.minecraft.text.LiteralText;
import net.wurstclient.WurstClient;
import net.wurstclient.keybinds.Keybind;
import net.wurstclient.keybinds.KeybindList;

public final class KeybindManagerScreen extends Screen {
	private static final class ListGui extends ListWidget {
		private int selected = -1;

		public ListGui(MinecraftClient mc, int width, int height, int top, int bottom, int slotHeight) {
			super(mc, width, height, top, bottom, slotHeight);
		}

		@Override
		protected int getItemCount() {
			return WurstClient.INSTANCE.getKeybinds().getAllKeybinds().size();
		}

		@Override
		protected boolean isSelectedItem(int index) {
			return selected == index;
		}

		@Override
		protected void renderBackground() {

		}

		@Override
		protected void renderItem(int index, int x, int y, int slotHeight, int mouseX, int mouseY, float partialTicks) {
			Keybind keybind = WurstClient.INSTANCE.getKeybinds().getAllKeybinds().get(index);

			minecraft.textRenderer.draw("Key: " + keybind.getKey().replace("key.keyboard.", ""), x + 3, y + 3, 0xa0a0a0);
			minecraft.textRenderer.draw("Commands: " + keybind.getCommands(), x + 3, y + 15, 0xa0a0a0);
		}

		@Override
		protected boolean selectItem(int index, int int_2, double var3, double var4) {
			if (index >= 0 && index < getItemCount()) {
				selected = index;
			}

			return true;
		}
	}

	private final Screen prevScreen;
	private ListGui listGui;
	private ButtonWidget addButton;
	private ButtonWidget editButton;
	private ButtonWidget removeButton;

	private ButtonWidget backButton;

	public KeybindManagerScreen(Screen prevScreen) {
		super(new LiteralText(""));
		this.prevScreen = prevScreen;
	}

	private void edit() {
		Keybind keybind = WurstClient.INSTANCE.getKeybinds().getAllKeybinds().get(listGui.selected);
		minecraft.openScreen(new KeybindEditorScreen(this, keybind.getKey(), keybind.getCommands()));
	}

	@Override
	public void init() {
		listGui = new ListGui(minecraft, width, height, 36, height - 56, 30);

		addButton(addButton = new ButtonWidget(width / 2 - 102, height - 52, 100, 20, "Add", b -> minecraft.openScreen(new KeybindEditorScreen(this))));

		addButton(editButton = new ButtonWidget(width / 2 + 2, height - 52, 100, 20, "Edit", b -> edit()));

		addButton(removeButton = new ButtonWidget(width / 2 - 102, height - 28, 100, 20, "Remove", b -> remove()));

		addButton(backButton = new ButtonWidget(width / 2 + 2, height - 28, 100, 20, "Back", b -> minecraft.openScreen(prevScreen)));

		addButton(new ButtonWidget(8, 8, 100, 20, "Reset Keybinds", b -> minecraft.openScreen(new ConfirmScreen(confirmed -> {
			if (confirmed) {
				WurstClient.INSTANCE.getKeybinds().setKeybinds(KeybindList.DEFAULT_KEYBINDS);
			}
			minecraft.openScreen(this);
		}, new LiteralText("Are you sure you want to reset your keybinds?"), new LiteralText("This cannot be undone!")))));
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int int_3) {
		if (keyCode == GLFW.GLFW_KEY_ENTER)
			if (editButton.active) {
				editButton.onPress();
			} else {
				addButton.onPress();
			}
		else if (keyCode == GLFW.GLFW_KEY_DELETE) {
			removeButton.onPress();
		} else if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			backButton.onPress();
		}

		return super.keyPressed(keyCode, scanCode, int_3);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
		boolean childClicked = super.mouseClicked(mouseX, mouseY, mouseButton);

		listGui.mouseClicked(mouseX, mouseY, mouseButton);

		if (!childClicked)
			if (mouseY >= 36 && mouseY <= height - 57)
				if (mouseX >= width / 2 + 140 || mouseX <= width / 2 - 126) {
					listGui.selected = -1;
				}

		return childClicked;
	}

	@Override
	public boolean mouseDragged(double double_1, double double_2, int int_1, double double_3, double double_4) {
		listGui.mouseDragged(double_1, double_2, int_1, double_3, double_4);
		return super.mouseDragged(double_1, double_2, int_1, double_3, double_4);
	}

	@Override
	public boolean mouseReleased(double double_1, double double_2, int int_1) {
		listGui.mouseReleased(double_1, double_2, int_1);
		return super.mouseReleased(double_1, double_2, int_1);
	}

	@Override
	public boolean mouseScrolled(double double_1, double double_2, double double_3) {
		listGui.mouseScrolled(double_1, double_2, double_3);
		return super.mouseScrolled(double_1, double_2, double_3);
	}

	private void remove() {
		Keybind keybind1 = WurstClient.INSTANCE.getKeybinds().getAllKeybinds().get(listGui.selected);
		WurstClient.INSTANCE.getKeybinds().remove(keybind1.getKey());
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		listGui.render(mouseX, mouseY, partialTicks);

		drawCenteredString(font, "Keybind Manager", width / 2, 8, 0xffffff);
		drawCenteredString(font, "Keybinds: " + listGui.getItemCount(), width / 2, 20, 0xffffff);

		super.render(mouseX, mouseY, partialTicks);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}

	@Override
	public void tick() {
		boolean inBounds = listGui.selected > -1 && listGui.selected < listGui.getItemCount();

		editButton.active = inBounds;
		removeButton.active = inBounds;
	}
}
