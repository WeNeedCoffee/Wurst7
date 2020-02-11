package net.wurstclient.mixin;

import java.util.Objects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.wurstclient.util.BlockUtils;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
	@Shadow
	@Final
	ItemModels models;

	@Inject(at = { @At("HEAD") }, method = { "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation/Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V" }, cancellable = true)
	private void renderItemMixin(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
		if (stack.getItem().equals(Items.SHULKER_BOX)) {
			String name = stack.getName().asString();
			ItemStack st;
			String e = name.replace(" ", "_").split(":")[0].replace("[^a-zA-Z0-9/-]+", "").toLowerCase();

			Item t = BlockUtils.getItemOrBlock(e).asItem();
			if (!t.equals(Items.AIR)) {
				st = t.getStackForRender();
			} else {
				st = stack;
			}
			if (!st.equals(stack)) {
				stack = st;
				String it = "minecraft:" + st.getName().asString().replace(" ", "_").replace("[^a-zA-Z0-9/-]+", "").toLowerCase() + "#inventory";
				model = this.models.getModelManager().getModel(new ModelIdentifier(it));

			}
			if (!stack.isEmpty()) {
				matrices.push();
				boolean bl = renderMode == ModelTransformation.Mode.GUI;
				boolean bl2 = bl || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED;
				if (stack.getItem() == Items.TRIDENT && bl2) {
					model = this.models.getModelManager().getModel(new ModelIdentifier("minecraft:trident#inventory"));
				}

				model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
				matrices.translate(-0.5D, -0.5D, -0.5D);
				if (!model.isBuiltin() && (stack.getItem() != Items.TRIDENT || bl2)) {
					RenderLayer renderLayer = RenderLayers.getItemLayer(stack);
					RenderLayer renderLayer3;
					if (bl && Objects.equals(renderLayer, TexturedRenderLayers.getEntityTranslucent())) {
						renderLayer3 = TexturedRenderLayers.getEntityTranslucentCull();
					} else {
						renderLayer3 = renderLayer;
					}

					VertexConsumer vertexConsumer = ItemRenderer.getArmorVertexConsumer(vertexConsumers, renderLayer3, true, stack.hasEnchantmentGlint());
					this.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
				} else {
					BuiltinModelItemRenderer.INSTANCE.render(stack, matrices, vertexConsumers, light, overlay);
				}

				matrices.pop();
			}
			ci.cancel();
		}
	}

	@Shadow
	void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices) {

	}
}
