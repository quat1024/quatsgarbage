package quaternary.quatsgarbage;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = QuatsGarbage.MODID, name = QuatsGarbage.NAME, version = QuatsGarbage.VERSION)
public class QuatsGarbage {
	public static final String MODID = "quatsgarbage";
	public static final String NAME = "Quat's Garbage";
	public static final String VERSION = "GRADLE:VERSION";
	
	@Mod.EventHandler
	public static void preinit(FMLPreInitializationEvent e) {
		ModConfig.preinit(e);
		
		LongBois.preinit();
		
		MinecraftForge.EVENT_BUS.register(DecorationTweaks.class);
		MinecraftForge.TERRAIN_GEN_BUS.register(DecorationTweaks.class);
	}
	
	@Mod.EventHandler
	public static void init(FMLInitializationEvent e) {
		ModConfig.init();
		NoBreakPls.doIt();
	}
}
