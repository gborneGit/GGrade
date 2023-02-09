package fr.aang.ggrade.guy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.aang.ggrade.Main;
import fr.aang.ggrade.utils.Utils;

public class GuyListener implements Listener {
	
	private Main	_main;
	
	public GuyListener(Main main) {
		_main = main;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if (current != null && current.getType() != null) {
			
			if (event.getView().getTitle().substring(2).equals("Les Grades")) {
				
				event.setCancelled(true);
				player.closeInventory();
				player.updateInventory();
				
				int player_grade_id = _main.manager.getPlayerGradeId(player);
				
				int grade_id = _main.manager.getGradeIdByName(current.getItemMeta().getDisplayName());
				
				if (player_grade_id >= 0 && grade_id >= 1 && grade_id - 1 == player_grade_id) {
					if (player_grade_id == 0 && _main.tuto_spawn != null) {
						player.sendMessage("§d[◎] §aFaites le §bTuto§a au spawn pour débloquer ce grade");
						return ;
					}
					else {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "menu " + player.getName() + " tp " + _main.grades.get(grade_id).getLoc().getWorld().getName() + " " + Utils.parseLocToString(_main.grades.get(grade_id).getLoc()) + " -1 " + _main.grades.get(grade_id).getName().replace("§", "&"));
						player.teleport(_main.grades.get(grade_id).getLoc());
						return ;
					}
				}
				else {
					player.sendMessage("§d[◎] §cVous devez être §7" + _main.grades.get(grade_id - 1).getName());
				}
			}
		}
		
	}

}
