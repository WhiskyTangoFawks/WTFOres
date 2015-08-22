package wtfores;

import java.io.File;
import java.util.HashSet;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import wtfcore.WTFCore;
import wtfcore.proxy.ClientProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;

public class WTFOresConfig {


	//this option would currently bug out the ore counter
	public static boolean enableDenseOres;

	public static HashSet<AddCustomOre> customOres = new HashSet<AddCustomOre>();

	public static boolean genLangFile;

	public static void customConfig() {

		Configuration config = new Configuration(new File("config/WTFOresConfig.cfg"));

		config.load();

		genLangFile = config.get("Lang File Generation", "Generate new en_US.lang file", false).getBoolean();

		String genCoal = config.get("Vanilla Ores", "Vanilla Coal Generation", "oreBlock=minecraft:coal_ore,useTexture=coal_ore,genType=1,v1=6,v2=6,maxHeightPercent=110,minPerChunk=90,maxPerChunk=160").getString();
		parseConfig(genCoal);

		String genIron = config.get("Vanilla Ores", "Vanilla iron generation", "oreBlock=minecraft:iron_ore,useTexture=iron_ore,genType=2,v1=8,maxHeightPercent=75,minPerChunk=80").getString();
		parseConfig(genIron);

		String genDiamond = config.get("Vanilla Ores", "Vanilla diamond generation", "oreBlock=minecraft:diamond_ore,useTexture=diamond_ore,genType=4,maxHeightPercent=33,minPerChunk=8").getString();
		parseConfig(genDiamond);

		String genRedstone = config.get("Vanilla Ores", "Vanilla redstone generation", "oreBlock=minecraft:redstone_ore,useTexture=redstone_ore,genType=5,v1=6,maxHeightPercent=40,minPerChunk=8").getString();
		parseConfig(genRedstone);

		String genLapis = config.get("Vanilla Ores", "Vanilla lapis generation", "oreBlock=minecraft:lapis_ore,useTexture=lapis_ore,genType=6,maxHeightPercent=52,minPerChunk=8").getString();
		parseConfig(genLapis);

		String genGold = config.get("Vanilla Ores", "Vanilla gold generation", "oreBlock=minecraft:gold_ore,useTexture=gold_ore,genType=3,v1=8,maxHeightPercent=45,minPerChunk=12").getString();
		parseConfig(genGold);

		String genEmerald = config.get("Vanilla Ores", "Vanilla emerald generation", "oreBlock=minecraft:emerald_ore,useTexture=emerald_ore,genType=4,maxHeightPercent=95,minPerChunk=0,maxPerChunk=5").getString();
		parseConfig(genEmerald);

		if (Loader.isModLoaded("TConstruct")){
			String tconDefault = "oreBlock=TConstruct:SearedBrick,metadata=5,useTexture=aluminum_ore,genType=2,maxHeightPercent=75,minPerChunk=50,v1=10,overrideTexture=ore_aluminum;"
								+ "oreBlock=TConstruct:SearedBrick,metadata=3,useTexture=copper_ore,genType=3,maxHeightPerent=90,minPerChunk=65,v1=10,overrideTexture=ore_copper;"
								+ "oreBlock=TConstruct:SearedBrick,metadata=4,useTexture=tin_ore,genType=1,maxHeightPercent=110,minPerChunk=60,maxPerChunk=90,v1=3,v2=3,overrideTexture=ore_tin";
			String genTcon =  config.get("Tinker's Constructs", "Don't forget to disable ore generation in the Tinker's config", tconDefault).getString();
			parseConfig(genTcon);
		}

		if (Loader.isModLoaded(WTFCore.WTFTweaks)){
			String wtftweaksDefault ="oreBlock=WTFTweaks:sulfur_ore,metadata=0,useTexture=sulfur_ore,genType=1,maxHeightPercent=110,minPerChunk=20,v1=3,v2=3;oreBlock=WTFTweaks:nitre_ore,metadata=0,useTexture=nitre_ore,genType=0,maxHeightPercent=66,minPerChunk=60,v1=8";
			String genWTFTweaks = config.get("WTFTweaks", "WTFTweaks ore generation has been overriden", wtftweaksDefault).getString();
			parseConfig(genWTFTweaks);
		}

		String fullOreString;

		fullOreString= config.get("Ubified, Fracturing, Variable Density Ores", "Add Your custom ores here", "").getString();
		if (fullOreString != ""){
			parseConfig(fullOreString);
		}


		enableDenseOres = config.get("Ore Generation", "Use dense ores during vein generation", true).getBoolean();

		config.save();

	}

