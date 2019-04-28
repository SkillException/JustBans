package me.skillexception.justbans.object;


import java.util.Date;
import java.util.UUID;

public class BannedPlayer {
    private final UUID uuid;
    private final String name;
    private Date bannedUntil;
    private String bannedFor;
    private String bannedby;
    private boolean permanent;

    public BannedPlayer(UUID uuid, String name, Date bannedUntil, String bannedFor, String bannedby, boolean permanent) {
        this.uuid = uuid;
        this.name = name;
        this.bannedUntil = bannedUntil;
        this.bannedFor = bannedFor;
        this.bannedby = bannedby;
        this.permanent = permanent;
    }

    public String getName(){
        return name;
    }

    public UUID getUUID(){
        return uuid;
    }

    public Date getBannedUntil() {
        return bannedUntil;
    }

    public String getBannedFor() {
        return bannedFor;
    }

    public String getBannedby() {
        return bannedby;
    }

    public boolean isPermanent() {
        return permanent;
    }

}
