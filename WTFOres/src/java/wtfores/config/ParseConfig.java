package wtfores.config;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;
import wtfcore.WTFCore;
import wtfcore.proxy.ClientProxy;
import wtfores.AddCustomOre;
import wtfores.WTFOres;

public class ParseConfig {
	
	public static void parse(String fullOreString){
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
				else if (currentString[stringLoop].startsWith("biomeType=")){
					String[] biometypestring = currentString[stringLoop].split("@");
					customOre.biomeModifier.put(BiomeDictionary.Type.valueOf(biometypestring[0].substring(10)), Float.parseFloat(biometypestring[1]));
				}
									
				else if (currentString[stringLoop].startsWith("stone=")){
					String string = currentString[stringLoop].substring(6);
					customOre.stoneTypes.add(string);
				}
				else if (currentString[stringLoop].startsWith("dimension=")){
					customOre.dimension.add(Integer.parseInt(currentString[stringLoop].substring(10)));
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
				//Checks to see if the user has specified a custom dimension- if not, then it sets to the default 0
				if (customOre.dimension.isEmpty()){
					customOre.dimension.add(0);
				}
				if (customOre.stoneTypes.isEmpty()){
					customOre.stoneTypes.add("stone");
				}
				WTFOresConfig.customOres.add(customOre);
				if (overrideTexture==null){overrideTexture=customOre.textureName;}
				ClientProxy.registerBlockOverlayOverride(customOre.oreBlock, overrideTexture, "minecraft:stone", customOre.textureName+0, WTFOres.overlayDomain, false);
			}
		}
	}
}
