package wtfores.blocks;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;
import wtfcore.blocks.IAlphaMaskedBlock;
import wtfcore.blocks.ChildBlockCustomMetadata;
import wtfcore.items.ItemMetadataSubblock;
import wtfcore.proxy.ClientProxy;
import wtfcore.tweaksmethods.FracMethods;
import wtfcore.utilities.BlockSets;
import wtfcore.utilities.OreBlockInfo;
import wtfcore.utilities.UBCblocks;
import wtfores.AddCustomOre;
import wtfores.WTFOres;
import wtfores.WTFOresConfig;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class OverlayOre extends ChildBlockCustomMetadata implements IAlphaMaskedBlock
{

	//This is designed to deal with mods with metadata- tcon ores, e.g. , will be split into 6 different blocks

	protected String[] textureNames;
	protected String[] parentLocations;
	protected String oreType;
	protected Block stoneBlock;
	protected String[] localizedNames;

	private IIcon[] textures;

	public static String[] vanillaStone = {"stone"};
	protected static String[] vanillaObsidian = {"obsidian"};
	protected static String[] vanillaSand = {"sand"};
	protected static String[] vanillaSandstone = {"sandstone"};
	protected static String[] vanillaGravel = {"gravel"};

	private static float [] vanillaStoneHardness = {1.5f};
	private static float [] vanillaObsidianHardness = {25f};
	private static float [] vanillaSandstoneHardness = {0.8f};
	private static float [] vanillaSandHardness = {0.5f};

	//private static Random random = new Random();
	protected int oreLevel;
	private float[] stoneHardness;

	public  OverlayOre(Block oreBlock, int parentMeta, int oreLevel, Block stoneBlock, String oreType, String[] stoneNames, String domain){
		super(oreBlock, parentMeta);

		loadTextureStrings(oreType, stoneNames, domain);
		this.stoneBlock  = stoneBlock;
		this.oreLevel = oreLevel;
		if (this.parentBlock != Blocks.lit_redstone_ore){this.setCreativeTab(WTFOres.oreTab);}
		if (this.stoneBlock == Blocks.stone){stoneHardness = vanillaStoneHardness;}
		else if (this.stoneBlock == Blocks.sand){stoneHardness = vanillaSandHardness;}
		else if (this.stoneBlock == Blocks.sandstone){stoneHardness = vanillaSandstoneHardness;}
		else if (this.stoneBlock == Blocks.obsidian){stoneHardness = vanillaObsidianHardness;}
		else if (Loader.isModLoaded("UndergroundBiomes") && this.stoneBlock == UBCblocks.IgneousStone){stoneHardness = UBCblocks.hardnessIgneousStone;}
		else if (Loader.isModLoaded("UndergroundBiomes") && this.stoneBlock == UBCblocks.MetamorphicStone){stoneHardness = UBCblocks.hardnessMetamorphicStone;}
		else if (Loader.isModLoaded("UndergroundBiomes") && this.stoneBlock == UBCblocks.SedimentaryStone){stoneHardness = UBCblocks.hardnessSedimentaryStone;}
	}

	public void loadTextureStrings(String oreType, String[] stoneNames, String domain){

		this.textureNames = new String[stoneNames.length];
		this.parentLocations = new String[stoneNames.length];
		this.localizedNames = new String[stoneNames.length];

		for (int loop = 0; loop < stoneNames.length; loop++){
			textureNames[loop] = oreType+"_"+stoneNames[loop];
			parentLocations[loop] = domain+":"+stoneNames[loop];
			String localizedStone = null;
			if (stoneNames[loop] ==  "redGranite"){localizedStone = "red granite";}
			else if (stoneNames[loop] ==  "blackGranite"){localizedStone = "black granite";}
			else if (stoneNames[loop] ==  "greenschist"){localizedStone = "Green Schist";}
			else if (stoneNames[loop] ==  "blueschist"){localizedStone = "Blue Schist";}
			else if (stoneNames[loop] ==  "ligniteblock"){localizedStone = "Lignite";}
			else {localizedStone = stoneNames[loop];}
			localizedNames[loop] = WordUtils.capitalize(localizedStone) + " " + WordUtils.capitalize(oreType.substring(0, oreType.length()-1).replace("_", " "));
		}
		this.oreType = oreType;
	}

    @Override
	public Block setBlockName(String p_149663_1_)
    {
    	Block block = super.setBlockName(p_149663_1_);
    	for (int loop = 0; loop < textureNames.length; loop++){
			String string = this.getUnlocalizedName()+"."+loop+".name="+localizedNames[loop];
			WTFOres.orenames.add(string);
		}
        return block;
    }

	public static Block[] registerOverlaidOre(Block oreBlock, int parentMeta, String oreType, Block stoneBlock, String stoneGeoType, String[] stoneNames, String domain){

		Block[] blockArray = new Block[3];
		Block blockToRegister= null;

		//this shunts the registry into redstone if it detects the block to be registered is redstone
		if (oreBlock == Blocks.redstone_ore){
			RedstoneOverlayOre.registerOverlaidOre(Blocks.lit_redstone_ore, parentMeta, "lit_redstone_ore", stoneBlock, stoneGeoType, stoneNames, domain);
			return RedstoneOverlayOre.registerOverlaidOre(oreBlock, parentMeta, oreType, stoneBlock, stoneGeoType, stoneNames, domain);
		}

		else {
			for (int loop = 2; loop > -1; loop--){
				String name = oreType+loop+"_"+stoneGeoType;

				blockToRegister = new OverlayOre(oreBlock, parentMeta, loop, stoneBlock, oreType+loop, stoneNames, domain).setBlockName(name);
				GameRegistry.registerBlock(blockToRegister, ItemMetadataSubblock.class, name);

				BlockSets.oreUbifier.put(new OreBlockInfo(oreBlock, parentMeta, stoneBlock, loop), blockToRegister);
				BlockSets.addOreBlock(blockToRegister, FracMethods.wtforesfrac);

				blockArray[loop] = blockToRegister;
			}
			//These are used to set ores of high density- doing this means I can call a negative density from the ubifier map and still get a block
			BlockSets.oreUbifier.put(new OreBlockInfo(oreBlock, parentMeta, stoneBlock, -1), blockToRegister);
			BlockSets.oreUbifier.put(new OreBlockInfo(oreBlock, parentMeta, stoneBlock, -2), blockToRegister);
			BlockSets.oreUbifier.put(new OreBlockInfo(oreBlock, parentMeta, stoneBlock, -3), blockToRegister);
			return blockArray;
		}
	}

	public static void registerOreSets(Block oreBlock, String oreType, int parentMeta){

		registerOverlaidOre(oreBlock, parentMeta, oreType, Blocks.stone, "stone", vanillaStone, "minecraft");
		if (Loader.isModLoaded("UndergroundBiomes"))
		{
			registerOverlaidOre(oreBlock, parentMeta, oreType, UBCblocks.IgneousStone, "igneous", UBCblocks.IgneousStoneList, "undergroundbiomes");
			registerOverlaidOre(oreBlock, parentMeta, oreType, UBCblocks.MetamorphicStone, "metamorphic", UBCblocks.MetamorphicStoneList, "undergroundbiomes");
			registerOverlaidOre(oreBlock, parentMeta, oreType, UBCblocks.SedimentaryStone, "sedimentary", UBCblocks.SedimentaryStoneList, "undergroundbiomes");
		}
		if (Loader.isModLoaded("TConstruct") && (oreType == "iron_ore" ||oreType == "gold_ore" ||oreType == "copper_ore" ||oreType == "aluminum_ore" ||oreType == "tin_ore" )){
			registerOverlaidOre(oreBlock, parentMeta, oreType, Blocks.gravel, "gravel", vanillaGravel, "minecraft");
		}
	}

	public static void register(){
		//Extra sets with non-stone backgrounds
		registerOverlaidOre(Blocks.diamond_ore, 0, "diamond_ore", Blocks.obsidian, "obsidian", vanillaObsidian, "minecraft");
		registerOverlaidOre(Blocks.redstone_ore, 0, "redstone_ore", Blocks.sand, "sand", vanillaSand, "minecraft");
		//PROBLEM: sandstone is a sided block, and my texturer cannot currently handle sided blocks
		//registerOverlaidOre(Blocks.redstone_ore, 0, "redstone_ore", Blocks.sandstone, "sandstone", vanillaSandstone, "minecraft");

		//Iterates through all the config set ores
		Iterator<AddCustomOre> iterator = WTFOresConfig.customOres.iterator();
		while (iterator.hasNext()){
			AddCustomOre newOre = (AddCustomOre)iterator.next();
			registerOreSets(newOre.oreBlock, newOre.textureName, newOre.metadata);
		}
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister){
		textures = new IIcon[16];
		for (int loop = 0; loop < textureNames.length; loop++){
			textures[loop] = iconRegister.registerIcon(WTFOres.modid+":"+textureNames[loop]);
			ClientProxy.registerBlockOverlay(textureNames[loop], parentLocations[loop], oreType, WTFOres.overlayDomain, false);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return textures[meta];
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		float oreLevelMod = (3F-oreLevel)/10F;
		float hardness =oreLevelMod + stoneBlock.getBlockHardness(world, x, y, z) * (this.parentBlock.getHarvestLevel(this.parentMeta)+1F);
		return hardness;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		if (this.parentBlock == Blocks.coal_ore){
			return true;
		}
		return parentBlock.getFlammability(world, x, y, z, face) > 0;
	}
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		if (this.parentBlock == Blocks.coal_ore){
			return Blocks.log.getFireSpreadSpeed(world, x, y, z, face);
		}
		return parentBlock.getFireSpreadSpeed(world, x, y, z, face);
	}
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		if (this.parentBlock == Blocks.coal_ore){
			return Blocks.log.getFlammability(world, x, y, z, face);
		}
		return Blocks.fire.getFlammability(this);
	}

	@Override
	public int getHarvestLevel(int metadata)
	{
		return parentBlock.getHarvestLevel(metadata);
	}
	@Override
	public void harvestBlock(World p_149636_1_, EntityPlayer p_149636_2_, int p_149636_3_, int p_149636_4_, int p_149636_5_, int p_149636_6_){
		parentBlock.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, parentMeta);;
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta){

		if (WTFOresConfig.enableDenseOres && this.oreLevel < 2){
			Block blockToSet = BlockSets.oreUbifier.get(new OreBlockInfo (this.parentBlock, this.parentMeta, this.stoneBlock, this.oreLevel+1));
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

}
