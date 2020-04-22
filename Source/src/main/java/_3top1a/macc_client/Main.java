package _3top1a.macc_client;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModSettings.MOD_ID, name = ModSettings.MOD_NAME, version = ModSettings.MOD_VERSION, acceptedMinecraftVersions = ModSettings.MOD_ACCEPTED_VERSIONS)
public class Main {

	@Instance
	public static Main instance;

	@EventHandler
	public static void Init(FMLPreInitializationEvent event) throws Exception {
		Connection.Connect();
		
	}

	@SidedProxy(clientSide = ModSettings.CLIENT_PROXY_CLASS, serverSide = ModSettings.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

}
