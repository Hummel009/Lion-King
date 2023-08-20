package lionking.common.entity;

import net.minecraft.world.World;

public class LKEntityLion extends LKEntityLionBase {
	public LKEntityLion(World world) {
		super(world);
		setSize(1.3F, 1.6F);
	}

	public LKCharacterSpeech getCharacterSpeech() {
		return LKCharacterSpeech.LION;
	}

	public LKCharacterSpeech getChildCharacterSpeech() {
		return LKCharacterSpeech.LION_CUB;
	}

	public String getAnimalName() {
		return "Lion";
	}
}
