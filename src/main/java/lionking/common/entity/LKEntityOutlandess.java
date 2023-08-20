package lionking.common.entity;

import lionking.common.LKLevelData;
import net.minecraft.world.World;

public class LKEntityOutlandess extends LKEntityOutlander {
	public LKEntityOutlandess(World world) {
		super(world);
		setSize(1.2F, 1.3F);
	}

	@Override
	public boolean isHostile() {
		if (inMound) {
			return LKLevelData.outlandersHostile == 1 || angerLevel > 0;
		} else {
			return true;
		}
	}
}
