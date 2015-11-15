package wtfores.config;

import java.io.File;
import java.util.HashSet;
import net.minecraftforge.common.config.Configuration;
import wtfcore.WTFCore;
import wtfcore.api.AddCustomOre;
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
			String wtftweaksDefault ="oreBlock=WTFTweaks:nitre_ore,metadata=0,useTexture=nitre_ore,genType=7,maxHeightPercent=90,minPerChunk=90,stone=stone,stone=gravel,stone=dirt,stone=sand;stone=sandstone";
			String genWTFTweaks = config.get("WTFTweaks", "WTFTweaks ore generation has been overriden", wtftweaksDefault).getString();
			ParseConfig.parse(genWTFTweaks);
		}
		if (Loader.isModLoaded("Metallurgy")){
			
		String metallurgyDefault = "oreBlock=Metallurgy:base.ore,useTexture=copper_ore,genType=2,maxHeightPerent=110,minPerChunk=62,maxPerChunk=82,v1=4,overrideTexture=copper_ore; oreBlock=Metallurgy:base.ore,metadata=1,useTexture=tin_ore,genType=2,maxHeightPerent=110,minPerChunk=60,maxPerChunk=80,v1=4,overrideTexture=tin_ore; oreBlock=Metallurgy:base.ore,metadata=2,useTexture=manganese_ore,genType=2,maxHeightPerent=110,minPerChunk=10,maxPerChunk=30,v1=5,overrideTexture=manganese_ore; oreBlock=Metallurgy:fantasy.ore,useTexture=prometheum_ore,genType=6,maxHeightPerent=110,minPerChunk=20,maxPerChunk=36,overrideTexture=prometheum_ore; oreBlock=Metallurgy:fantasy.ore,metadata=1,useTexture=deep_iron_ore,genType=6,maxHeightPerent=110,minPerChunk=10,maxPerChunk=24,overrideTexture=deep_iron_ore; oreBlock=Metallurgy:fantasy.ore,metadata=2,useTexture=infuscolium_ore,genType=6,maxHeightPerent=110,minPerChunk=10,maxPerChunk=22,overrideTexture=infuscolium_ore; oreBlock=Metallurgy:fantasy.ore,metadata=4,useTexture=oureclase_ore,genType=3,maxHeightPerent=110,minPerChunk=6,maxPerChunk=15,v1=3,overrideTexture=oureclase_ore; oreBlock=Metallurgy:fantasy.ore,metadata=5,useTexture=astral_silver_ore,genType=3,maxHeightPerent=110,minPerChunk=6,maxPerChunk=14,v1=3,overrideTexture=astral_silver_ore; oreBlock=Metallurgy:fantasy.ore,metadata=6,useTexture=carmot_ore,genType=2,maxHeightPerent=110,minPerChunk=1,maxPerChunk=11,v1=3,overrideTexture=carmot_ore; oreBlock=Metallurgy:fantasy.ore,metadata=7,useTexture=mithril_ore,genType=2,maxHeightPerent=110,minPerChunk=1,maxPerChunk=9,v1=3,overrideTexture=mithril_ore; oreBlock=Metallurgy:fantasy.ore,metadata=8,useTexture=rubracium_ore,genType=2,maxHeightPerent=110,minPerChunk=1,maxPerChunk=6,v1=3,overrideTexture=rubracium_ore; oreBlock=Metallurgy:fantasy.ore,metadata=11,useTexture=orichalcum_ore,genType=2,maxHeightPerent=110,minPerChunk=1,maxPerChunk=8,v1=4,overrideTexture=orichalcum_ore; oreBlock=Metallurgy:fantasy.ore,metadata=13,useTexture=adamantine_ore,genType=4,maxHeightPerent=110,minPerChunk=0,maxPerChunk=2,overrideTexture=adamantine_ore; oreBlock=Metallurgy:fantasy.ore,metadata=14,useTexture=altarus_ore,genType=4,maxHeightPerent=110,minPerChunk=0,maxPerChunk=2,overrideTexture=atlarus_ore; oreBlock=Metallurgy:precious.ore,useTexture=zinc_ore,genType=3,maxHeightPerent=110,minPerChunk=20,maxPerChunk=40,v1=6,overrideTexture=zinc_ore; oreBlock=Metallurgy:precious.ore,metadata=1,useTexture=silver_ore,genType=3,maxHeightPerent=110,minPerChunk=20,maxPerChunk=40,v1=5,overrideTexture=silver_ore; oreBlock=Metallurgy:precious.ore,metadata=3,useTexture=platinum_ore,genType=3,maxHeightPerent=110,minPerChunk=1,maxPerChunk=3,v1=4,overrideTexture=platinum_ore; oreBlock=Metallurgy:utility.ore,useTexture=sulfur_ore,genType=1,v1=2,v2=3,maxHeightPercent=110,minPerChunk=6,maxPerChunk=26; oreBlock=Metallurgy:utility.ore,metadata=1,useTexture=phosphorite_ore,genType=1,v1=2,v2=3,maxHeightPercent=110,minPerChunk=6,maxPerChunk=26; oreBlock=Metallurgy:utility.ore,metadata=2,useTexture=saltpeter_ore,genType=1,v1=2,v2=3,maxHeightPercent=110,minPerChunk=6,maxPerChunk=26; oreBlock=Metallurgy:utility.ore,metadata=3,useTexture=magnesium_ore,genType=1,v1=2,v2=3,maxHeightPercent=110,minPerChunk=6,maxPerChunk=26; oreBlock=Metallurgy:utility.ore,metadata=4,useTexture=bitumen_ore,genType=1,v1=2,v2=3,maxHeightPercent=110,minPerChunk=6,maxPerChunk=26; oreBlock=Metallurgy:utility.ore,metadata=5,useTexture=potash_ore,genType=1,v1=2,v2=3,maxHeightPercent=110,minPerChunk=6,maxPerChunk=26";
		String genMetallurgy = config.get("Metallurgy", "Metallurgy Ores", metallurgyDefault).getString();
		ParseConfig.parse(genMetallurgy);
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
