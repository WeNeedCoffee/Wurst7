package net.wurstclient.hacks;

import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.hack.Hack;
import net.wurstclient.settings.SliderSetting;

@SearchTags({ "auto store"})
public final class AutoStoreHack extends Hack {
	private final SliderSetting delay = new SliderSetting("Delay", "Delay between moving stacks of items.\n" + "Should be at least 70ms for NoCheat+ servers.", 100, 0, 2000, 10, v -> (int) v + "ms");
	public boolean forced = false;
	public AutoStoreHack() {
		super("AutoStore", "Automatically stores everything\n" + "into all chests that you open.");
		setCategory(Category.ITEMS);
		addSetting(delay);
	}

	public long getDelay() {
		return delay.getValueI();
	}
	@Override
	public void onEnable() {
		WURST.getHax().autoStealHack.setEnabled(false);
	}
	
	@Override
	public void onDisable() {
		forced = false;
	}
}