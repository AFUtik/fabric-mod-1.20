package org.callofthevoid.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import org.callofthevoid.network.packet.EnergySyncS2CPacket;
import org.callofthevoid.network.packet.FluidSyncS2CPacket;
import org.callofthevoid.CallOfTheVoid;

public class ModMessages {
    public static final Identifier FLUID_SYNC = new Identifier(CallOfTheVoid.MOD_ID, "fluid_sync");
    public static final Identifier ENERGY_SYNC = new Identifier(CallOfTheVoid.MOD_ID, "energy_sync");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(FLUID_SYNC, FluidSyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ENERGY_SYNC, EnergySyncS2CPacket::receive);
    }
}
