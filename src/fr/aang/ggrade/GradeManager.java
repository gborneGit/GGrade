package fr.aang.ggrade;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.aang.ggrade.utils.Utils;

public class GradeManager {
	
	private Main _main;
	
	public GradeManager(Main main) {
		_main = main;
	}
	
	public int getPlayerGradeId(Player player) {
		
		int id = -1;
		
		for (int i = 0; i < _main.grades.size(); i++) {
			if (player.hasPermission(_main.grades.get(i).getPerm()))
				id = i;
		}
		return id;
	}
	
	public int getGradeIdByName(String grade_name) {
		
		for (int id = 0; id < _main.grades.size(); id++) {
			if (_main.grades.get(id).getName().equals("§r" + grade_name))
				return id;
		}
		return -1;
	}
	
	public int getGradeIdByGroup(String group) {
		
		for (int id = 0; id < _main.grades.size(); id++) {
			if (_main.grades.get(id).getGroup().equals(group))
				return id;
		}
		return -1;
	}
	
	public Grade getNextGrade(Player player) {
		int id = getPlayerGradeId(player);
		if (id < 0 != id >= _main.grades.size() - 1)
			return null;
		else
			return _main.grades.get(id + 1);
	}
	
	public void upTask(Player player, Grade grade) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "menu " + player.getName() + " spawn");
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " parent set " + grade.getGroup());
		return ;
	}
	
	public void giveTask(Player player, ItemStack item) {
		
		 new BukkitRunnable() {
	        @Override
	        public void run() {
	        	    	
	        	if (Utils.hasAvaliableSlot(Bukkit.getPlayer(player.getName()), 1)) {
					player.getInventory().addItem(item);
					player.sendMessage("§a[⛃] §aVous avez reçu §e" + item.getAmount() + "§a x §e" + Utils.getItemName(item));
					cancel();
	        	}
	        	else {
	        		player.sendMessage("§c[⛃] §cVous avez §e1 récompense §cen attente, inventaire plein");
	        	}
	        }
		}.runTaskTimer(_main, 100, 20L);
		return ;
	}
	
	public void up(Player player, String group) {
		
		int grade_id = getGradeIdByGroup(group);
		int player_grade_id = getPlayerGradeId(player);
		
		if (grade_id == -1)
			player.sendMessage("§d[◎] §cCe grade n'existe pas");
		else {
			if (player_grade_id == -1) {
				player.sendMessage("§d[◎] §cVous avez le grade maximum");
			}
			else if (player_grade_id == grade_id - 1) {
				
				Grade grade = _main.grades.get(grade_id);
				
				if (_main.eco.getBalance(player) >= grade.getPrice()) {
					
					_main.eco.withdrawPlayer(player, grade.getPrice());
					player.sendMessage("§d[◎] §aVous avez acheter " + grade.getName() + "§a pour §6" + grade.getPrice() + "⛃");
					
					upTask(player, grade);
				
					if (grade.getGive() != null) {
						
						if (Utils.hasAvaliableSlot(player, 1)) {
							player.sendMessage("§a[⛃] §aVous avez reçu §e" + grade.getGive().getAmount() + "§a x §e"+ Utils.getItemName(grade.getGive()));
							player.getInventory().addItem(grade.getGive());
						}
						else {
							giveTask(player, grade.getGive());
						}
					}
				}
				else {
					player.sendMessage("§c[⛃] §cVous n'avez pas assez d'argent");
				}
			}
			else {
				player.sendMessage("§d[◎] §cVous devez être §7" + _main.grades.get(grade_id - 1).getName());
			}
		}
	}
}
