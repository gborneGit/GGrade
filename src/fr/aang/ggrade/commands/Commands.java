package fr.aang.ggrade.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aang.ggrade.Main;
import fr.aang.ggrade.guy.Guy;
import fr.aang.ggrade.utils.Utils;

public class Commands implements CommandExecutor {
	
	private Main	_main;
	private	Guy		_guy;
	
	public Commands(Main main) {
		_main = main;
		_guy = new Guy(main);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = Bukkit.getPlayer(args[0]);
		
		if (player != null) {
			
			if (!(sender instanceof Player)) {
				
				if (args.length == 1) {
					_guy.openGuy(player);
					return true;
				}
				else if (args.length == 2) {
					if (args[1].equals("tuto")) {
						
						if (_main.tuto_spawn != null) {
							if (player.hasPermission("group.coal1"))
								player.sendMessage("§d[◎] §cVous ne pouvez pas refaire le §bTuto");
							else
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "menu " + player.getName() + " tp " + "world_safe " + Utils.parseLocToString(_main.tuto_spawn));
							return true;
						}
					}
				}
				else if (args.length == 3) {
					if (args[1].equals("up")) {
						_main.manager.up(player, args[2]);
						return true;
					}
				}
			}
		}
		else if (sender instanceof Player && sender.hasPermission("ggrade.use")) {
			if (args[0].equals("reload"))
				_main.config.reload();
			sender.sendMessage("§a[GGrade] Reload success");
			System.out.println("&a[GGrade] Reload success");
		}
		return false;
	}
	
}
