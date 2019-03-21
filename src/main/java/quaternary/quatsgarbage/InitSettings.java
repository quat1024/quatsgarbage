package quaternary.quatsgarbage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.input.Keyboard;

public class InitSettings {
	public static void doThing() {
		GameSettings s = Minecraft.getMinecraft().gameSettings;
		s.advancedItemTooltips = true;
		s.autoJump = false;
		s.keyBindDrop.setKeyCode(Keyboard.KEY_J);
		s.keyBindChat.setKeyCode(Keyboard.KEY_RETURN);
		s.enableVsync = false;
		s.limitFramerate = 260;
		s.fovSetting = 110.0f;
		s.mipmapLevels = 0;
		s.showSubtitles = true;
		s.gammaSetting = 1f;
		s.saveOptions();
		
		Configuration forgeCfg = ForgeModContainer.getConfig();
		
		forgeCfg.get("general", "dimensionUnloadQueueDelay", 300).set(300);
		forgeCfg.get("client", "alwaysSetupTerrainOffThread", true).set(true);
		
		try {
			ReflectionHelper.findMethod(ForgeModContainer.class, "syncConfig", "syncConfig", Boolean.TYPE).invoke(null, false);
		} catch(Exception e) {e.printStackTrace();}
	}
}
