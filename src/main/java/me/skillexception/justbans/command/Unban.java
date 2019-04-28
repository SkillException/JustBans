package me.skillexception.justbans.command;

import me.skillexception.justbans.configurations.Messages;
import me.skillexception.justbans.manager.BanManager;
import me.skillexception.justbans.object.BannedPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Unban implements CommandExecutor {
    public boolean onCommand(CommandSender s, Command command, String k, String[] args) {
        if(s.hasPermission("justbans.unban")){
            if(args.length > 0){
                String name = args[0];
                if(BanManager.getMainBanManager().isBanned(name)){
                    String realname = "";
                    for(BannedPlayer bp : BanManager.getMainBanManager().getBannedPlayers()){
                        if(bp.getName().equalsIgnoreCase(name)){
                            realname = bp.getName();
                        }
                    }
                    BanManager.getMainBanManager().unban(name);
                    s.sendMessage(Messages.getMainMessages().messages.get("unbanned")
                            .replace("%prefix%", Messages.getMainMessages().messages.get("prefix"))
                            .replace("%name%", realname));
                }else{
                    s.sendMessage(Messages.getMainMessages().messages.get("usernotbanned")
                        .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
                }
            }else{
                s.sendMessage(Messages.getMainMessages().messages.get("unbansyntax")
                        .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
            }
        }else{
            s.sendMessage(Messages.getMainMessages().messages.get("nopermission")
                    .replace("%prefix%", Messages.getMainMessages().messages.get("prefix")));
        }
        return true;
    }
}
