package me.skillexception.justbans.configurations;

import me.skillexception.justbans.JustBansMain;
import me.skillexception.justbans.utils.StartupStatus;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class Messages {
    private static Messages mainMessages;
    public HashMap<String, String> messages = new HashMap<String, String>();
    public Messages(){

        File folder = new File("plugins//JustBans");
        File f = new File(folder, "messages.yml");
        if(!folder.exists())
            folder.mkdirs();
        if(!f.exists()){
            JustBansMain.instance.saveResource("messages.yml", false);
        }


        YamlConfiguration config = YamlConfiguration.loadConfiguration(f);


        try{messages.put("prefix", config.getString("messages.prefix").replace("Â", ""));}catch(Exception e){messages.put("prefix", "");}
        try{messages.put("sqlshutout", config.getString("messages.SQLShutOut").replace("Â", ""));}catch(Exception e){messages.put("sqlshutout", "");}
        try{messages.put("nopermission", config.getString("messages.nopermission").replace("Â", ""));}catch(Exception e){messages.put("nopermission", "");}
        try{messages.put("unbansyntax", config.getString("messages.unbansyntax").replace("Â", ""));}catch(Exception e){messages.put("unbansyntax", "");}
        try{messages.put("usernotbanned", config.getString("messages.usernotbanned").replace("Â", ""));}catch(Exception e){messages.put("usernotbanned", "");}
        try{messages.put("unbanned", config.getString("messages.unbanned").replace("Â", ""));}catch(Exception e){messages.put("unbanned", "");}
        try{messages.put("bansyntax", config.getString("messages.bansyntax").replace("Â", ""));}catch(Exception e){messages.put("bansyntax", "");}
        try{messages.put("alreadybanned", config.getString("messages.alreadybanned").replace("Â", ""));}catch(Exception e){messages.put("alreadybanned", "");}
        try{messages.put("banned", config.getString("messages.banned").replace("Â", ""));}catch(Exception e){messages.put("banned", "");}
        try{messages.put("notanumber", config.getString("messages.notanumber").replace("Â", ""));}catch(Exception e){messages.put("notanumber", "");}
        try{messages.put("rejoin", config.getString("messages.banmessage.rejoin").replace("Â", ""));}catch(Exception e){messages.put("rejoin", "");}
        try{messages.put("firstcol", config.getString("messages.banmessage.firstcol").replace("Â", ""));}catch(Exception e){messages.put("firstcol", "");}
        try{messages.put("secondcol", config.getString("messages.banmessage.secondcol").replace("Â", ""));}catch(Exception e){messages.put("secondcol", "");}
        try{messages.put("thirdcol", config.getString("messages.banmessage.thirdcol").replace("Â", ""));}catch(Exception e){messages.put("thirdcol", "");}
        try{messages.put("fourthcol", config.getString("messages.banmessage.fourthcol").replace("Â", ""));}catch(Exception e){messages.put("fourthcol", "");}
        try{messages.put("fifthcol", config.getString("messages.banmessage.fifthcol").replace("Â", ""));}catch(Exception e){messages.put("fifthcol", "");}
        try{messages.put("sixthcol", config.getString("messages.banmessage.sixthcol").replace("Â", ""));}catch(Exception e){messages.put("sixthcol", "");}
        try{messages.put("septhcol", config.getString("messages.banmessage.septhcol").replace("Â", ""));}catch(Exception e){messages.put("septhcol", "");}
        try{messages.put("permanent", config.getString("messages.banmessage.permanent").replace("Â", ""));}catch(Exception e){messages.put("permanent", "");}
        try{messages.put("temporary", config.getString("messages.banmessage.temporary").replace("Â", ""));}catch(Exception e){messages.put("temporary", "");}

        mainMessages = this;
        StartupStatus.messagesStatus = true;
    }

    public static Messages getMainMessages() {
        return mainMessages;
    }
}
