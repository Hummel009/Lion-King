package lionking.common.entity;

import net.minecraft.world.World;

public class LKEntityLioness extends LKEntityLionBase {
	public LKEntityLioness(World world) {
		super(world);
		setSize(1.2F, 1.3F);
	}

	public LKCharacterSpeech getCharacterSpeech() {
		return LKCharacterSpeech.LIONESS;
	}

	public LKCharacterSpeech getChildCharacterSpeech() {
		return LKCharacterSpeech.LIONESS_CUB;
	}

	public String getAnimalName() {
		return "Lioness";
	}
}
