package quaternary.quatsgarbage;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import quaternary.quatsgarbage.ModConfig;
import quaternary.quatsgarbage.QuatsGarbage;

@Mod.EventBusSubscriber(modid = QuatsGarbage.MODID)
public class FastBois {
	@SubscribeEvent
	public static void breakSp(PlayerEvent.BreakSpeed e) {
		EntityPlayer player = e.getEntityPlayer();
		if(player == null || !ModConfig.fast) return;
		
		e.setNewSpeed(e.getNewSpeed() * ModConfig.baseFast);
		
		if(ModConfig.fastPenalty) {
			boolean underwaterPenalty = player.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier(player);
			if(underwaterPenalty) e.setNewSpeed(e.getNewSpeed() * 5);
			
			boolean flyPenalty = !player.onGround;
			if(flyPenalty) e.setNewSpeed(e.getNewSpeed() * 5);
		}
		
		if(e.getState().getBlock() == Blocks.OBSIDIAN) e.setNewSpeed(e.getNewSpeed() * ModConfig.obsidianFast);
	}
}
