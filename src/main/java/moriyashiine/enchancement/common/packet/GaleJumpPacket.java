package moriyashiine.enchancement.common.packet;

import io.netty.buffer.Unpooled;
import moriyashiine.enchancement.client.packet.AddGaleParticlesPacket;
import moriyashiine.enchancement.common.Enchancement;
import moriyashiine.enchancement.common.registry.ModEntityComponents;
import moriyashiine.enchancement.common.registry.ModSoundEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class GaleJumpPacket {
	public static final Identifier ID = new Identifier(Enchancement.MOD_ID, "gale_jump");

	public static void send() {
		ClientPlayNetworking.send(ID, new PacketByteBuf(Unpooled.buffer()));
	}

	public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		server.execute(() -> ModEntityComponents.GALE.maybeGet(player).ifPresent(galeComponent -> {
			if (galeComponent.hasGale()) {
				player.jump();
				player.setVelocity(player.getVelocity().getX(), player.getVelocity().getY() * 1.5, player.getVelocity().getZ());
				player.playSound(ModSoundEvents.ENTITY_GENERIC_AIR_JUMP, 1, 1);
				galeComponent.setJumpCooldown(10);
				galeComponent.setJumpsLeft(galeComponent.getJumpsLeft() - 1);
				PlayerLookup.tracking(player).forEach(foundPlayer -> AddGaleParticlesPacket.send(foundPlayer, player.getId()));
			}
		}));
	}
}