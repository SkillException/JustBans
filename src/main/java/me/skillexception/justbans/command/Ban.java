package me.skillexception.justbans.command;

import me.skillexception.justbans.configurations.Messages;
import me.skillexception.justbans.manager.BanManager;
import me.skillexception.justbans.object.BanTime;
import me.skillexception.justbans.object.BanTimeUnit;
import me.skillexception.justbans.object.BannedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Ban implements CommandExecutor {
    public boolean onCommand(CommandSender s, Command command, String l, String[] args) {
        if(s.hasPermission("justbans.ban")){
            //ban <SPIELER> <GRUND> [ZEIT]
            if(args.length == 2){
                String bannedname = "";
                if(BanManager.getMainBanManager().isBanned(args[0])){
                    for(BannedPlayer bp : BanManager.getMainBanManager().getBannedPlayers()){
                        if(bp.getName().equalsIgnoreCase(args[0]))
                            bannedname = bp.getName();
                    }
                    s.sendMessage(Messages.getMainMessages().messages.get("alreadybanned")
                            .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                            .replace("%name%", bannedname));
                }else{
                    BanManager.getMainBanManager().ban(args[0], s.getName(),args[1],new BanTime(0, BanTimeUnit.PERMANENT));
                    s.sendMessage(Messages.getMainMessages().messages.get("banned")
                            .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                            .replace("%name%", bannedname));
                }
            }else if(args.length == 3){
                if(BanManager.getMainBanManager().isBanned(args[0])){
                    String bannedname = "";
                    for(BannedPlayer bp : BanManager.getMainBanManager().getBannedPlayers()){
                        if(bp.getName().equalsIgnoreCase(args[0]))
                            bannedname = bp.getName();
                    }
                    s.sendMessage(Messages.getMainMessages().messages.get("alreadybanned")
                            .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                            .replace("%name%", bannedname));
                }else{
                    String timecode = args[2];
                    BanTimeUnit unit;
                    int num;
                    if(timecode.endsWith("s")){
                        unit = BanTimeUnit.SECOND;
                        try {
                            num = Integer.valueOf(timecode.replace("s", ""));
                        } catch (NumberFormatException e) {
                            s.sendMessage(Messages.getMainMessages().messages.get("notanumber")
                                    .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
                            return true;
                        }
                    }else if(timecode.endsWith("m")){
                        unit = BanTimeUnit.MINUTE;
                        try {
                            num = Integer.valueOf(timecode.replace("m", ""));
                        } catch (NumberFormatException e) {
                            s.sendMessage(Messages.getMainMessages().messages.get("notanumber")
                                    .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
                            return true;
                        }
                    }else if(timecode.endsWith("h")){
                        unit = BanTimeUnit.HOUR;
                        try {
                            num = Integer.valueOf(timecode.replace("h", ""));
                        } catch (NumberFormatException e) {
                            s.sendMessage(Messages.getMainMessages().messages.get("notanumber")
                                    .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
                            return true;
                        }
                    }else if(timecode.endsWith("d")){
                        unit = BanTimeUnit.DAY;
                        try {
                            num = Integer.valueOf(timecode.replace("d", ""));
                        } catch (NumberFormatException e) {
                            s.sendMessage(Messages.getMainMessages().messages.get("notanumber")
                                    .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
                            return true;
                        }
                    }else if(timecode.endsWith("w")){
                        unit = BanTimeUnit.WEEK;
                        try {
                            num = Integer.valueOf(timecode.replace("w", ""));
                        } catch (NumberFormatException e) {
                            s.sendMessage(Messages.getMainMessages().messages.get("notanumber")
                                    .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
                            return true;
                        }
                    }else if(timecode.endsWith("M")){
                        unit = BanTimeUnit.MONTH;
                        try {
                            num = Integer.valueOf(timecode.replace("M", ""));
                        } catch (NumberFormatException e) {
                            s.sendMessage(Messages.getMainMessages().messages.get("notanumber")
                                    .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
                            return true;
                        }
                    }else if(timecode.endsWith("y")){
                        unit = BanTimeUnit.YEAR;
                        try {
                            num = Integer.valueOf(timecode.replace("y", ""));
                        } catch (NumberFormatException e) {
                            s.sendMessage(Messages.getMainMessages().messages.get("notanumber")
                                    .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
                            return true;
                        }
                    }else if(timecode.equalsIgnoreCase("PERMANENT") || timecode.equalsIgnoreCase("PERMA")){
                        unit = BanTimeUnit.PERMANENT;
                        num = 0;
                    }else{
                        s.sendMessage(Messages.getMainMessages().messages.get("bansyntax")
                                .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
                        return true;
                    }

                    String realname = Bukkit.getOfflinePlayer(args[0]).getName();

                    BanManager.getMainBanManager().ban(args[0],s.getName(),args[1],new BanTime(num, unit));
                    s.sendMessage(Messages.getMainMessages().messages.get("banned")
                            .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                            .replace("%name%", realname));
                }
            }else{
                s.sendMessage(Messages.getMainMessages().messages.get("bansyntax")
                        .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
            }
        }else {
            s.sendMessage(Messages.getMainMessages().messages.get("nopermission")
                    .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
        }
        return true;
    }
}
