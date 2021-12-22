package com.sumo.main.YML;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocUtils {

    public static Location decode(String str){
        String[] encoded =str.split("\\:");
        Location loc = new Location(Bukkit.getServer().getWorld(encoded[0]),0,0,0);
        loc.setX(Double.parseDouble(encoded[1]));
        loc.setY(Double.parseDouble(encoded[2]));
        loc.setZ(Double.parseDouble(encoded[3]));
        loc.setPitch(Float.parseFloat(encoded[4]));
        loc.setYaw(Float.parseFloat(encoded[5]));
        return loc;
    }

    public static String encode(Location loc){
        return loc.getWorld().getName()+":"+loc.getX() +":"+loc.getY()+":"+loc.getZ()+":"+loc.getPitch()+":"+loc.getYaw();
    }


}
