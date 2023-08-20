package lionking.common.entity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import lionking.common.LKMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LKEntities {
	public static HashMap creatures = new LinkedHashMap();

	public static void registerCreature(Class entityClass, String name, int id, int eggBackground, int eggSpots) {
		EntityRegistry.registerModEntity(entityClass, name, id, LKMod.instance, 80, 3, true);
		LanguageRegistry.instance().addStringLocalization("entity." + FMLCommonHandler.instance().findContainerFor(LKMod.instance).getModId() + "." + name + ".name", name);
		creatures.put(id, new EntityEggInfo(id, eggBackground, eggSpots));
	}

	public static void registerEntity(Class entityClass, String name, int id) {
		EntityRegistry.registerModEntity(entityClass, name, id, LKMod.instance, 80, 3, true);
		LanguageRegistry.instance().addStringLocalization("entity." + FMLCommonHandler.instance().findContainerFor(LKMod.instance).getModId() + "." + name + ".name", name);
	}

	public static int getEntityID(Entity entity) {
		EntityRegistry.EntityRegistration registry = EntityRegistry.instance().lookupModSpawn(entity.getClass(), false);
		if (registry != null) {
			return registry.getModEntityId();
		}
		return 0;
	}

	public static int getEntityIDFromClass(Class entityClass) {
		EntityRegistry.EntityRegistration registry = EntityRegistry.instance().lookupModSpawn(entityClass, false);
		if (registry != null) {
			return registry.getModEntityId();
		}
		return 0;
	}

	public static Entity createEntityByID(int id, World world) {
		Entity entity = null;
		try {
			ModContainer container = FMLCommonHandler.instance().findContainerFor(LKMod.instance);
			EntityRegistry.EntityRegistration registry = EntityRegistry.instance().lookupModSpawn(container, id);
			Class<? extends Entity> entityClass = registry.getEntityClass();

			if (entityClass != null) {
				entity = entityClass.getConstructor(new Class[]{World.class}).newInstance(world);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (entity == null) {
			System.out.println("Skipping Entity with id " + id);
		}
		return entity;
	}

	public static String getStringFromID(int id) {
		ModContainer container = FMLCommonHandler.instance().findContainerFor(LKMod.instance);
		EntityRegistry.EntityRegistration registry = EntityRegistry.instance().lookupModSpawn(container, id);
		if (registry != null) {
			return registry.getEntityName();
		}
		return null;
	}
}
