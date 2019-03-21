package quaternary.quatsgarbage.gen;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;

import java.util.Random;

//LITERALLY all a copypaste from worldgenglowstonemore
//There are 2 copies in vanilla too don't @ me
//just for fun i changes the settings though
public class WorldGenGlowstoneMoreThonkjang extends WorldGenGlowStone2 {
	int TRIES = 2000;
	int XZ_RANGE = 4;
	int Y_RANGE = 25;
	
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
						
						if (j > 2) //change
						{
							break;
						}
					}
					
					if (j != 0 && j <= 2) //change
					{
						worldIn.setBlockState(blockpos, Blocks.GLOWSTONE.getDefaultState(), 2);
					}
				}
			}
			
			return true;
		}
	}
}
