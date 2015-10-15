package wtfores.blocks;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import wtfcore.api.BlockSets;
import wtfcore.api.OreBlockInfo;
import wtfcore.blocks.IAlphaMaskedBlock;
import wtfcore.items.ItemMetadataSubblock;
import wtfcore.utilities.LoadBlockSets;
import wtfores.config.WTFOresConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class RedstoneOverlayOre extends OverlayOre implements IAlphaMaskedBlock{

	private boolean isLit;

	public RedstoneOverlayOre(Block oreBlock, int parentMeta, int oreLevel,
			Block stoneBlock, String oreType, String[] stoneNames, String domain) {
		super(oreBlock, parentMeta, oreLevel, stoneBlock, oreType, stoneNames, domain);
		if (oreBlock == Blocks.lit_redstone_ore){
			this.setLightLevel(0.67F);
			this.isLit = true;
		}


	}

	public static Block[] registerOverlaidOre(Block oreBlock, int parentMeta, String oreType, Block stoneBlock, String stoneGeoType, String[] stoneNames, String domain){

		Block[] blockArray = new Block[3];
		Block blockToRegister= null;

		for (int loop = 2; loop > -1; loop--){
			String name = oreType+loop+"_"+stoneGeoType;

			blockToRegister = new RedstoneOverlayOre(oreBlock, parentMeta, loop, stoneBlock, "redstone_ore"+loop, stoneNames, domain).setBlockName(name);
			GameRegistry.registerBlock(blockToRegister, ItemMetadataSubblock.class, name);

			//if (WTFOresConfig.genDenseOres){
			BlockSets.oreUbifier.put(new OreBlockInfo(oreBlock, parentMeta, stoneBlock, loop), blockToRegister);
			LoadBlockSets.addOreBlock(blockToRegister);

			blockArray[loop] = blockToRegister;
		}
		//These are used to set ores of high density- doing this means I can call a negative density from the ubifier map and still get a block
		BlockSets.oreUbifier.put(new OreBlockInfo(oreBlock, parentMeta, stoneBlock, -1), blockToRegister);
		BlockSets.oreUbifier.put(new OreBlockInfo(oreBlock, parentMeta, stoneBlock, -2), blockToRegister);
		BlockSets.oreUbifier.put(new OreBlockInfo(oreBlock, parentMeta, stoneBlock, -3), blockToRegister);
		return blockArray;
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta){
		if (WTFOresConfig.enableDenseOres && this.oreLevel < 2){
			Block blockToSet = BlockSets.oreUbifier.get(new OreBlockInfo (Blocks.lit_redstone_ore, this.oreMeta, this.stoneBlock, this.oreLevel+1));
			if (blockToSet != null){
				world.setBlock(x, y, z, blockToSet, meta, 0);
			}
		}
	}

    @Override
	public int tickRate(World p_149738_1_)
    {
        return 30;
    }
    @Override
	public void onBlockClicked(World p_149699_1_, int p_149699_2_, int p_149699_3_, int p_149699_4_, EntityPlayer p_149699_5_)
    {
        this.func_150185_e(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_);
        //super.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
    }
    /*
    public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity p_149724_5_)
    {
        this.func_150185_e(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_);
        super.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
    }
    */
    @Override
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        this.func_150185_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        return super.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    private void func_150185_e(World world, int x, int y, int z)
    {
        this.func_150186_m(world, x, y, z);

        if (!isLit)
        {
            Block blockToSet = BlockSets.oreUbifier.get(new OreBlockInfo(Blocks.lit_redstone_ore, this.oreMeta, this.stoneBlock, this.oreLevel));
        	world.setBlock(x, y, z, blockToSet, world.getBlockMetadata(x,y,z), 3);
        }
    }
    @Override
	public void updateTick(World world, int x, int y, int z, Random random)
    {
        if (isLit)
        {
        	Block blockToSet = BlockSets.oreUbifier.get(new OreBlockInfo(Blocks.redstone_ore, this.oreMeta, this.stoneBlock, this.oreLevel));
        	world.setBlock(x, y, z, blockToSet, world.getBlockMetadata(x, y, z), 0);
        }
        super.updateTick(world, x, y, z, random);
    }



    private void func_150186_m(World world, int p_150186_2_, int p_150186_3_, int p_150186_4_)
    {
        Random random = world.rand;
        double d0 = 0.0625D;

        for (int l = 0; l < 6; ++l)
        {
            double d1 = p_150186_2_ + random.nextFloat();
            double d2 = p_150186_3_ + random.nextFloat();
            double d3 = p_150186_4_ + random.nextFloat();

            if (l == 0 && !world.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube())
            {
                d2 = p_150186_3_ + 1 + d0;
            }

            if (l == 1 && !world.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube())
            {
                d2 = p_150186_3_ + 0 - d0;
            }

            if (l == 2 && !world.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube())
            {
                d3 = p_150186_4_ + 1 + d0;
            }

            if (l == 3 && !world.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube())
            {
                d3 = p_150186_4_ + 0 - d0;
            }

            if (l == 4 && !world.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube())
            {
                d1 = p_150186_2_ + 1 + d0;
            }

            if (l == 5 && !world.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube())
            {
                d1 = p_150186_2_ + 0 - d0;
            }

            if (d1 < p_150186_2_ || d1 > p_150186_2_ + 1 || d2 < 0.0D || d2 > p_150186_3_ + 1 || d3 < p_150186_4_ || d3 > p_150186_4_ + 1)
            {
                world.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
