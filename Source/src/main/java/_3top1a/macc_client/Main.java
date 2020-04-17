package _3top1a.macc_client;

import java.lang.System;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.CommandException;
import net.minecraft.command.server.CommandMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@Mod(modid = ModSettings.MOD_ID, name = ModSettings.MOD_NAME, version = ModSettings.MOD_VERSION, acceptedMinecraftVersions = ModSettings.MOD_ACCEPTED_VERSIONS)
public class Main {
	
	static boolean CPIsOpen = false;
	
	@Instance
	public static Main instance;
	
	@EventHandler
	public static void Init( FMLPreInitializationEvent event ) throws Exception {Connection.Connect(); }
	
	@SidedProxy(clientSide = ModSettings.CLIENT_PROXY_CLASS, serverSide = ModSettings.COMMON_PROXY_CLASS )
	public static CommonProxy proxy;

}
