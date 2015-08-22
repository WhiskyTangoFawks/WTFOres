package wtfores.gencores;

import cpw.mods.fml.common.Loader;

public class GenOreProvider {

	public static VOreGen getGenCore(){
		if (Loader.isModLoaded("UndergroundBiomes")){
			return new UBCOreGen();
		}
		else{
			return new VOreGen();
		}
	}
	
}
