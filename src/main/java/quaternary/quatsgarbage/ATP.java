package quaternary.quatsgarbage;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import quaternary.quatsgarbage.ModConfig;
import quaternary.quatsgarbage.QuatsGarbage;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Mod.EventBusSubscriber(modid = QuatsGarbage.MODID)
public class ATP {
	static int oldCameraMode = 0;
	static int elytraFlyingTicks = 0;
	static Pattern[] whitelistPatterns = null;
	static Pattern[] blacklistPatterns = null;
	
	@SubscribeEvent
	public static void mountEvent(EntityMountEvent e) {
		if(!ModConfig.atp) return;
		
		Minecraft mc = Minecraft.getMinecraft();
		
		if(e.getEntity() == mc.player) {
			Entity mounting = e.getEntityBeingMounted();
			boolean doIt = false;
			
			if(whitelistPatterns == null || blacklistPatterns == null) parseConfigPatterns();
			
			//Additional whitelist
			if(whitelistPatterns.length > 0) {
				ResourceLocation entityLocation = EntityList.getKey(mounting);
				if(entityLocation != null) {
					String entityType = entityLocation.toString();
					for(Pattern p : whitelistPatterns) {
						if(p != null && p.matcher(entityType).matches()) {
							doIt = true;
							break;
						}
					}
				}
			}
			
			//Additional blacklist
			if(blacklistPatterns.length > 0) {
				ResourceLocation entityLocation = EntityList.getKey(mounting);
				if(entityLocation != null) {
					String entityType = entityLocation.toString();
					for(Pattern p : blacklistPatterns) {
						if(p != null && p.matcher(entityType).matches()) {
							doIt = false;
							break;
						}
					}
				}
			}
			
			if(doIt) {
				if(e.isMounting()) enterThirdPerson();
				else leaveThirdPerson();
			}
		}
	}
	
	@SubscribeEvent
	public static void onFrame(TickEvent.RenderTickEvent e) {
		if(e.phase != TickEvent.Phase.START) return;
		
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.isGamePaused()) return;
		if(mc.player == null) return;
		
		if(getCameraMode() == 2) { setCameraMode(0); }
	}
	
	@SubscribeEvent
	public static void onTick(TickEvent.ClientTickEvent e) {
		if(!ModConfig.atp) return;
		if(e.phase != TickEvent.Phase.START) return;
		
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.isGamePaused()) return;
		
		EntityPlayer player = mc.player;
		if(player == null) return;
		
		if(ModConfig.atpElytra) {
			if(player.isElytraFlying()) {
				if(elytraFlyingTicks == 5) {
					enterThirdPerson();
				}
				
				elytraFlyingTicks++;
			} else {
				if(elytraFlyingTicks != 0) {
					leaveThirdPerson();
				}
				
				elytraFlyingTicks = 0;
			}
		}
	}
	
	private static void enterThirdPerson() {
		oldCameraMode = Minecraft.getMinecraft().gameSettings.thirdPersonView;
		setCameraMode(1);
	}
	
	private static void leaveThirdPerson() {
		setCameraMode(oldCameraMode);
	}
	
	private static void setCameraMode(int mode) {
		Minecraft.getMinecraft().gameSettings.thirdPersonView = mode;
	}
	
	private static int getCameraMode() {
		return Minecraft.getMinecraft().gameSettings.thirdPersonView;
	}
	
	public static void parseConfigPatterns() {
		whitelistPatterns = new Pattern[ModConfig.atpWhitelist.length];
		for(int i = 0; i < ModConfig.atpWhitelist.length; i++) {
			try {
				whitelistPatterns[i] = Pattern.compile(ModConfig.atpWhitelist[i]);
			} catch(PatternSyntaxException e) {
				whitelistPatterns[i] = null;
			}
		}
		
		blacklistPatterns = new Pattern[ModConfig.atpBlacklist.length];
		for(int i = 0; i < ModConfig.atpBlacklist.length; i++) {
			try {
				blacklistPatterns[i] = Pattern.compile(ModConfig.atpBlacklist[i]);
			} catch(PatternSyntaxException e) {
				blacklistPatterns[i] = null;
			}
		}
	}
}