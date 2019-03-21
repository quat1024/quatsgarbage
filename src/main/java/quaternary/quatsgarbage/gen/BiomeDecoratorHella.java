package quaternary.quatsgarbage.gen;

import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import quaternary.quatsgarbage.ModConfig;

import java.util.Random;

public class BiomeDecoratorHella extends BiomeDecorator {
	public BiomeDecoratorHella() {
		if(ModConfig.customOres) {
			this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 20);
			this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 15);
			this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 15);
			this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 15);
			this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 15);
			this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 15);
		}
	}
	
	@Override
	public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
		if(!ModConfig.customOres) {
			super.decorate(worldIn, random, biome, pos);
			return;
		}
		
		if(this.decorating) {
			throw new RuntimeException("Already decorating oogabooga");
		} else {
			//begin copypaste
			this.chunkProviderSettings = ChunkGeneratorSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
			this.chunkPos = pos;
			this.dirtGen = new WorldGenMinable(Blocks.DIRT.getDefaultState(), this.chunkProviderSettings.dirtSize);
			this.gravelOreGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), this.chunkProviderSettings.gravelSize);
			this.graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
			this.dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
			this.andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
			//end copypaste
			//ores set above in constructor w/ hardcoded big boy values
			this.genDecorations(biome, worldIn, random);
			this.decorating = false;
		}
	}
	
	@Override
	protected void genStandardOre1(World worldIn, Random random, int blockCount, WorldGenerator generator, int minHeight, int maxHeight) {
		int m = ModConfig.doubleOreSpawnCounts ? 2 : 1;
		super.genStandardOre1(worldIn, random, blockCount * m, generator, minHeight, maxHeight);
	}
	
	@Override
	protected void genStandardOre2(World worldIn, Random random, int blockCount, WorldGenerator generator, int centerHeight, int spread) {
		int m = ModConfig.doubleOreSpawnCounts ? 2 : 1;
		super.genStandardOre2(worldIn, random, blockCount * m, generator, centerHeight, spread);
	}
}
