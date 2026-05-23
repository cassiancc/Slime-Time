package cc.cassian.slime;

//? fabric {
/*import net.fabricmc.loader.api.FabricLoader;
*///?} else {
import net.neoforged.fml.loading.FMLPaths;
//?}

import java.nio.file.Path;

public class Platform {
    public static Path getConfigDirectory() {
        //? fabric
        //return FabricLoader.getInstance().getConfigDir();
        //? neoforge
        return FMLPaths.CONFIGDIR.get();
    }
}
