package quaternary.quatsgarbage;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = QuatsGarbage.MODID)
public class LongBois {
	private static final UUID xd = UUID.fromString("0986917b-66cf-4987-acdf-b16709c6074e");
	private static AttributeModifier xdd;
	
	public static void preinit() {
		xdd = new AttributeModifier(
			xd,
			"Longg hand wow",
			ModConfig.longExtra,
			0
		).setSaved(false);
	}
	
	@SubscribeEvent
	public static void joinWorld(EntityJoinWorldEvent e) {
		if(ModConfig.longBois && e.getEntity() instanceof EntityPlayer && !e.getWorld().isRemote) {
			doIt((EntityPlayer) e.getEntity());
		}
	}
	
	@SubscribeEvent
	public static void clone(PlayerEvent.Clone e) {
		if(ModConfig.longBois && !e.isWasDeath()) //What the fuck is this method name
			doIt(e.getEntityPlayer());
	}
	
	private static void doIt(EntityPlayer p) {
		IAttributeInstance reach = p.getEntityAttribute(EntityPlayer.REACH_DISTANCE);
		if(!reach.hasModifier(xdd))	reach.applyModifier(xdd);
	}
}
