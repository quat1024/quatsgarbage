package quaternary.quatsgarbage;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.terraingen.BiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import quaternary.quatsgarbage.gen.BiomeDecoratorHella;
import quaternary.quatsgarbage.gen.WorldGenGlowstoneMore;
import quaternary.quatsgarbage.gen.WorldGenGlowstoneMoreThonkjang;
import quaternary.quatsgarbage.gen.WorldGenHellLavaHahaJustKiddingActuallyItsANoop;

import java.lang.reflect.Field;

public class DecorationTweaks {
	@SubscribeEvent
	public static void decorators(BiomeEvent.CreateDecorator e) {
		if(ModConfig.replaceBiomeDecorator) {
			BiomeDecoratorHella hella = new BiomeDecoratorHella();
			BiomeDecorator d = e.getNewBiomeDecorator();
			
			//Copy all the settings over.
			hella.bigMushroomsPerChunk = d.bigMushroomsPerChunk;
			hella.cactiPerChunk = d.cactiPerChunk;
			hella.clayPerChunk = d.clayPerChunk;
			hella.deadBushPerChunk = d.deadBushPerChunk;
			hella.flowersPerChunk = d.flowersPerChunk;
			hella.generateFalls = d.generateFalls;
			hella.grassPerChunk = d.grassPerChunk;
			hella.mushroomsPerChunk = d.mushroomsPerChunk;
			hella.reedsPerChunk = d.reedsPerChunk;
			hella.gravelPatchesPerChunk = d.gravelPatchesPerChunk;
			hella.sandPatchesPerChunk = d.sandPatchesPerChunk;
			hella.treesPerChunk = d.treesPerChunk;
			hella.waterlilyPerChunk = d.waterlilyPerChunk;
			
			e.setNewBiomeDecorator(hella);
		}
		
		BiomeDecorator d = e.getNewBiomeDecorator();
		
		if(ModConfig.tweakBiomeSettings) {
			d.reedsPerChunk *= 3;
			d.grassPerChunk = d.grassPerChunk > 1 ? 1 : 0;
			d.mushroomsPerChunk *= 10;
			d.flowersPerChunk *= 3;
			d.cactiPerChunk++;
		}
		
		if(ModConfig.killLake) d.generateFalls = false;
	}
	
	@SubscribeEvent
	public static void populate(PopulateChunkEvent.Populate e) {
		if(ModConfig.killLake) {
			if(e.getType() == PopulateChunkEvent.Populate.EventType.LAKE || e.getType() == PopulateChunkEvent.Populate.EventType.LAVA) e.setResult(Event.Result.DENY);
		}
	}
	
	@SubscribeEvent
	public static void decorateBiome(DecorateBiomeEvent.Decorate e) {
		if(ModConfig.killLake) {
			if(e.getType() == DecorateBiomeEvent.Decorate.EventType.LAKE_LAVA || e.getType() == DecorateBiomeEvent.Decorate.EventType.LAKE_WATER) e.setResult(Event.Result.DENY);
		}
	}
	
	private static WorldGenMinable quartz2Electricboogaloo = new WorldGenMinable(Blocks.QUARTZ_ORE.getDefaultState(), 20, BlockMatcher.forBlock(Blocks.NETHERRACK));
	
	@SubscribeEvent
	public static void decorateLate(DecorateBiomeEvent.Post e) {
		if(!ModConfig.moreQuartz) return;
		
		World world = e.getWorld();
		if(world.isRemote) return;
		IChunkProvider provider = world.getChunkProvider();
		if(provider instanceof ChunkProviderServer) {
			IChunkGenerator gen = ((ChunkProviderServer) provider).chunkGenerator;
			if(gen instanceof ChunkGeneratorHell) {
				for (int l1 = 0; l1 < 16; ++l1) {
					quartz2Electricboogaloo.generate(world, e.getRand(), e.getPos().add(e.getRand().nextInt(16), e.getRand().nextInt(108) + 10, e.getRand().nextInt(16)));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void world(WorldEvent.Load e) {
		if(!ModConfig.hackyMagic) return;
		
		World world = e.getWorld();
		IChunkProvider provider = world.getChunkProvider();
		if(provider instanceof ChunkProviderServer) {
			IChunkGenerator gen = ((ChunkProviderServer) provider).chunkGenerator;
			if(gen instanceof ChunkGeneratorHell) {
				ChunkGeneratorHell heck = ((ChunkGeneratorHell) gen);
				
				Field a = ReflectionHelper.findField(ChunkGeneratorHell.class, "lightGemGen", "field_177469_u");
				//voldethonk
				Field b = ReflectionHelper.findField(ChunkGeneratorHell.class, "hellPortalGen", "field_177468_v");
				Field c = ReflectionHelper.findField(ChunkGeneratorHell.class, "lavaTrapGen", "field_177473_x");
				
				try {
					//these are actually 2 copies of the same class (thonkjang)
					EnumHelper.setFailsafeFieldValue(a, heck, new WorldGenGlowstoneMore());
					EnumHelper.setFailsafeFieldValue(b, heck, new WorldGenGlowstoneMoreThonkjang());
					
					if(ModConfig.killNetherLava) {
						EnumHelper.setFailsafeFieldValue(c, heck, new WorldGenHellLavaHahaJustKiddingActuallyItsANoop());
					}
				} catch(Exception oof) {
					oof.printStackTrace();
				}
			}
		}
	}
}
