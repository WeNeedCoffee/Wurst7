package net.wurstclient.hacks;

import net.minecraft.block.Blocks;
import net.minecraft.container.PlayerContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.wurstclient.WurstClient;
import net.wurstclient.events.UpdateListener;
import net.wurstclient.hack.Hack;
import net.wurstclient.mixinterface.IClientPlayerInteractionManager;
import net.wurstclient.util.BlockUtils;
import net.wurstclient.util.RotationUtils;

public class LavaFillHack extends Hack implements UpdateListener {

	public static int hasBlock() {
		if (!(MC.player.container instanceof PlayerContainer)) {
			return -1;
		}
		int slot = 44;
		if (MC.player.container.getSlot(slot) != null && MC.player.container.getSlot(slot).getStack() != null && MC.player.container.getSlot(slot).getStack().getItem().getGroup() != null && MC.player.container.getSlot(slot).getStack().getItem().getGroup().equals(ItemGroup.BUILDING_BLOCKS))
			return slot;
		for (slot = 9; slot < 35; slot++) {
			if (MC.player.container.getSlot(slot).getStack() != null && MC.player.container.getSlot(slot).getStack().getItem() != null && MC.player.container.getSlot(slot).getStack().getItem().getGroup() != null && MC.player.container.getSlot(slot).getStack().getItem().getGroup().equals(ItemGroup.BUILDING_BLOCKS))
				return slot;
		}
		return -1;
	}

	public static void swapSlot(int item, int hotbar) {
		IMC.getInteractionManager().windowClick_PICKUP(item);
		IMC.getInteractionManager().windowClick_PICKUP(hotbar);
		IMC.getInteractionManager().windowClick_PICKUP(item);

	}

	int t = 0;

	public LavaFillHack() {
		super("LavaFill", "Fills lava with blocks");
		// TODO Auto-generated constructor stub
	}

	private boolean buildInstantly(BlockPos pos) {
		Vec3d eyesPos = RotationUtils.getEyesPos();
		IClientPlayerInteractionManager im = IMC.getInteractionManager();
		double rangeSq = Math.pow(5, 2);
		if (!BlockUtils.getState(pos).getMaterial().isReplaceable())
			return false;
		int g = hasBlock();
		if (g == -1)
			return false;
		else if (g < 35) {
			swapSlot(g, 44);
		}
		g = 8;
		Vec3d posVec = new Vec3d(pos).add(0.5, 0.5, 0.5);

		for (Direction side : Direction.values()) {
			BlockPos neighbor = pos.offset(side);

			// check if neighbor can be right-clicked
			if (!BlockUtils.canBeClicked(neighbor)) {
				continue;
			}

			Vec3d sideVec = new Vec3d(side.getVector());
			Vec3d hitVec = posVec.add(sideVec.multiply(0.5));

			// check if hitVec is within range
			if (eyesPos.squaredDistanceTo(hitVec) > rangeSq) {
				continue;
			}
			int i = MC.player.inventory.selectedSlot;

			MC.player.inventory.selectedSlot = g;
			// place block
			im.rightClickBlock(neighbor, side.getOpposite(), hitVec);
			MC.player.inventory.selectedSlot = i;
			return true;
		}
		return false;
	}

	@Override
	public void onDisable() {
		EVENTS.remove(UpdateListener.class, this);
	}

	@Override
	public void onEnable() {
		EVENTS.add(UpdateListener.class, this);
	}

	@Override
	public void onUpdate() {
		t++;
		if (t > 2 && hasBlock() != -1) {
			int reach = 3;
			// TODO Auto-generated method stub
			int x = WurstClient.MC.player.getBlockPos().getX();
			int y = WurstClient.MC.player.getBlockPos().getY();
			int z = WurstClient.MC.player.getBlockPos().getZ();
			for (int xx = -reach; xx <= reach; xx++) {
				for (int yy = -reach; yy <= reach; yy++) {
					for (int zz = -reach; zz <= reach; zz++) {
						BlockPos pos = new BlockPos(xx + x, yy + y, zz + z);
						if (BlockUtils.getBlock(pos).equals(Blocks.LAVA)) {
							if (buildInstantly(pos)) {
								t = 0;
								return;
							}
						}
					}
				}
			}
			t = 0;
		}
	}

}
