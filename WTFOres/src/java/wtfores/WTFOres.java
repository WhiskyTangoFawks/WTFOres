package wtfores;

import java.util.HashSet;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import wtfcore.WTFCore;
import wtfcore.utilities.LangWriter;
import wtfcore.worldgen.WorldGenListener;
import wtfores.blocks.OverlayOre;
import wtfores.config.WTFOresConfig;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = WTFOres.modid, name = "WhiskyTangoFox's Ores", version = "1.46", dependencies = "after:UndergroundBiomes;after:TConstructs;required-after:WTFCore@[1.62,);after:WTFTweaks;required-after:TextureGeneratorLib")
public class WTFOres {

		public static  final String modid = WTFCore.WTFOres;

		@Instance(modid)
		public static WTFOres instance;

		//@SidedProxy(clientSide="cavebiomes.proxy.CBClientProxy", serverSide="cavebiomes.proxy.CommonProxy")
		//public static CommonProxy proxy;

		public static String alphaMaskDomain = "wtfores:textures/blocks/alphamasks/";
		public static String overlayDomain =   "wtfores:textures/blocks/overlays/";

		public static HashSet<String> orenames = new HashSet<String>();

		public static CreativeTabs oreTab = new CreativeTabs("WTF's Ores")
		{

			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem()
			{
				return Item.getItemFromBlock(Blocks.gold_ore);
			}

		};

		@EventHandler
		public void PreInit(FMLPreInitializationEvent preEvent)
		{
			

		}

		@EventHandler public void load(FMLInitializationEvent event)
		{
			
			MinecraftForge.ORE_GEN_BUS.register(new VanillOreGenCatcher());

		}
		
		@EventHandler
		public void PostInit(FMLPostInitializationEvent postEvent){
			WTFOresConfig.customConfig();
			OverlayOre.register();
			if (WTFOresConfig.genLangFile){
				LangWriter.genLangFile(orenames, "WTFOres_en_US.lang");
			}
			WorldGenListener.generator = new OreGenTweaked();

		}

}
