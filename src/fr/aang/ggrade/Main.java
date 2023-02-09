package fr.aang.ggrade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.aang.ggrade.commands.Commands;
import fr.aang.ggrade.config.Config;
import fr.aang.ggrade.guy.GuyListener;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	
	public Config		config;
	public List<Grade>	grades = new ArrayList<Grade>();
	public GradeManager	manager;
	public Economy		eco;
	public Location		tuto_spawn;

	@Override
	public void onEnable() {
		
		if (!setupEconomy()) {
			System.out.println(ChatColor.RED + "You must have Vault");
			getServer().getPluginManager().disablePlugin(this);
			return ;
		}
		
		this.config = new Config(this, "config.yml");
		this.manager = new GradeManager(this);
		
		getCommand("grade").setExecutor(new Commands(this));
		getServer().getPluginManager().registerEvents(new GuyListener(this), this);
	}
	
	@Override
	public void onDisable() {

	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economy != null)
			eco = economy.getProvider();
		return (economy != null);
	}
	
	public File getDirectory() {
		return getDataFolder();
	}
}
