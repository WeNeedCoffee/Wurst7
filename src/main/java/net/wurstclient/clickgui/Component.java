/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.clickgui;

public abstract class Component {
	private int x;
	private int y;
	private int width;
	private int height;

	private Window parent;

	public abstract int getDefaultHeight();

	public abstract int getDefaultWidth();

	public int getHeight() {
		return height;
	}

	public Window getParent() {
		return parent;
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

	public void handleMouseClick(double mouseX, double mouseY, int mouseButton) {

	}

	private void invalidateParent() {
		if (parent != null) {
			parent.invalidate();
		}
	}

	public abstract void render(int mouseX, int mouseY, float partialTicks);

	public void setHeight(int height) {
		if (this.height != height) {
			invalidateParent();
		}

		this.height = height;
	}

	public void setParent(Window parent) {
		this.parent = parent;
	}

	public void setWidth(int width) {
		if (this.width != width) {
			invalidateParent();
		}

		this.width = width;
	}

	public void setX(int x) {
		if (this.x != x) {
			invalidateParent();
		}

		this.x = x;
	}

	public void setY(int y) {
		if (this.y != y) {
			invalidateParent();
		}

		this.y = y;
	}
}
