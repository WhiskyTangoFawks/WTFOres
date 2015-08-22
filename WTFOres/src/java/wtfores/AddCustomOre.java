package wtfores;

import java.util.HashSet;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.BiomeDictionary;

public class AddCustomOre {

	//block,meta,texturename,gentype,maxheight,perChunk,v1, v2

	public static Random random = new Random();
	public Block oreBlock;
	public int metadata = 0;
	public String textureName;
	public int genType = 0;



	private float maxHeightPercent=100F;
	public void setMaxHeightPercent(int var){
		this.maxHeightPercent = (float)var/100F;
	}

	private float minHeightPercent=0F;
	public void setMinHeightPercent(int var){
		this.minHeightPercent = (float)var/100F;
	}

	private int maxPerChunk=-1;
	public void setMaxPerChunk(int var){
		this.maxPerChunk=var;
	}

	private int minPerChunk=-1;
	public void setMinPerChunk(int var){
		this.minPerChunk=var;
	}

	public int getPerChunk(){
		if (maxPerChunk==-1){return minPerChunk;}
		else if (minPerChunk==-1){return maxPerChunk;}
		else {
			return random.nextInt(maxPerChunk-minPerChunk)+minPerChunk;
		}
	}


	public HashSet<BiomeDictionary.Type> abundantBiomeTypes;


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
		result = prime
				* result
				+ ((abundantBiomeTypes == null) ? 0 : abundantBiomeTypes
						.hashCode());
		result = prime * result + genType;
		result = prime * result + Float.floatToIntBits(maxHeightPercent);
		result = prime * result + maxPerChunk;
		result = prime * result + metadata;
		result = prime * result + Float.floatToIntBits(minHeightPercent);
		result = prime * result + minPerChunk;
		result = prime * result
				+ ((oreBlock == null) ? 0 : oreBlock.hashCode());
		result = prime * result
				+ ((textureName == null) ? 0 : textureName.hashCode());
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
		if (abundantBiomeTypes == null) {
			if (other.abundantBiomeTypes != null)
				return false;
		} else if (!abundantBiomeTypes.equals(other.abundantBiomeTypes))
			return false;
		if (genType != other.genType)
			return false;
		if (Float.floatToIntBits(maxHeightPercent) != Float
				.floatToIntBits(other.maxHeightPercent))
			return false;
		if (maxPerChunk != other.maxPerChunk)
			return false;
		if (metadata != other.metadata)
			return false;
		if (Float.floatToIntBits(minHeightPercent) != Float
				.floatToIntBits(other.minHeightPercent))
			return false;
		if (minPerChunk != other.minPerChunk)
			return false;
		if (oreBlock == null) {
			if (other.oreBlock != null)
				return false;
		} else if (!oreBlock.equals(other.oreBlock))
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
