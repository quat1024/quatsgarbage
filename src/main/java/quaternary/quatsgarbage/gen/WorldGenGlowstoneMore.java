package quaternary.quatsgarbage.gen;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;

import java.util.Random;

public class WorldGenGlowstoneMore extends WorldGenGlowStone1 {
	int TRIES = 2500;
	int XZ_RANGE = 7;
	int Y_RANGE = 18;
	
	//Extremely copypaste from super
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		if (!worldIn.isAirBlock(position))
		{
			return false;
		}
		else if (worldIn.getBlockState(position.up()).getBlock() != Blocks.NETHERRACK)
		{
			return false;
		}
		else
		{
			worldIn.setBlockState(position, Blocks.GLOWSTONE.getDefaultState(), 2);
			
			for (int i = 0; i < TRIES; ++i)
			{
				BlockPos blockpos = position.add(rand.nextInt(XZ_RANGE) - rand.nextInt(XZ_RANGE), -rand.nextInt(Y_RANGE), rand.nextInt(XZ_RANGE) - rand.nextInt(XZ_RANGE));
				
				if (worldIn.isAirBlock(blockpos))
				{
					int j = 0;
					
					for (EnumFacing enumfacing : EnumFacing.values())
					{
						if (worldIn.getBlockState(blockpos.offset(enumfacing)).getBlock() == Blocks.GLOWSTONE)
						{
							++j;
						}
						
						if (j > 1)
						{
							break;
						}
					}
					
					if (j == 1)
					{
						worldIn.setBlockState(blockpos, Blocks.GLOWSTONE.getDefaultState(), 2);
					}
				}
			}
			
			return true;
		}
	}
}
