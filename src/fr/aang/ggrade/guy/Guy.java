package fr.aang.ggrade.guy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.aang.ggrade.Grade;
import fr.aang.ggrade.Main;
import fr.aang.ggrade.utils.Utils;

public class Guy {
	
	private Main _main;
	
	public Guy(Main main) {
		_main = main;
	}
	
	public void openGuy(Player player) {
		
		int slot = 0;
		while (slot < _main.grades.size() - 1)
			slot += 9;
		
		if (slot > 54)
			player.sendMessage("§c[✖] §cErreur: Trop de grades à afficher");
		else {
			
			player.closeInventory();
			player.updateInventory();
			
			Inventory inv = Bukkit.createInventory(null, slot, "§7Les Grades");
			
			int player_grade_id = _main.manager.getPlayerGradeId(player);
			
			for (int i = 1; i < _main.grades.size(); i++) {
				
				Grade grade = _main.grades.get(i);
				List<String> lore = new ArrayList<String>();
				
				if (player_grade_id < 0 || i != player_grade_id + 1) {
					if (i < player_grade_id + 1)
						lore.add("§aAcquis");
					else
						lore.add("§cVerrouillé");
					inv.setItem(i -  1, Utils.addLore(grade.getItem(), lore));
				}
				else {
					if (player_grade_id == 0 && _main.tuto_spawn != null) {
						lore.add("§7▪ §aFaites le §bTuto§a au spawn");
						lore.add("  §apour débloquer ce grade");
					}
					else
						lore.add("§e(●) §aAcheter §6" + grade.getPrice() + "⛃");
					ItemStack item = Utils.addLore(grade.getItem(), lore);
					item = Utils.addEnchantEffect(item);
					inv.setItem(i -  1, item);
				}
					
				
				
			}
			
			player.openInventory(inv);
		}
	}

}
