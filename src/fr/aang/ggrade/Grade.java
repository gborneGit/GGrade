package fr.aang.ggrade;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import fr.aang.ggrade.utils.Utils;

public class Grade {
	
	private String		_group;
	private String		_name;
	private ItemStack	_item;
	private String		_perm;
	private Location	_loc;
	private double		_price;
	private ItemStack	_give;
	
	public Grade() {}
	
	// SETTERS
	
	public void setGroup(String group) {
		_group = group;
	}
	
	public void setName(String name) {
		_name = name;
	}
	
	public void setItem(Material material, String name, List<String> lore) {
		_item = Utils.getItem(material, name, lore);
	}
	
	public void setPerm(String permission) {
		_perm = permission;
	}
	
	public void setLocation(Location loc) {
		_loc = loc;
	}
	
	public void setPrice(double price) {
		_price = price;
	}
	
	public void setGive(Material material, Enchantment enchant, int level) {
		_give = new ItemStack(material);
		if (enchant != null & level > 0)
			_give.addUnsafeEnchantment(enchant, level);
	}
	
	// GETTERS
	
	public String getGroup() {
		return _group;
	}
	
	public String getName() {
		return _name;
	}
	
	public ItemStack getItem() {
		return _item;
	}
	
	public String getPerm() {
		return _perm;
	}
	
	public Location getLoc() {
		return _loc;
	}
	
	public double getPrice() {
		return _price;
	}
	
	public ItemStack getGive() {
		return _give;
	}
}
