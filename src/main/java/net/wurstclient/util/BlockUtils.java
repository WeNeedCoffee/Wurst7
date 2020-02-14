/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import coffee.weneed.utils.NetUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.wurstclient.WurstClient;

public enum BlockUtils {
	;

	private static final MinecraftClient MC = WurstClient.MC;

	public static boolean canBeClicked(BlockPos pos) {
		return getOutlineShape(pos) != VoxelShapes.empty();
	}

	public static ArrayList<BlockPos> getAllInBox(BlockPos from, BlockPos to) {
		ArrayList<BlockPos> blocks = new ArrayList<>();
		BlockPos min = new BlockPos(Math.min(from.getX(), to.getX()), Math.min(from.getY(), to.getY()), Math.min(from.getZ(), to.getZ()));
		BlockPos max = new BlockPos(Math.max(from.getX(), to.getX()), Math.max(from.getY(), to.getY()), Math.max(from.getZ(), to.getZ()));
		for (int x = min.getX(); x <= max.getX(); x++) {
			for (int y = min.getY(); y <= max.getY(); y++) {
				for (int z = min.getZ(); z <= max.getZ(); z++) {
					blocks.add(new BlockPos(x, y, z));
				}
			}
		}

		return blocks;
	}

	public static Block getBlock(BlockPos pos) {
		return getState(pos).getBlock();
	}

	static File f = new File("./ids.json");
	static Map<String, String> blocks = new HashMap<>();
	static Map<String, String> items = new HashMap<>();

	static {
		/*try {
			call();
		} catch (JSONException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
		JSONObject json;
		try {
			json = new JSONObject(new String(NetUtil.downloadUrl(f.toURI().toURL()), StandardCharsets.UTF_8));
			System.out.println(json);
			for (String e : json.getJSONObject("items").keySet()) {
				items.put(e, json.optJSONObject("items").getString(e));
			}
			for (String e : json.getJSONObject("blocks").keySet()) {
				blocks.put(e, json.optJSONObject("blocks").getString(e));
			}
		} catch (JSONException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	public static void call() throws JSONException, IOException {
		Map<Integer, String> bids = new HashMap<>();
		int i = 0;
		for (Block b : Registry.BLOCK) {
			bids.put(i, Registry.BLOCK.getId(b).getPath());
			i++;
		}

		Map<Integer, String> iids = new HashMap<>();
		i = 0;
		for (Item it : Registry.ITEM) {
			iids.put(i, Registry.ITEM.getId(it).getPath());
			i++;
		}
		f.delete();
		FileOutputStream fos = new FileOutputStream(f);
		JSONObject json = new JSONObject().put("blocks", bids);
		fos.write(json.put("items", iids).toString().getBytes());
		fos.flush();
		fos.close();
		f.setLastModified(System.currentTimeMillis());

	}

	public static ItemConvertible getItemOrBlock(String name) {
		if (name.matches("\\d+i")) {
			return getItemFromName(items.get(name.replace("i", "")).replace(" ", "_").toLowerCase());
		} else if (name.matches("\\d+b")) {
			return getBlockFromName(blocks.get(name.replace("b", "")).replace(" ", "_").toLowerCase());
		}
		Item i = getItemFromName(name);
		if (!i.equals(Items.AIR))
			return i;
		Block b = getBlockFromName(name);
		return b;
	}

	public static Block getBlockFromName(String name) {
		try {
			return Registry.BLOCK.get(new Identifier(name));

		} catch (InvalidIdentifierException e) {
			return Blocks.AIR;
		}
	}

	public static String getID(Item item) {
		String name = item.getName().asString();
		for (String i : items.keySet()) {
			if (items.get(i.replace(" ", "_")).equalsIgnoreCase(name.replace(" ", "_"))) {
				return i + "i";
			}
		}

		for (String i : blocks.keySet()) {
			if (blocks.get(i).equalsIgnoreCase(name)) {
				return i + "b";
			}
		}

		return "0i";
	}

	public static String getID(String name) {

		return getID(getItemOrBlock(name).asItem());

	}

	public static Item getItemFromName(String name) {
		try {
			return Registry.ITEM.get(new Identifier(name));

		} catch (InvalidIdentifierException e) {
			return Items.AIR;
		}
	}

	public static Box getBoundingBox(BlockPos pos) {
		return getOutlineShape(pos).getBoundingBox().offset(pos);
	}

	public static float getHardness(BlockPos pos) {
		return getState(pos).calcBlockBreakingDelta(MC.player, MC.world, pos);
	}

	public static int getId(BlockPos pos) {
		return Block.getRawIdFromState(getState(pos));
	}

	public static String getName(Block block) {
		return Registry.BLOCK.getId(block).toString();
	}

	public static String getName(BlockPos pos) {
		return getName(getBlock(pos));
	}

	private static VoxelShape getOutlineShape(BlockPos pos) {
		return getState(pos).getOutlineShape(MC.world, pos);
	}

	public static BlockState getState(BlockPos pos) {
		return MC.world.getBlockState(pos);
	}
}
