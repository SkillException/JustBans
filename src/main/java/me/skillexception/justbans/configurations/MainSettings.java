
package me.skillexception.justbans.configurations;

import me.skillexception.justbans.JustBansMain;
import me.skillexception.justbans.object.BanTime;
import me.skillexception.justbans.object.BanTimeUnit;
import me.skillexception.justbans.utils.StartupStatus;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MainSettings {

    private static MainSettings mainSettings;

    public BanTime standardbanlength;
    public String dateformat = "dd.MM.yyyy hh:mm";

    private YamlConfiguration config;
    private File folder = new File("plugins//JustBans");
    private File file = new File(folder, "config.yml");
    public MainSettings(){
        if(!folder.exists()){
            folder.mkdirs();
        }
        if(!file.exists()){
            JustBansMain.instance.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        dateformat = config.getString("bans.dateformat");
        mainSettings = this;
        StartupStatus.configurationStatus = true;
    }

    public static MainSettings getMainSettings() {
        return mainSettings;
    }
}
