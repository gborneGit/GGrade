package fr.aang.ggrade.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {
	
	public static ItemStack getItem(Material material, String custom_name, List<String> lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(custom_name);
		if (lore != null && !lore.isEmpty())
			itemM.setLore(lore);
		item.setItemMeta(itemM);;
		return item;
	}
	
	public static String getItemName(ItemStack item) {
		if (item.getItemMeta() != null && !item.getItemMeta().getDisplayName().isEmpty()) {
			return item.getItemMeta().getDisplayName();
		}
		else {
			return item.getType().name().replace("_", " ").toLowerCase();
		}
	}
	
	public static Location parseStringToLoc(String world, String string) {
		
		String[] parseLoc = string.split(",");
		
		double x = Double.valueOf(parseLoc[0]);
		double y = Double.valueOf(parseLoc[1]);
		double z = Double.valueOf(parseLoc[2]);
		float face_x = Float.valueOf(parseLoc[3]);
		float face_y = Float.valueOf(parseLoc[4]);
		
		Location loc = new Location(Bukkit.getWorld(world), x, y, z, face_x, face_y);
		
		return loc;
	}
	
	public static String parseLocToString(Location loc) {
		String string = loc.getX() + "," + loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + "," + loc.getPitch();  
		return string;
	}
	
	public static ItemStack addLore(ItemStack item, List<String> lore) {
		
		ItemStack new_item = item.clone();
		
		ItemMeta itemM = new_item.getItemMeta();
		List<String> new_lore = itemM.getLore();
		
		if (new_lore == null)
			new_lore = new ArrayList<String>();
			
		for (int i = 0; i < lore.size(); i++) {
			new_lore.add(lore.get(i));
		}
		itemM.setLore(new_lore);
		new_item.setItemMeta(itemM);
		return new_item;
	}
	
	public static ItemStack addEnchantEffect(ItemStack item) {
		
		ItemStack new_item = item.clone();
		
		ItemMeta itemM = new_item.getItemMeta();
		itemM.addEnchant(Enchantment.DAMAGE_ALL, 1, false);
		itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		new_item.setItemMeta(itemM);
		return new_item;
	}
	
	public static List<String> listFormat(List<String> list) {
		
		List<String> new_list = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			new_list.add(list.get(i).replace('&', '§'));
		}
		return new_list;
	}
	
	public static boolean hasAvaliableSlot(Player player,int howmanyclear) {
		
		Inventory inv = player.getInventory();
		int check = 0;
		
		for (int i = 0; i < 36; i++) {
		    ItemStack item = inv.getItem(i);
		    if (item == null || item.getType() == Material.AIR) {
		        check++;
		    }
		}
		if(check >= howmanyclear)
			return true;
		else
			return false;
	}
	
}
