/*
 * Copyright (C) 2014 - 2020 | Alexander01998 | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public License,
 * version 3. If a copy of the GPL was not distributed with this file, You can
 * obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockActionS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkLoadDistanceS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkRenderDistanceCenterS2CPacket;
import net.minecraft.network.packet.s2c.play.CloseContainerS2CPacket;
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.network.packet.s2c.play.ConfirmGuiActionS2CPacket;
import net.minecraft.network.packet.s2c.play.ContainerPropertyUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ContainerSlotUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.CooldownUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.CraftFailedResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.DifficultyS2CPacket;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnGlobalS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceBarUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceOrbSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.HeldItemChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.LookAtS2CPacket;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenContainerS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenHorseContainerS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.network.packet.s2c.play.PaintingSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerActionResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSpawnPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.RemoveEntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.ResourcePackSendS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.SelectAdvancementTabS2CPacket;
import net.minecraft.network.packet.s2c.play.SetCameraEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.SetTradeOffersS2CPacket;
import net.minecraft.network.packet.s2c.play.SignEditorOpenS2CPacket;
import net.minecraft.network.packet.s2c.play.StatisticsS2CPacket;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import net.minecraft.network.packet.s2c.play.SynchronizeTagsS2CPacket;
import net.minecraft.network.packet.s2c.play.TagQueryResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.network.packet.s2c.play.UnloadChunkS2CPacket;
import net.minecraft.network.packet.s2c.play.UnlockRecipesS2CPacket;
import net.minecraft.network.packet.s2c.play.VehicleMoveS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.text.Text;
import net.wurstclient.WurstClient;
import net.wurstclient.events.PacketOutputListener.PacketOutputEvent;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener {
	@Shadow
	@Override
	public ClientConnection getConnection() {
		return null;
	}

	@Shadow
	@Override
	public void onAdvancements(AdvancementUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onBlockAction(BlockActionS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onBlockDestroyProgress(BlockBreakingProgressS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onBlockEntityUpdate(BlockEntityUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onBlockUpdate(BlockUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onBossBar(BossBarS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onChatMessage(ChatMessageS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onChunkData(ChunkDataS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onChunkDeltaUpdate(ChunkDeltaUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onChunkLoadDistance(ChunkLoadDistanceS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onChunkRenderDistanceCenter(ChunkRenderDistanceCenterS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onCloseContainer(CloseContainerS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onCombatEvent(CombatEventS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onCommandSuggestions(CommandSuggestionsS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onCommandTree(CommandTreeS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onContainerPropertyUpdate(ContainerPropertyUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onContainerSlotUpdate(ContainerSlotUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onCooldownUpdate(CooldownUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onCraftFailedResponse(CraftFailedResponseS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onCustomPayload(CustomPayloadS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onDifficulty(DifficultyS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onDisconnect(DisconnectS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onDisconnected(Text var1) {

	}

	@Shadow
	@Override
	public void onEntitiesDestroy(EntitiesDestroyS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntityAnimation(EntityAnimationS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntityAttach(EntityAttachS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntityAttributes(EntityAttributesS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntityPassengersSet(EntityPassengersSetS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntityPosition(EntityPositionS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntityPotionEffect(EntityStatusEffectS2CPacket var1) {
	}

	@Shadow
	@Override
	public void onEntitySetHeadYaw(EntitySetHeadYawS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntitySpawn(EntitySpawnS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntitySpawnGlobal(EntitySpawnGlobalS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntityStatus(EntityStatusS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntityTrackerUpdate(EntityTrackerUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEntityUpdate(EntityS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onEquipmentUpdate(EntityEquipmentUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onExperienceBarUpdate(ExperienceBarUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onExperienceOrbSpawn(ExperienceOrbSpawnS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onExplosion(ExplosionS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onGameJoin(GameJoinS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onGameStateChange(GameStateChangeS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onGuiActionConfirm(ConfirmGuiActionS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onHealthUpdate(HealthUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onHeldItemChange(HeldItemChangeS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onInventory(InventoryS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onItemPickupAnimation(ItemPickupAnimationS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onKeepAlive(KeepAliveS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onLightUpdate(LightUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onLookAt(LookAtS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onMapUpdate(MapUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onMobSpawn(MobSpawnS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onOpenContainer(OpenContainerS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onOpenHorseContainer(OpenHorseContainerS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onOpenWrittenBook(OpenWrittenBookS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPaintingSpawn(PaintingSpawnS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onParticle(ParticleS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlayerAbilities(PlayerAbilitiesS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlayerActionResponse(PlayerActionResponseS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlayerList(PlayerListS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlayerListHeader(PlayerListHeaderS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlayerPositionLook(PlayerPositionLookS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlayerRespawn(PlayerRespawnS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlayerSpawn(PlayerSpawnS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlayerSpawnPosition(PlayerSpawnPositionS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlaySound(PlaySoundS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlaySoundFromEntity(PlaySoundFromEntityS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onPlaySoundId(PlaySoundIdS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onRemoveEntityEffect(RemoveEntityStatusEffectS2CPacket var1) {
	}

	@Shadow
	@Override
	public void onResourcePackSend(ResourcePackSendS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onScoreboardDisplay(ScoreboardDisplayS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onScoreboardObjectiveUpdate(ScoreboardObjectiveUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onScoreboardPlayerUpdate(ScoreboardPlayerUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onSelectAdvancementTab(SelectAdvancementTabS2CPacket var1) {

	}

	@Inject(at = { @At("HEAD") }, method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, cancellable = true)
	private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
		PacketOutputEvent event = new PacketOutputEvent(packet);
		WurstClient.INSTANCE.getEventManager().fire(event);

		if (event.isCancelled()) {
			ci.cancel();
		}
	}

	@Shadow
	@Override
	public void onSetCameraEntity(SetCameraEntityS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onSetTradeOffers(SetTradeOffersS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onSignEditorOpen(SignEditorOpenS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onStatistics(StatisticsS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onStopSound(StopSoundS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onSynchronizeRecipes(SynchronizeRecipesS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onSynchronizeTags(SynchronizeTagsS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onTagQuery(TagQueryResponseS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onTeam(TeamS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onTitle(TitleS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onUnloadChunk(UnloadChunkS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onUnlockRecipes(UnlockRecipesS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onVehicleMove(VehicleMoveS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onVelocityUpdate(EntityVelocityUpdateS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onWorldBorder(WorldBorderS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onWorldEvent(WorldEventS2CPacket var1) {

	}

	@Shadow
	@Override
	public void onWorldTimeUpdate(WorldTimeUpdateS2CPacket var1) {

	}
}
