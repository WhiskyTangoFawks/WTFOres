package wtfores.gencores;

import java.util.Random;
import wtfcore.utilities.BlockInfo;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;


public class VOreGen {
	public VOreGen() {
	}
	public Random random = new Random();

	public static VOreGen getGenMethods(){
		if (Loader.isModLoaded("UndergroundBiomes")){
			return new UBCOreGen();
		}
		else {
			return new VOreGen();
		}
	}

	

	/**
	 **UBC sensitive version of world.getBlock- is overridden in UBCGen if UBC is installed
	 **/
	public BlockInfo getBlockToReplace(World world, int x, int y, int z){
		return new BlockInfo(world.getBlock(x,y,z), world.getBlockMetadata(x,y,z) );
	}

	/**
	 **Use instead of world.setBlock, when you don't want it to update adjacent blocks.  non-fluid blocks placed during world generation should use this method.
	 **/
	public boolean setBlockWithoutNotify(World world, int x, int y, int z, Block block, int metadata){
		int flags = 0;
		Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
		net.minecraftforge.common.util.BlockSnapshot blockSnapshot = null;
		if ((flags & 1) != 0){
		}
		if (world.captureBlockSnapshots && !world.isRemote)	{
			blockSnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, x, y, z, flags);
			world.capturedBlockSnapshots.add(blockSnapshot);
		}
		boolean flag = chunk.func_150807_a(x & 15, y, z & 15, block, metadata);
		if (!flag && blockSnapshot != null)	{
			world.capturedBlockSnapshots.remove(blockSnapshot);
			blockSnapshot = null;
		}
		world.theProfiler.startSection("checkLight");
		world.func_147451_t(x, y, z);
		world.theProfiler.endSection();
		return flag;
	}

}
