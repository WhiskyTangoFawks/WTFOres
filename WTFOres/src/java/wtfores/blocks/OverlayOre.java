package wtfores.blocks;

import java.util.Iterator;
import org.apache.commons.lang3.text.WordUtils;
import wtfcore.blocks.IAlphaMaskedBlock;
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
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class OverlayOre extends OreChildBlock implements IAlphaMaskedBlock
{
	private IIcon[] textures;
	public static String[] vanillaStone = {"stone"};
	protected static String[] vanillaObsidian = {"obsidian"};
	protected static String[] vanillaSand = {"sand"};
	protected static String[] vanillaSandstone = {"sandstone"};
	protected static String[] vanillaGravel = {"gravel"};


	public  OverlayOre(Block oreBlock, int parentMeta, int oreLevel, Block stoneBlock, String oreType, String[] stoneNames, String domain){
		super(oreBlock, parentMeta, stoneBlock);

		loadTextureStrings(oreType, stoneNames, domain);
		
		this.oreLevel = oreLevel;
		//if (this.parentBlock != Blocks.lit_redstone_ore){this.setCreativeTab(WTFOres.oreTab);}
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
			AddCustomOre newOre = iterator.next();
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



	
}
