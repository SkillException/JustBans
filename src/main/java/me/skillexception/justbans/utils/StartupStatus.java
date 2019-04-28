package me.skillexception.justbans.utils;

public class StartupStatus {
    public static boolean startup = true;
    public static boolean messagesStatus = false;
    public static boolean configurationStatus = false;
    public static boolean mysqlStatus = false;
    public static boolean eventStatus = false;
    public static boolean commandStatus = false;
    public static boolean banloadStatus = false;

    public static boolean alright(){
        return (messagesStatus && commandStatus && mysqlStatus && eventStatus && commandStatus && banloadStatus);
    }
}
