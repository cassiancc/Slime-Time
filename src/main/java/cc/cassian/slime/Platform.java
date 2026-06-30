package cc.cassian.slime;

//? fabric {
import net.fabricmc.loader.api.FabricLoader;
//?} else {
/*import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
*///?}

import java.nio.file.Path;

public class Platform {
    public static Path getConfigDirectory() {
        //? fabric
        return FabricLoader.getInstance().getConfigDir();
        //? neoforge
        //return FMLPaths.CONFIGDIR.get();
    }

    public static boolean isModLoaded(String mod) {
        //? fabric
        return FabricLoader.getInstance().isModLoaded(mod);
        //? neoforge
        //return ModList.get().isLoaded(mod);
    }

	public static String loader() {
		//? fabric
		return "fabric";
		//? neoforge
		//return "neoforge";
	}
}
