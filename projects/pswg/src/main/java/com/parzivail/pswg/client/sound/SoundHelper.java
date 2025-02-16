package com.parzivail.pswg.client.sound;

import com.parzivail.pswg.entity.ship.ShipEntity;
import com.parzivail.pswg.features.lightsabers.client.ThrownLightsaberEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;

public class SoundHelper
{
	public static void playThrownLightsaberSound(ThrownLightsaberEntity entity)
	{
		var minecraft = MinecraftClient.getInstance();
		minecraft.getSoundManager().play(new LightsaberThrownSoundInstance(entity));
	}

	public static void playShipExteriorSound(ShipEntity entity, SoundEvent sound)
	{
		var minecraft = MinecraftClient.getInstance();
		minecraft.getSoundManager().play(new ShipExteriorSoundInstance(entity, sound, SoundInstance.createRandom()));
	}
}
