package net.wurstclient.hacks;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.wurstclient.Category;
import net.wurstclient.SearchTags;
import net.wurstclient.WurstClient;
import net.wurstclient.events.UpdateListener;
import net.wurstclient.hack.Hack;
import net.wurstclient.mixinterface.IClientPlayerInteractionManager;
import net.wurstclient.util.BlockUtils;
import net.wurstclient.util.RotationUtils;
@SearchTags({"AutoTorch", "Torch", "auto torch", "torch"})
public final class AutoTorchHack extends Hack implements UpdateListener {

	public AutoTorchHack() {
		super("AutoTorch", "Auto torches");
		setCategory(Category.BLOCKS);
	}

	@Override
	public void onEnable() {
		EVENTS.add(UpdateListener.class, this);
	}

	@Override
	public void onDisable() {
		EVENTS.remove(UpdateListener.class, this);
	}

	@Override
	public void onUpdate() {
		if (!d) checkAround((int) Math.round(WurstClient.MC.player.getPos().x), (int) Math.round(WurstClient.MC.player.getPos().y), (int) Math.round(WurstClient.MC.player.getPos().z));

	}
	static boolean d = false;
	static int radius = 4;
	public static void checkAround(int x, int y, int z) {
		d = true;
		for (int xx = -radius; xx <= radius; xx++) {
			for (int yy = -radius; yy <= radius; yy++) {
				for (int zz = -radius; zz <= radius; zz++) {
					BlockPos pos = new BlockPos(xx + x, yy + y, zz + z);
					if (MC.world.isChunkLoaded(pos) && MC.player.getPos().distanceTo(new Vec3d(pos)) < 7) {
						if (BlockUtils.getState(pos).isAir() && MC.player.clientWorld.getLightLevel(net.minecraft.world.LightType.BLOCK, pos) < 8 && placeTorch(pos)) {
							d = false;
							return;
						}
					} else {
						d = false;
						return;
					}
				}
			}
		}
		d = false;
	}

	public static boolean placeTorch(BlockPos pos) {
		Vec3d posVec = new Vec3d(pos).add(0.5, 0.5, 0.5);
		Vec3d eyesPos = RotationUtils.getEyesPos();
		IClientPlayerInteractionManager im = IMC.getInteractionManager();
		double rangeSq = Math.pow(5, 2);
		for (Direction side : Direction.values()) {
			BlockPos neighbor = pos.offset(side);

			// check if neighbor can be right clicked
			if (!BlockUtils.canBeClicked(neighbor) || BlockUtils.getState(neighbor).getMaterial().isReplaceable())
				continue;

			Vec3d dirVec = new Vec3d(side.getVector());
			Vec3d hitVec = posVec.add(dirVec.multiply(0.5));

			// check if hitVec is within range
			if (eyesPos.squaredDistanceTo(hitVec) > rangeSq)
				continue;

			// place block
			int s = MC.player.inventory.selectedSlot;
			if (hasTorch() == -1)
				return false;
			MC.player.inventory.selectedSlot = hasTorch();
			im.rightClickBlock(neighbor, side.getOpposite(), hitVec);
			// MC.player.swingHand(Hand.MAIN_HAND);
			MC.player.inventory.selectedSlot = s;
			return true;
		}
		return false;
	}

	public static int hasTorch() {
		for (int slot = 0; slot <= 8; slot++) {
			if (isTorchItem((ItemStack) MC.player.inventory.getInvStack(slot)))
				return slot;
		}
		return -1;
	}

	private static boolean isTorchItem(ItemStack candidate) {
		if (candidate.getItem().equals(Items.TORCH) || candidate.getItem().equals(Items.LANTERN))
			return true;
		return false;
	}

}
