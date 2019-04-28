package me.skillexception.justbans.manager;

import me.skillexception.justbans.configurations.MainSettings;
import me.skillexception.justbans.configurations.Messages;
import me.skillexception.justbans.configurations.MySQL;
import me.skillexception.justbans.object.BanTime;
import me.skillexception.justbans.object.BanTimeUnit;
import me.skillexception.justbans.object.BannedPlayer;
import me.skillexception.justbans.utils.StartupStatus;
import me.skillexception.justbans.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BanManager {
    private static BanManager mainBanManager;

    private ArrayList<BannedPlayer> bannedPlayers = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat(MainSettings.getMainSettings().dateformat);


    public BanManager(){
        MySQL.getMainMySQL().connect();
        if(!MySQL.getMainMySQL().isConnected()){
            return;
        }

        loadBans();
        setMainBanManager(this);
        MySQL.getMainMySQL().disconnect();
        StartupStatus.banloadStatus = true;
    }


    private void loadBans() {
        ResultSet set = MySQL.getMainMySQL().query("SELECT * FROM bannedplayers");
        try {
            while(set.next()){
                try {
                    String uuid = set.getString("uuid");
                    String name = set.getString("name");
                    String reason = set.getString("reason");
                    String enddate = set.getString("enddate");
                    String bannedby = set.getString("bannedby");

                    BannedPlayer bp;
                    if(enddate.equalsIgnoreCase("never")){
                        bp = new BannedPlayer(UUID.fromString(uuid), name, new Date(), reason, bannedby, true);
                    }
                    else bp = new BannedPlayer(UUID.fromString(uuid), name,dateFormat.parse(enddate), reason, bannedby, false);
                    bannedPlayers.add(bp);

                } catch (ParseException ignored) {}
            }
        } catch (SQLException ignored) {}
    }

    public void reloadBans(){
        MySQL.getMainMySQL().connect();
        if(MySQL.getMainMySQL().isConnected()){
            ArrayList<BannedPlayer> pl = new ArrayList<BannedPlayer>();

            ResultSet set = MySQL.getMainMySQL().query("SELECT * FROM bannedplayers");
            try {
                while(set.next()){
                    try {
                        String uuid = set.getString("uuid");
                        String name = set.getString("name");
                        String reason = set.getString("reason");
                        String enddate = set.getString("enddate");
                        String bannedby = set.getString("bannedby");

                        BannedPlayer bp;
                        if(enddate.equalsIgnoreCase("never")){
                            bp = new BannedPlayer(UUID.fromString(uuid), name, new Date(), reason, bannedby, true);
                        }
                        else bp = new BannedPlayer(UUID.fromString(uuid), name,dateFormat.parse(enddate), reason, bannedby, false);
                        pl.add(bp);

                    } catch (ParseException e) {
                        Bukkit.getConsoleSender().sendMessage("§cReloading of bans in MySQL-Database failed\n" +
                                "§cError: §e->" + e.getMessage());
                    }
                }
                bannedPlayers = pl;
                MySQL.getMainMySQL().disconnect();
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage("§cReloading of bans in MySQL-Database failed\n" +
                        "§cError: §e->" + e.getMessage());
            }
        }else{
            Bukkit.getConsoleSender().sendMessage(Messages.getMainMessages().messages.get("prefix") + Messages.getMainMessages().messages.get("sqlnotconnected"));
        }
    }

    public boolean isBanned(UUID uuid){
        for(BannedPlayer bp : bannedPlayers){
            if(bp.getUUID().equals(uuid)){
                return true;
            }
        }
        return false;
    }

    public void ban(String playername, String by, String reason, BanTime banTime){
        UUID uuid;
        String name;
        if(Bukkit.getOnlineMode()){
            uuid = UUIDFetcher.getUUID(playername);
            name = UUIDFetcher.getName(uuid);
        }else{
            uuid = Bukkit.getOfflinePlayer(playername).getUniqueId();
            name = Bukkit.getOfflinePlayer(playername).getName();
        }

        if(name == null)
            return;



        MySQL.getMainMySQL().update("INSERT INTO `ban`.`bannedplayers` (`uuid`, `name`, `bannedby`, `reason`, `enddate`) VALUES ('[uuid]', '[name]', '[bannedby]', '[reason]', '[enddate]');"
                .replace("[uuid]", uuid.toString())
                .replace("[name]", name)
                .replace("[bannedby]", by)
                .replace("[reason]", reason)
                .replace("[enddate]", getEnddate(banTime)));

        Player p = Bukkit.getPlayer(name);
        if(p != null){
            p.kickPlayer(Messages.getMainMessages().messages.get("rejoin"));
        }

        reloadBans();
    }

    public void ban(UUID uuid, String by, String reason, BanTime banTime){
        String name;
        if(Bukkit.getOnlineMode()){
            name = UUIDFetcher.getName(uuid);
        }else{
            name = Bukkit.getOfflinePlayer(uuid).getName();
        }

        if(name == null)
            return;


        MySQL.getMainMySQL().update("INSERT INTO `ban`.`bannedplayers` (`uuid`, `name`, `bannedby`, `reason`, `enddate`) VALUES ('[uuid]', '[name]', '[bannedby]', '[reason]', '[enddate]');"
                .replace("[uuid]", uuid.toString())
                .replace("[name]", name)
                .replace("[bannedby]", by)
                .replace("[reason]", reason)
                .replace("[enddate]", getEnddate(banTime)));
        Player p = Bukkit.getPlayer(name);
        if(p != null){
            p.kickPlayer(Messages.getMainMessages().messages.get("rejoin"));
        }
        reloadBans();
    }

    public String getEnddate(BanTime time){
        if(time.getUnit().equals(BanTimeUnit.PERMANENT))
            return "never";
        GregorianCalendar calendar = new GregorianCalendar();
        if(time.getUnit().equals(BanTimeUnit.SECOND))
            calendar.add(Calendar.SECOND, time.getTime());
        if(time.getUnit().equals(BanTimeUnit.MINUTE))
            calendar.add(Calendar.MINUTE, time.getTime());
        if(time.getUnit().equals(BanTimeUnit.HOUR))
            calendar.add(Calendar.HOUR_OF_DAY, time.getTime());
        if(time.getUnit().equals(BanTimeUnit.DAY))
            calendar.add(Calendar.DAY_OF_MONTH, time.getTime());
        if(time.getUnit().equals(BanTimeUnit.WEEK))
            calendar.add(Calendar.DAY_OF_MONTH, time.getTime() * 7);
        if(time.getUnit().equals(BanTimeUnit.MONTH))
            calendar.add(Calendar.MONTH, time.getTime());
        if(time.getUnit().equals(BanTimeUnit.YEAR))
            calendar.add(Calendar.YEAR, time.getTime());
        return dateFormat.format(calendar.getTime());
    }


    public boolean isBanned(String name){
        for(BannedPlayer bp : bannedPlayers){
            if(bp.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void unban(UUID uuid){
        if(isBanned(uuid)){
            MySQL.getMainMySQL().update("DELETE FROM `ban`.`bannedplayers` WHERE (`uuid` = '[uuid]');"
                    .replace("[uuid]", uuid.toString()));
            reloadBans();
        }
    }

    public void unban(String name){
        if(isBanned(name)){
            MySQL.getMainMySQL().update("DELETE FROM `ban`.`bannedplayers` WHERE (`name` = '[name]');"
                    .replace("[name]", name));
            reloadBans();
        }
    }

    public void checkExpire(UUID uuid){
        for(BannedPlayer bp : bannedPlayers){
            if(bp.getUUID().equals(uuid)){
                if(bp.isPermanent()){
                    return;
                }
                if(bp.getBannedUntil().before(new Date())){
                    unban(uuid);
                }
            }
        }
    }


    public static BanManager getMainBanManager() {
        return mainBanManager;
    }

    public static void setMainBanManager(BanManager mainBanManager) {
        BanManager.mainBanManager = mainBanManager;
    }

    public ArrayList<BannedPlayer> getBannedPlayers() {
        return bannedPlayers;
    }

}
