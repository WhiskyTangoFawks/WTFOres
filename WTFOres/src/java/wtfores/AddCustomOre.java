package wtfores;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class AddCustomOre {

	//block,meta,texturename,gentype,maxheight,perChunk,v1, v2

	public static Random random = new Random();
	public Block oreBlock;
	public int metadata = 0;
	public String textureName;
	public int genType = 0;
	public HashMap<BiomeDictionary.Type, Float> biomeModifier = new HashMap<BiomeDictionary.Type, Float>(); 
	public HashSet<Integer> dimension = new HashSet<Integer>();
	public HashSet<Block> stoneTypes;
	
	private float maxHeightPercent=100F;
	private float minHeightPercent=0F;
	private int maxPerChunk=-1;
	private int minPerChunk=-1;
	
	public void setMaxHeightPercent(int var){
		this.maxHeightPercent = (float)var/100F;
	}
	
	public void setMinHeightPercent(int var){
		this.minHeightPercent = (float)var/100F;
	}
	
	public void setMaxPerChunk(int var){
		this.maxPerChunk=var;
	}
	
	public void setMinPerChunk(int var){
		this.minPerChunk=var;
	}

	public int getPerChunk(Type[] biome){
		int genNumber;
		if (maxPerChunk==-1){genNumber =  minPerChunk;}
		else if (minPerChunk==-1){genNumber = maxPerChunk;}
		else {
			genNumber = random.nextInt(maxPerChunk-minPerChunk)+minPerChunk;
		}
		for (int loop=0; loop < biome.length; loop++){
			if (biomeModifier.containsKey(biome[loop])){
				genNumber*=biomeModifier.get(biome[loop]);
			}
		}		
		return genNumber;
	}

	public int getHeight(int surface) {
		int maxHeight = MathHelper.floor_float(maxHeightPercent*surface);
		int minHeight = MathHelper.floor_float(minHeightPercent*surface);
		return random.nextInt(maxHeight-minHeight)+minHeight;
	}

	public int var1=5;
	public void setVar1(int var){
		this.var1=var;
	}
	public int var2=5;
	public void setVar2(int var){
		this.var2=var;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((biomeModifier == null) ? 0 : biomeModifier.hashCode());
		result = prime * result + ((dimension == null) ? 0 : dimension.hashCode());
		result = prime * result + genType;
		result = prime * result + Float.floatToIntBits(maxHeightPercent);
		result = prime * result + maxPerChunk;
		result = prime * result + metadata;
		result = prime * result + Float.floatToIntBits(minHeightPercent);
		result = prime * result + minPerChunk;
		result = prime * result + ((oreBlock == null) ? 0 : oreBlock.hashCode());
		result = prime * result + ((stoneTypes == null) ? 0 : stoneTypes.hashCode());
		result = prime * result + ((textureName == null) ? 0 : textureName.hashCode());
		result = prime * result + var1;
		result = prime * result + var2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddCustomOre other = (AddCustomOre) obj;
		if (biomeModifier == null) {
			if (other.biomeModifier != null)
				return false;
		} else if (!biomeModifier.equals(other.biomeModifier))
			return false;
		if (dimension == null) {
			if (other.dimension != null)
				return false;
		} else if (!dimension.equals(other.dimension))
			return false;
		if (genType != other.genType)
			return false;
		if (Float.floatToIntBits(maxHeightPercent) != Float.floatToIntBits(other.maxHeightPercent))
			return false;
		if (maxPerChunk != other.maxPerChunk)
			return false;
		if (metadata != other.metadata)
			return false;
		if (Float.floatToIntBits(minHeightPercent) != Float.floatToIntBits(other.minHeightPercent))
			return false;
		if (minPerChunk != other.minPerChunk)
			return false;
		if (oreBlock == null) {
			if (other.oreBlock != null)
				return false;
		} else if (!oreBlock.equals(other.oreBlock))
			return false;
		if (stoneTypes == null) {
			if (other.stoneTypes != null)
				return false;
		} else if (!stoneTypes.equals(other.stoneTypes))
			return false;
		if (textureName == null) {
			if (other.textureName != null)
				return false;
		} else if (!textureName.equals(other.textureName))
			return false;
		if (var1 != other.var1)
			return false;
		if (var2 != other.var2)
			return false;
		return true;
	}

	
}
