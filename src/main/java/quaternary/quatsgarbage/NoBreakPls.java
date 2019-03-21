package quaternary.quatsgarbage;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.regex.Pattern;

public class NoBreakPls {
	public static void doIt() {
		Pattern[] wlist = new Pattern[ModConfig.noBreakWhitelist.length];
		String[] noBreakWhitelist = ModConfig.noBreakWhitelist;
		for(int i = 0; i < noBreakWhitelist.length; i++) {
			wlist[i] = Pattern.compile(noBreakWhitelist[i]);
		}
		
		for(Item item : ForgeRegistries.ITEMS.getValuesCollection()) {
			String xd = item.getRegistryName().toString();
			for(Pattern p : wlist) {
				if(p.matcher(xd).matches()) {
					thing(item);
					break;
				}
			}
		}
	}
	
	private static void thing(Item xd) {
		xd.setMaxDamage(0);
	}
}