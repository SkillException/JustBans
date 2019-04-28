package me.skillexception.justbans.configurations;

import me.skillexception.justbans.object.BannedPlayer;
import me.skillexception.justbans.utils.StartupStatus;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.HashMap;

public class MySQL {

    private static MySQL mainMySQL;
    public HashMap<String, BannedPlayer> bannedPlayers = new HashMap<String, BannedPlayer>();
    private Connection con;
    private String host, database, user, password;
    private int port;
    private Statement statement;

    public MySQL(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public static MySQL getMainMySQL() {
        return mainMySQL;
    }

    public static void setMainMySQL(MySQL mainMySQL) {
        MySQL.mainMySQL = mainMySQL;
    }

    public void connect() {
        if(!isConnected()){
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
                statement = con.createStatement();
                update("CREATE TABLE IF NOT EXISTS `ban`.`bannedplayers` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `uuid` VARCHAR(45) NULL,\n" +
                        "  `name` VARCHAR(45) NULL,\n" +
                        "  `bannedby` VARCHAR(45) NULL,\n" +
                        "  `reason` VARCHAR(45) NULL,\n" +
                        "  `enddate` VARCHAR(45) NULL,\n" +
                        "  PRIMARY KEY (`id`));\n");


                StartupStatus.mysqlStatus = true;
            } catch (SQLException e) {
                if(!StartupStatus.startup){
                    Bukkit.getConsoleSender().sendMessage("§cConnection to MySQL-Server failed. Please check your mysql.yml\n" +
                            "§cError: §e->" + e.getMessage());
                }
            }
        }
    }

    public ResultSet query(String query) {
        connect();
        try {
            statement = con.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cError while querying MySQL (§4" + e.getMessage() + "): \n§e-> " + query + "\n§cPlease report this bug on the official Projectpage: §ebit.ly/justbans");
        }
        return null;
    }

    public int update(String update) {
        connect();
        try {
            statement = con.createStatement();
            return statement.executeUpdate(update);
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cError while updating MySQL (§4" + e.getMessage() + "): \n§e-> " + update + "\n§cPlease report this bug on the official Projectpage: §ebit.ly/justbans");
        }
        return 0;
    }

    public void disconnect() {

        try {
            if (isConnected()) {
                con.close();
                con = null;
            }
            statement.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cDisconnecting to MySQL-Server failed. Please check your mysql.yml\n" +
                    "§cError: §e->" + e.getMessage());
        }

    }

    public boolean isConnected() {

        try {

            if (con != null && !con.isClosed()) {
                return true;
            }

        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage("§cChecking if MySQL is connected failed. Probably its not lol\n" +
                    "§cError: §e->" + e.getMessage());
        }

        return false;

    }
}