	public static void parseConfig(String fullOreString){
		//split it at each ; into an array
		fullOreString = fullOreString.replaceAll("\\s","");
		String[] oreStringArray = fullOreString.split(";");

		for (int loop = 0; loop < oreStringArray.length; loop ++){
			String[] currentString = oreStringArray[loop].split(",");

			AddCustomOre customOre = new AddCustomOre();
			String overrideTexture = null;

			WTFCore.log.info ("WTF-ores: loading from "+  oreStringArray[loop]);

			for (int stringLoop = 0; stringLoop < currentString.length; stringLoop++){

				if (currentString[stringLoop].startsWith("oreBlock=")){
					customOre.oreBlock = GameData.getBlockRegistry().getObject(currentString[stringLoop].substring(9));
				}
				else if (currentString[stringLoop].startsWith("metadata=")){
					customOre.metadata = Integer.parseInt(currentString[stringLoop].substring(9));
				}
				else if (currentString[stringLoop].startsWith("useTexture=")){
					customOre.textureName = currentString[stringLoop].substring(11);
				}
				else if (currentString[stringLoop].startsWith("genType=")){
					customOre.genType = Integer.parseInt(currentString[stringLoop].substring(8));
				}
				else if (currentString[stringLoop].startsWith("maxHeightPercent=")){
					customOre.setMaxHeightPercent(Integer.parseInt(currentString[stringLoop].substring(17)));
				}
				else if (currentString[stringLoop].startsWith("minHeightPercent=")){
					customOre.setMinHeightPercent(Integer.parseInt(currentString[stringLoop].substring(17)));
				}
				else if (currentString[stringLoop].startsWith("maxPerChunk=")){
					customOre.setMaxPerChunk(Integer.parseInt(currentString[stringLoop].substring(12)));
				}
				else if (currentString[stringLoop].startsWith("minPerChunk=")){
					customOre.setMinPerChunk(Integer.parseInt(currentString[stringLoop].substring(12)));
				}
				else if (currentString[stringLoop].startsWith("v1=")){
					customOre.var1 = Integer.parseInt(currentString[stringLoop].substring(3));
				}
				else if (currentString[stringLoop].startsWith("v2=")){
					customOre.var2 = Integer.parseInt(currentString[stringLoop].substring(3));
				}
				else if (currentString[stringLoop].startsWith("overrideTexture=")){
					overrideTexture = currentString[stringLoop].substring(8);
				}
				else if (currentString[stringLoop].startsWith("abundantBiomeType=")){
					customOre.abundantBiomeTypes.add(BiomeDictionary.Type.valueOf(currentString[stringLoop].substring(18)));
				}

				else {
					WTFCore.log.info("WTFOres CustomOre Config: Cannot parse " + currentString[stringLoop]);
				}


			}
			if (customOre.oreBlock == null || customOre.oreBlock == Blocks.air){
				WTFCore.log.info("Adding custom ores, block not found for " +  oreStringArray[loop]);
			}
			else {
				//the default required to gen the AddCustomOre is oreBlock, metadata, useTexture, and genType
				WTFCore.log.info("Adding custom ores, block loaded: " +  customOre.oreBlock.getUnlocalizedName());
				customOres.add(customOre);
				if (overrideTexture==null){overrideTexture=customOre.textureName;}
				ClientProxy.registerBlockOverlayOverride(customOre.oreBlock, overrideTexture, "minecraft:stone", customOre.textureName+0, WTFOres.overlayDomain, false);
			}
		}
	}
}
