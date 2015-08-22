package wtfores.gencores;

import java.util.Random;
import wtfcore.utilities.BlockInfo;
import wtfcore.utilities.BlockSets;
import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import exterminatorJeff.undergroundBiomes.api.BlockCodes;
import exterminatorJeff.undergroundBiomes.api.UBAPIHook;
import exterminatorJeff.undergroundBiomes.api.UBStrataColumn;
import exterminatorJeff.undergroundBiomes.api.UBStrataColumnProvider;


public class UBCOreGen extends VOreGen{
	public UBCOreGen() {
	}

	private static int DimensionID = 0;

	protected BlockInfo getUBCStone(World world, int x, int y, int z){
		UBStrataColumnProvider columnProvider = UBAPIHook.ubAPIHook.dimensionalStrataColumnProvider.ubStrataColumnProvider(DimensionID);
		UBStrataColumn column = columnProvider.strataColumn(x, z);
		BlockCodes stoneCode = column.stone(y);
		return new BlockInfo(stoneCode.block, stoneCode.metadata);
	}

	@Override
	public BlockInfo getBlockToReplace(World world, int x, int y, int z){
		Block block = world.getBlock(x,y,z);
		if (block.hashCode() == Blocks.stone.hashCode()){
			return getUBCStone(world, x, y, z);
		}
		return new BlockInfo(block, world.getBlockMetadata(x,y,z));
	}

	public void genFloatingStone(World world, int x, int y, int z, BlockSets.Modifier modifier){
		BlockInfo blockandmeta = getUBCStone(world, x, y, z);
		Block blockToSet = BlockSets.floorBlock.get(new BlockInfo(blockandmeta.block, blockandmeta.meta, modifier));
		if (blockToSet != null){
			setBlockWithoutNotify(world, x, y+1, z, blockToSet, blockandmeta.meta);
		}
	}




}
