package wtfores.config;

import java.io.File;
import java.util.HashSet;
import net.minecraftforge.common.config.Configuration;
import wtfcore.WTFCore;
import wtfores.AddCustomOre;
import cpw.mods.fml.common.Loader;

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
		ParseConfig.parse(genCoal);

		String genIron = config.get("Vanilla Ores", "Vanilla iron generation", "oreBlock=minecraft:iron_ore,useTexture=iron_ore,genType=2,v1=8,maxHeightPercent=75,minPerChunk=80").getString();
		ParseConfig.parse(genIron);

		String genDiamond = config.get("Vanilla Ores", "Vanilla diamond generation", "oreBlock=minecraft:diamond_ore,useTexture=diamond_ore,genType=4,maxHeightPercent=33,minPerChunk=8,stone=stone,stone=obsidian").getString();
		ParseConfig.parse(genDiamond);

		String genRedstone = config.get("Vanilla Ores", "Vanilla redstone generation", "oreBlock=minecraft:redstone_ore,useTexture=redstone_ore,genType=5,v1=6,maxHeightPercent=40,minPerChunk=8,stone=stone,stone=sand").getString();
		ParseConfig.parse(genRedstone);

		String genLapis = config.get("Vanilla Ores", "Vanilla lapis generation", "oreBlock=minecraft:lapis_ore,useTexture=lapis_ore,genType=6,maxHeightPercent=52,minPerChunk=8").getString();
		ParseConfig.parse(genLapis);

		String genGold = config.get("Vanilla Ores", "Vanilla gold generation", "oreBlock=minecraft:gold_ore,useTexture=gold_ore,genType=3,v1=8,maxHeightPercent=45,minPerChunk=12").getString();
		ParseConfig.parse(genGold);

		String genEmerald = config.get("Vanilla Ores", "Vanilla emerald generation", "oreBlock=minecraft:emerald_ore,useTexture=emerald_ore,genType=4,maxHeightPercent=95,minPerChunk=0,maxPerChunk=5").getString();
		ParseConfig.parse(genEmerald);
		
		String genQuartz = config.get("Vanilla Ores", "Vanilla quartz generation", "oreBlock=minecraft:quartz_ore,useTexture=quartz_ore,genType=6,maxHeightPercent=100,minPerChunk=90,maxPerChunk=160,stone=netherrack,dimension=-1").getString();
		ParseConfig.parse(genQuartz);
		
		if (Loader.isModLoaded("TConstruct")){
			String tconDefault = "oreBlock=TConstruct:SearedBrick,metadata=5,useTexture=aluminum_ore,genType=2,maxHeightPercent=75,minPerChunk=50,v1=10,overrideTexture=ore_aluminum;"
								+ "oreBlock=TConstruct:SearedBrick,metadata=3,useTexture=copper_ore,genType=3,maxHeightPerent=90,minPerChunk=65,v1=10,overrideTexture=ore_copper;"
								+ "oreBlock=TConstruct:SearedBrick,metadata=4,useTexture=tin_ore,genType=1,maxHeightPercent=110,minPerChunk=60,maxPerChunk=90,v1=3,v2=3,overrideTexture=ore_tin";
			String genTcon =  config.get("Tinker's Constructs", "Don't forget to disable ore generation in the Tinker's config", tconDefault).getString();
			ParseConfig.parse(genTcon);
		}

		if (Loader.isModLoaded(WTFCore.WTFTweaks)){
			String wtftweaksDefault ="oreBlock=WTFTweaks:sulfur_ore,metadata=0,useTexture=sulfur_ore,genType=1,maxHeightPercent=110,minPerChunk=20,v1=3,v2=3;oreBlock=WTFTweaks:nitre_ore,metadata=0,useTexture=nitre_ore,genType=0,maxHeightPercent=66,minPerChunk=60,v1=8";
			String genWTFTweaks = config.get("WTFTweaks", "WTFTweaks ore generation has been overriden", wtftweaksDefault).getString();
			ParseConfig.parse(genWTFTweaks);
		}

		String fullOreString;

		fullOreString= config.get("Ubified, Fracturing, Variable Density Ores", "Add Your custom ores here", "").getString();
		if (fullOreString != ""){
			ParseConfig.parse(fullOreString);
		}


		enableDenseOres = config.get("Ore Generation", "Use dense ores during vein generation", true).getBoolean();

		config.save();

	}

	
}
