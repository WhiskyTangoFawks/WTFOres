package wtfores.blocks;

import java.util.List;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import wtfcore.WTFCore;
import wtfcore.utilities.BlockSets;
import wtfcore.utilities.OreBlockInfo;
import wtfores.WTFOres;
import wtfores.WTFOresConfig;

public class OreChildBlock extends Block {

	public Block oreBlock;
	public int oreMeta;
	protected String oreType;
	protected Block stoneBlock;
	protected int oreLevel;
	protected String[] textureNames;
	protected String[] parentLocations;
	protected String[] localizedNames;
	private boolean shouldFall;

	protected OreChildBlock(Block block, int meta, Block stoneBlock) {
		super(stoneBlock.getMaterial());
		this.oreBlock = block;
		this.oreMeta = meta;
		this.stoneBlock  = stoneBlock;
		this.setStepSound(stoneBlock.stepSound);
		if (stoneBlock == Blocks.sand || stoneBlock == Blocks.gravel){
			shouldFall = true;	
		}
		if (stoneBlock != Blocks.lit_redstone_ore){
			this.setCreativeTab(WTFOres.oreTab);
		}
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z){
		return 2*(stoneBlock.getBlockHardness(world, x, y, z));
	}

	@Override
	public String getHarvestTool(int metadata) {
		return stoneBlock.getHarvestTool(metadata);
	}
	@Override
	public int getHarvestLevel(int metadata) {
		return oreBlock.getHarvestLevel(this.oreMeta);
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face){
		if (this.oreBlock == Blocks.coal_ore){
			return true;
		}
		return oreBlock.getFlammability(world, x, y, z, face) > 0;
	}
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)	{
		if (this.oreBlock == Blocks.coal_ore){
			return Blocks.log.getFireSpreadSpeed(world, x, y, z, face);
		}
		return oreBlock.getFireSpreadSpeed(world, x, y, z, face);
	}
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)	{
		if (this.oreBlock == Blocks.coal_ore){
			return Blocks.log.getFlammability(world, x, y, z, face);
		}
		return Blocks.fire.getFlammability(this);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta){
		if (WTFOresConfig.enableDenseOres && this.oreLevel < 2){
			Block blockToSet = BlockSets.oreUbifier.get(new OreBlockInfo (this.oreBlock, this.oreMeta, this.stoneBlock, this.oreLevel+1));
			if (blockToSet != null){
				world.setBlock(x, y, z, blockToSet, meta, 0);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list){
		for (int i = 0; i < this.textureNames.length; ++i)	{
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getRenderType(){
		return 0;
	}

	public void onBlockAdded(World world, int x, int y, int z)
	{
		WTFCore.log.info("Block added");
		if (shouldFall){  
			WTFCore.log.info("Calling update");
			world.scheduleBlockUpdate(x, y, z, this, 2);
		}
	}
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (shouldFall){  
			world.scheduleBlockUpdate(x, y, z, this, 2);
		}
	}

	public void updateTick(World world, int x, int y, int z, Random random)
	{
		WTFCore.log.info("Update tick");
		if (shouldFall && !world.isRemote)
		{
			WTFCore.log.info("calling drop block");
			this.dropBlock(world, x, y, z);
		}
	}

	private void dropBlock(World world, int x, int y, int z)
	{
		if (func_149831_e(world, x, y - 1, z) && y >= 0)
		{
			byte b0 = 32;

			if (world.checkChunksExist(x - b0, y - b0, z - b0, x + b0, y + b0, z + b0))
			{
				if (!world.isRemote)
				{
					EntityFallingBlock entityfallingblock = new EntityFallingBlock(world, (double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), this, world.getBlockMetadata(x, y, z));
					this.func_149829_a(entityfallingblock);
					world.spawnEntityInWorld(entityfallingblock);
				}
			}
			else
			{
				world.setBlockToAir(x, y, z);

				while (func_149831_e(world, x, y - 1, z) && y > 0)
				{
					--y;
				}

				if (y > 0)
				{
					world.setBlock(x, y, z, this);
				}
			}
		}
	}

	protected void func_149829_a(EntityFallingBlock p_149829_1_) {}

	public static boolean func_149831_e(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);

		if (block.isAir(world, x, y, z))
		{
			return true;
		}
		else if (block == Blocks.fire)
		{
			return true;
		}
		else
		{
			Material material = block.getMaterial();
		return material == Material.water ? true : material == Material.lava;
		}
	}

	public void func_149828_a(World p_149828_1_, int p_149828_2_, int p_149828_3_, int p_149828_4_, int p_149828_5_) {}
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
		return oreBlock.getIcon(p_149691_1_, oreMeta);
    }

    @Override
	public boolean isOpaqueCube(){return true;}
    @Override
    public boolean renderAsNormalBlock(){return true;}

    @Override
	@SideOnly(Side.CLIENT)
    public int getRenderBlockPass(){
    	return oreBlock.getRenderBlockPass();
    }
	@Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return oreBlock.getItemDropped(oreMeta, random, fortune);
    }
    @Override
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        oreBlock.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, oreMeta);
    }
    @Override
	public void dropXpOnBlockBreak(World p_149657_1_, int p_149657_2_, int p_149657_3_, int p_149657_4_, int p_149657_5_){
    	oreBlock.dropXpOnBlockBreak(p_149657_1_, p_149657_2_, p_149657_3_, p_149657_4_, p_149657_5_);
    }
    @Override
	public float getExplosionResistance(Entity p_149638_1_)
    {
        return oreBlock.getExplosionResistance(p_149638_1_);
    }
    
    @Override
    public boolean canHarvestBlock(EntityPlayer player, int meta)
    {
        ItemStack currentItem = player.inventory.getCurrentItem();
        String reqTool = stoneBlock.getHarvestTool(meta);
        if (currentItem == null || reqTool == null)
        {
            return player.canHarvestBlock(oreBlock);
        }

        int toolLevel = currentItem.getItem().getHarvestLevel(currentItem, reqTool);
        if (toolLevel < 0)
        {
            return player.canHarvestBlock(oreBlock);
        }

        return toolLevel >= oreBlock.getHarvestLevel(meta);
    }

    @Override
	public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_){
    	oreBlock.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, oreMeta);;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        oreBlock.registerBlockIcons(iconRegister);
    }

    @Override
    public int damageDropped(int metadata){
    	return oreBlock.damageDropped(oreMeta);
    }
    @Override
    public boolean canRenderInPass(int pass)
    {
    	return oreBlock.canRenderInPass(pass);
    }
    @Override
	@SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
       return oreBlock.getBlockColor();
    }
    @Override
	@SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        return oreBlock.getBlockColor();
    }
    @Override
	@SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_)
    {
    	return oreBlock.colorMultiplier(p_149720_1_, p_149720_2_, p_149720_3_, p_149720_4_);
    }
    @Override
	@SideOnly(Side.CLIENT)
    	public int getMixedBrightnessForBlock(IBlockAccess p_149677_1_, int p_149677_2_, int p_149677_3_, int p_149677_4_)
    {
    	return oreBlock.getMixedBrightnessForBlock(p_149677_1_, p_149677_2_, p_149677_3_, p_149677_4_);
    }
    @Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
    {
        return getLightValue();
    }
    public boolean isToolEffective(String type, int metadata)
    {
    	return stoneBlock.isToolEffective(type, metadata);
    }
}
