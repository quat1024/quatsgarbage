package quaternary.quatsgarbage.gen;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenHellLavaHahaJustKiddingActuallyItsANoop extends WorldGenHellLava {
	public WorldGenHellLavaHahaJustKiddingActuallyItsANoop() {
		super(null, false);
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		return true;
	}
}
