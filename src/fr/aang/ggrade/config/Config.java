package fr.aang.ggrade.config;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;

import fr.aang.ggrade.Grade;
import fr.aang.ggrade.Main;
import fr.aang.ggrade.utils.Utils;

public class Config {
	
	private Main				_main;
	private File				_file;
	private YamlConfiguration	_config;
	private	String				_file_name;
	
	public Config(Main main, String file_name) {
		_file_name = file_name;
		_main = main;
		_config = loadConfig(file_name);
		readConfig();
	}
	
	private YamlConfiguration loadConfig(String file_name) {
		
		if(!_main.getDirectory().exists()) {
			_main.getDirectory().mkdir();
		}
		
		_file = new File(_main.getDataFolder(), file_name);
		
		if (!_file.exists()) {
			_main.saveResource(file_name, false);
		}
		
		return YamlConfiguration.loadConfiguration(_file);
	}
	
	public void reload() {
		_main.grades.clear();
		_config = loadConfig(_file_name);
		readConfig();
	}
	
	private void addGrade(ConfigurationSection section) {
		
		Grade grade = new Grade();
		
		grade.setGroup(section.getString("group"));
		grade.setName(section.getString("item.name").replace('&', '§'));
		grade.setItem(Material.getMaterial(section.getString("item.material")), section.getString("item.name").replace('&', '§'), Utils.listFormat(section.getStringList("item.lore")));
		grade.setPerm(section.getString("perm"));
		grade.setPrice(section.getDouble("price"));
		if (section.isSet("map"))
			grade.setLocation(Utils.parseStringToLoc(section.getString("map.world"), section.getString("map.loc")));
		if (section.isSet("give")) {
			Material	material = Material.getMaterial(section.getString("give.material"));
			Enchantment enchant = null;
			int			level = 0;
			if (section.isSet("give.enchants")) {
				enchant = EnchantmentWrapper.getByKey(NamespacedKey.minecraft("give.enchants.name"));
				level = section.getInt("give.enchants.level");
			}
			grade.setGive(material, enchant, level);
		}
		_main.grades.add(grade);
	}
	
	private void readConfig() {
		
		ConfigurationSection section;
		
		int i = 0;
		if (_config.getBoolean("tuto.enable") == true)
			_main.tuto_spawn = Utils.parseStringToLoc(_config.getString("tuto.world"), _config.getString("tuto.loc"));
		while ((section = _config.getConfigurationSection("grades." + i)) != null) {
			addGrade(section);
			i++;
		}
	}

}
