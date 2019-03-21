package quaternary.quatsgarbage;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import quaternary.quatsgarbage.ModConfig;
import quaternary.quatsgarbage.QuatsGarbage;

@Mod.EventBusSubscriber(modid = QuatsGarbage.MODID)
public class Crowmap {
	@SubscribeEvent
	public static void playerTickEvent(TickEvent.PlayerTickEvent e) {
		if(!ModConfig.crow) return;
		
		World world = e.player.world;
		if(world.isRemote) return;
		
		EntityPlayer player = e.player;
		
		for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
			if(i == player.inventory.currentItem) continue; //Already holding this map. No need to update it again
			
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(ModConfig.crowVanilla ? (stack.getItem() == Items.FILLED_MAP) : (stack.getItem() instanceof ItemMap)) {
				//Force the map data to update.
				ItemMap map = (ItemMap) stack.getItem();
				map.updateMapData(world, player, map.getMapData(stack, world));
			}
		}
	}
}
