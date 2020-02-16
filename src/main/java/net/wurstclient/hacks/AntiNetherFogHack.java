package net.wurstclient.hacks;

import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.hack.Hack;

@SearchTags({ "AntiNetherFogHack", "fog", "no fog", "nether fog" })
public final class AntiNetherFogHack extends Hack {
	public AntiNetherFogHack() {
		super("AntiNetherFogHack", "AntiNetherFogHack");
		setCategory(Category.RENDER);
	}
}
