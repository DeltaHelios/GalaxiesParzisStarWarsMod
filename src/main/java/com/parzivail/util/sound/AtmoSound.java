package com.parzivail.util.sound;

import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;

public class AtmoSound extends PositionedSound implements ITickableSound
{
	private boolean donePlaying;

	public AtmoSound(ResourceLocation sound, float x, float y, float z, float volume, float pitch) {
		super(sound);
		this.xPosF = x;
		this.yPosF = y;
		this.zPosF = z;
		this.volume = volume;
		this.pitch = pitch;
		this.repeat = true;
	}

	@Override
	public void update() {
	}

	@Override
	public boolean isDonePlaying() {
		return donePlaying;
	}

	public void endPlaying() {
		donePlaying = true;
	}

	public void startPlaying() {
		donePlaying = false;
	}

	public AtmoSound setVolume(float vol) {
		this.volume = vol;
		return this;
	}

	public AtmoSound setPitch(float pitch) {
		this.pitch = pitch;
		return this;
	}
}
