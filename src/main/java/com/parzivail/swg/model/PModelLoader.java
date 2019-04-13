package com.parzivail.swg.model;

import com.parzivail.swg.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.animation.ModelBlockAnimation;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class PModelLoader implements ICustomModelLoader
{
	public static final PModelLoader INSTANCE = new PModelLoader();

	@Override
	public boolean accepts(ResourceLocation modelLocation)
	{
		return Resources.MODID.equals(modelLocation.getResourceDomain()) && !(modelLocation instanceof ModelResourceLocation);
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception
	{
		// Load vanilla model
		PModelBlock vanillaModel;
		ResourceLocation vanillaModelLocation = new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath() + ".json");
		try (IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(vanillaModelLocation); Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
		{
			vanillaModel = PModelBlock.deserialize(reader);
			vanillaModel.name = modelLocation.toString();
		}

		// Load armature animation (currently disabled for efficiency, see MixinModelBlockAnimation)
		String modelPath = modelLocation.getResourcePath();
		if (modelPath.startsWith("models/"))
		{
			modelPath = modelPath.substring("models/".length());
		}
		ResourceLocation armatureLocation = new ResourceLocation(modelLocation.getResourceDomain(), "armatures/" + modelPath + ".json");
		ModelBlockAnimation animation = ModelBlockAnimation.loadVanillaAnimation(Minecraft.getMinecraft().getResourceManager(), armatureLocation);

		// Return the vanilla model weapped in a VanillaModelWrapper
		return new VanillaModelWrapper(modelLocation, vanillaModel, false, animation);
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager)
	{
	}

	@Override
	public String toString()
	{
		return "PModelLoader";
	}
}
