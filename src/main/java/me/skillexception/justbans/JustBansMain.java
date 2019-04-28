package me.skillexception.justbans;

import me.skillexception.justbans.command.Ban;
import me.skillexception.justbans.command.Unban;
import me.skillexception.justbans.configurations.MainSettings;
import me.skillexception.justbans.configurations.Messages;
import me.skillexception.justbans.configurations.MySQL;
import me.skillexception.justbans.event.Login;
import me.skillexception.justbans.manager.BanManager;
import me.skillexception.justbans.utils.StartupStatus;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JustBansMain extends JavaPlugin {
    public static JustBansMain instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getConsoleSender().sendMessage("\n\n" +
                "§a   ___           _  ______                 \n" +
                "§a  |_  |         | | | ___ \\                \n" +
                "§a    | |_   _ ___| |_| |_/ / __ _ _ __  ___ \n" +
                "§a    | | | | / __| __| ___ \\/ _` | '_ \\/ __|\n" +
                "§a/\\__/ / |_| \\__ \\ |_| |_/ / (_| | | | \\__ \\\n" +
                "§a\\____/ \\__,_|___/\\__\\____/ \\__,_|_| |_|___/\n" +
                "                                           \n" +
                "                                           " +
                "\n");


        new Messages();
        new MainSettings();
        this.connectToMySQL();
        registerEvents();
        registerCommands();
        new BanManager();


        if(StartupStatus.messagesStatus){
            Bukkit.getConsoleSender().sendMessage("§a-Loading Messages");
        }else{
            Bukkit.getConsoleSender().sendMessage("§c-Loading Messages");
        }
        if(StartupStatus.configurationStatus){
            Bukkit.getConsoleSender().sendMessage("§a-Loading Configuration");
        }else{
            Bukkit.getConsoleSender().sendMessage("§c-Loading Configuration");
        }
        if(StartupStatus.mysqlStatus){
            Bukkit.getConsoleSender().sendMessage("§a-Connecting to MySQL");
        }else{
            Bukkit.getConsoleSender().sendMessage("§c-Connecting to MySQL");
        }
        if(StartupStatus.eventStatus){
            Bukkit.getConsoleSender().sendMessage("§a-Registering Events");
        }else{
            Bukkit.getConsoleSender().sendMessage("§c-Registering Events");
        }
        if(StartupStatus.commandStatus){
            Bukkit.getConsoleSender().sendMessage("§a-Registering Commands");
        }else{
            Bukkit.getConsoleSender().sendMessage("§c-Registering Commands");
        }
        if(StartupStatus.banloadStatus){
            Bukkit.getConsoleSender().sendMessage("§a-Loading Bans");
        }else{
            Bukkit.getConsoleSender().sendMessage("§c-Loading Bans");
        }

        if(!StartupStatus.alright()){
            System.out.println("Plugin could not be initialized. Check messages.yml, mysql.yml");
        }

        StartupStatus.startup = false;
    }

    private void registerCommands() {
        getCommand("unban").setExecutor(new Unban());
        getCommand("ban").setExecutor(new Ban());
        StartupStatus.commandStatus = true;
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new Login(), this);
        StartupStatus.eventStatus = true;
    }

    private void connectToMySQL() {
        this.saveResource("mysql.yml", false);

        File folder = new File("plugins//JustBans");
        File f = new File("plugins//JustBans//mysql.yml");

        if (!folder.exists()) folder.mkdirs();

        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
        String host = config.getString("mysql.host");
        int port = config.getInt("mysql.port");
        String database = config.getString("mysql.database");
        String user = config.getString("mysql.user");
        String password = config.getString("mysql.password");

        MySQL.setMainMySQL(new MySQL(host, port, database, user, password));
        MySQL.getMainMySQL().connect();

    }

}
