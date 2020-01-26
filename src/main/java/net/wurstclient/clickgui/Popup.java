/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.clickgui;

public abstract class Popup {
	private final Component owner;

	private int x;
	private int y;
	private int width;
	private int height;
	private boolean closing;

	public Popup(Component owner) {
		this.owner = owner;
	}

	public void close() {
		closing = true;
	}

	public abstract int getDefaultHeight();

	public abstract int getDefaultWidth();

	public int getHeight() {
		return height;
	}

	public Component getOwner() {
		return owner;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public abstract void handleMouseClick(int mouseX, int mouseY, int mouseButton);

	public boolean isClosing() {
		return closing;
	}

	public abstract void render(int mouseX, int mouseY);

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
