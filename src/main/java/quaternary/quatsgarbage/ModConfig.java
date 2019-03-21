package quaternary.quatsgarbage;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = QuatsGarbage.MODID)
public class ModConfig {
	public static Configuration config;
	
	public static boolean atp;
	public static String[] atpWhitelist;
	public static String[] atpBlacklist;
	public static boolean atpElytra;
	
	public static boolean crow;
	public static boolean crowVanilla;
	
	public static boolean fast;
	public static boolean fastPenalty;
	public static float baseFast;
	public static float obsidianFast;
	
	public static boolean longBois;
	public static int longExtra;
	
	public static boolean noBreak;
	public static String[] noBreakWhitelist;
	
	public static boolean replaceBiomeDecorator;
	public static boolean tweakBiomeSettings;
	public static boolean customOres;
	public static boolean doubleOreSpawnCounts;
	public static boolean moreQuartz;
	public static boolean killNetherLava;
	public static boolean killLake;
	public static boolean hackyMagic;
	
	public static void preinit(FMLPreInitializationEvent e) {
		config = new Configuration(e.getSuggestedConfigurationFile(), "despacito");
		config.load();
		
		//First-run detection
		if(config.getCategory("general").isEmpty() && FMLCommonHandler.instance().getSide() == Side.CLIENT) InitSettings.doThing();
	}
	
	public static void init() {
		read();
	}
	
	public static void read() {
		atp = config.getBoolean("atp", "atp", true, "Enable auto thirdperson stuff");
		atpWhitelist = config.getStringList("atpWhitelist", "atp", new String[] {
			"botania:player_mover"
		}, "atp whitelist regexes");
		atpBlacklist = config.getStringList("atpBlacklist", "atp", new String[]{}, "atp blacklist regexes");
		ATP.parseConfigPatterns();
		atpElytra = config.getBoolean("atpElytra", "atp", true, "atp elytra");
		
		crow = config.getBoolean("crow", "cr", true, "enable crowmap");
		crowVanilla = config.getBoolean("crowVanilla", "cr", false, "crowmap only vanilla maps");
		
		fast = config.getBoolean("fast", "fast", true, "Increase player break speed");
		fastPenalty = config.getBoolean("fastPenalty", "fast", true, "Cancel mining penalty effects");
		baseFast = config.getFloat("fastBase", "fast", 1.4f, 1f, 1000f, "Base mining speed multiplier");
		obsidianFast = config.getFloat("fastObsidian", "fast", 10f, 1f, 1000f, "Mining speed for obsidian");
		
		longBois = config.getBoolean("long", "long", true, "Should you have long af hand");
		longExtra = config.getInt("longExtra", "long", 4, 0, 99999, "How many meters is your hand");
		
		noBreak = config.getBoolean("noBreak", "nobreak", true, "Should stuff be strong bois");
		noBreakWhitelist = config.getStringList("noBreakWhitelist", "nobreak", new String[]{"minecraft:.*"}, "stuff that no break regex list");
		
		replaceBiomeDecorator = config.getBoolean("replaceBiomeDecorator", "gen", true, "causes fun stuff");
		tweakBiomeSettings = config.getBoolean("tweakBiomeSettings", "gen", true, "tweak biome settings to make them betterer");
		customOres = config.getBoolean("customOres", "gen", true, "make ore settings better (needs custom decorator)");
		doubleOreSpawnCounts = config.getBoolean("doubleOreSpawnCounts", "gen", true, "more ores (needs custom decorator)");
		moreQuartz = config.getBoolean("moreQuartz", "gen", true, "should the quartz be the more");
		killNetherLava = config.getBoolean("killNetherLava", "gen", true, "no floaty nether lava for u");
		killLake = config.getBoolean("killLake", "gen", true, "no lake");
		hackyMagic = config.getBoolean("hackyMagic", "gen", true, "do nether haxxxxxx");
		
		if(config.hasChanged()) config.save();
	}
	
	@SubscribeEvent
	public static void change(ConfigChangedEvent.OnConfigChangedEvent e) {
		if(e.getModID().equals(QuatsGarbage.MODID)) {
			read();
			
			NoBreakPls.doIt();
		}
	}
}
