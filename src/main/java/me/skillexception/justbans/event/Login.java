package me.skillexception.justbans.event;

import me.skillexception.justbans.configurations.MainSettings;
import me.skillexception.justbans.configurations.Messages;
import me.skillexception.justbans.configurations.MySQL;
import me.skillexception.justbans.manager.BanManager;
import me.skillexception.justbans.object.BannedPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import sun.plugin2.message.Message;

import java.text.SimpleDateFormat;


public class Login implements Listener {

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent e) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(MainSettings.getMainSettings().dateformat);
        MySQL.getMainMySQL().connect();
        if (!MySQL.getMainMySQL().isConnected()) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Messages.getMainMessages().messages.get("sqlshutout").replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
        }

        if(MySQL.getMainMySQL().isConnected()){
            if(BanManager.getMainBanManager() == null)
                new BanManager();
            BanManager.getMainBanManager().reloadBans();
            BannedPlayer bp = null;

            for(BannedPlayer bp1 : BanManager.getMainBanManager().getBannedPlayers()){
                if(bp1.getUUID().equals(e.getUniqueId())){
                    bp = bp1;
                }
            }

            if(bp == null){
                return;
            }

            BanManager.getMainBanManager().checkExpire(bp.getUUID());

            if(BanManager.getMainBanManager().isBanned(bp.getUUID())){
                String enddate = "";
                if(bp.isPermanent()){
                    enddate = Messages.getMainMessages().messages.get("permanent");
                }else{
                    enddate = dateFormat.format(bp.getBannedUntil());
                }

                StringBuilder disallow = new StringBuilder();
                disallow.append(Messages.getMainMessages().messages.get("firstcol")
                        .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                        .replace("%uuid%", bp.getUUID().toString())
                        .replace("%name%", bp.getName())
                        .replace("%enddate%", enddate)
                        .replace("%bannedby%", bp.getBannedby())
                        .replace("%reason%", bp.getBannedFor()));
                if(disallow.toString().contains("%bantype%")){
                    String type;
                    if(bp.isPermanent()){
                        type = Messages.getMainMessages().messages.get("permanent");
                    }else {
                        type = Messages.getMainMessages().messages.get("temporary");
                    }
                    disallow = new StringBuilder(disallow.toString().replace("%bantype%", type));
                }
                if(!(bp.isPermanent() && Messages.getMainMessages().messages.get("secondcol").contains("%enddate%"))){
                    if(!Messages.getMainMessages().messages.get("secondcol").equals("")){
                        disallow.append("\n").append(Messages.getMainMessages().messages.get("secondcol")
                                .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                                .replace("%uuid%", bp.getUUID().toString())
                                .replace("%name%", bp.getName())
                                .replace("%enddate%", enddate)
                                .replace("%bannedby%", bp.getBannedby())
                                .replace("%reason%", bp.getBannedFor()));
                        if(disallow.toString().contains("%bantype%")){
                            String type;
                            if(bp.isPermanent()){
                                type = Messages.getMainMessages().messages.get("permanent");
                            }else {
                                type = Messages.getMainMessages().messages.get("temporary");
                            }
                            disallow = new StringBuilder(disallow.toString().replace("%bantype%", type));
                        }
                    }
                }
                if(!(bp.isPermanent() && Messages.getMainMessages().messages.get("thirdcol").contains("%enddate%"))){
                    if(!Messages.getMainMessages().messages.get("thirdcol").equals("")){
                        disallow.append("\n").append(Messages.getMainMessages().messages.get("thirdcol")
                                .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                                .replace("%uuid%", bp.getUUID().toString())
                                .replace("%name%", bp.getName())
                                .replace("%enddate%", enddate)
                                .replace("%bannedby%", bp.getBannedby())
                                .replace("%reason%", bp.getBannedFor()));
                        if(disallow.toString().contains("%bantype%")){
                            String type;
                            if(bp.isPermanent()){
                                type = Messages.getMainMessages().messages.get("permanent");
                            }else {
                                type = Messages.getMainMessages().messages.get("temporary");
                            }
                            disallow = new StringBuilder(disallow.toString().replace("%bantype%", type));
                        }
                    }
                }
                if(!(bp.isPermanent() && Messages.getMainMessages().messages.get("fourthcol").contains("%enddate%"))){
                    if(!Messages.getMainMessages().messages.get("fourthcol").equals("")){
                        disallow.append("\n").append(Messages.getMainMessages().messages.get("fourthcol")
                                .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                                .replace("%uuid%", bp.getUUID().toString())
                                .replace("%name%", bp.getName())
                                .replace("%enddate%", enddate)
                                .replace("%bannedby%", bp.getBannedby())
                                .replace("%reason%", bp.getBannedFor()));
                        if(disallow.toString().contains("%bantype%")){
                            String type;
                            if(bp.isPermanent()){
                                type = Messages.getMainMessages().messages.get("permanent");
                            }else {
                                type = Messages.getMainMessages().messages.get("temporary");
                            }
                            disallow = new StringBuilder(disallow.toString().replace("%bantype%", type));
                        }
                    }
                }
                if(!(bp.isPermanent() && Messages.getMainMessages().messages.get("fifthcol").contains("%enddate%"))){
                    if(!Messages.getMainMessages().messages.get("fifthcol").equals("")){
                        disallow.append("\n").append(Messages.getMainMessages().messages.get("fifthcol")
                                .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                                .replace("%uuid%", bp.getUUID().toString())
                                .replace("%name%", bp.getName())
                                .replace("%enddate%", enddate)
                                .replace("%bannedby%", bp.getBannedby())
                                .replace("%reason%", bp.getBannedFor()));
                        if(disallow.toString().contains("%bantype%")){
                            String type;
                            if(bp.isPermanent()){
                                type = Messages.getMainMessages().messages.get("permanent");
                            }else {
                                type = Messages.getMainMessages().messages.get("temporary");
                            }
                            disallow = new StringBuilder(disallow.toString().replace("%bantype%", type));
                        }
                    }
                }
                if(!(bp.isPermanent() && Messages.getMainMessages().messages.get("sixthcol").contains("%enddate%"))){
                    if(!Messages.getMainMessages().messages.get("sixthcol").equals("")){
                        disallow.append("\n").append(Messages.getMainMessages().messages.get("sixthcol")
                                .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                                .replace("%uuid%", bp.getUUID().toString())
                                .replace("%name%", bp.getName())
                                .replace("%enddate%", enddate)
                                .replace("%bannedby%", bp.getBannedby())
                                .replace("%reason%", bp.getBannedFor()));
                        if(disallow.toString().contains("%bantype%")){
                            String type;
                            if(bp.isPermanent()){
                                type = Messages.getMainMessages().messages.get("permanent");
                            }else {
                                type = Messages.getMainMessages().messages.get("temporary");
                            }
                            disallow = new StringBuilder(disallow.toString().replace("%bantype%", type));
                        }
                    }
                }
                if(!(bp.isPermanent() && Messages.getMainMessages().messages.get("septhcol").contains("%enddate%"))){
                    if(!Messages.getMainMessages().messages.get("septhcol").equals("")){
                        disallow.append("\n").append(Messages.getMainMessages().messages.get("septhcol")
                                .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                                .replace("%uuid%", bp.getUUID().toString())
                                .replace("%name%", bp.getName())
                                .replace("%enddate%", enddate)
                                .replace("%bannedby%", bp.getBannedby())
                                .replace("%reason%", bp.getBannedFor()));
                        if(disallow.toString().contains("%bantype%")){
                            String type;
                            if(bp.isPermanent()){
                                type = Messages.getMainMessages().messages.get("permanent");
                            }else {
                                type = Messages.getMainMessages().messages.get("temporary");
                            }
                            disallow = new StringBuilder(disallow.toString().replace("%bantype%", type));
                        }
                    }
                }

                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, disallow.toString());
            }
        }





    }
}
