package com.bumpjammy.lifesteal;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class PlayerManager {
    static File dataFolder = LifeSteal.getPlugin(LifeSteal.class).getDataFolder();

    private static void setHealth(Player player, double hearts) {
        if(hearts == 0){
            player.kickPlayer(ChatColor.RED + "You have no hearts left!");
            return;
        }
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hearts);
        if(player.getHealth() > hearts){
            player.setHealth(hearts);
        }
    }

    public static FileConfiguration getPlayerFile(String UUID) throws IOException {
        File playerFile = new File(dataFolder, UUID + ".yml");
        if(!playerFile.exists()){
            playerFile.createNewFile();
            FileConfiguration newPlayer = YamlConfiguration.loadConfiguration(playerFile);
            newPlayer.set("hearts", 20);
            newPlayer.save(playerFile);
        }
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
        return playerData;
    }

    public static void setHearts(String PlayerUUID, int hearts) throws IOException {
        FileConfiguration playerData = getPlayerFile(PlayerUUID);
        playerData.set("hearts", hearts);
        playerData.save(new File(dataFolder, PlayerUUID + ".yml"));
        
        //Get player from UUID
        Player player = LifeSteal.getPlugin(LifeSteal.class).getServer().getPlayer(UUID.fromString(PlayerUUID));
        if(player == null){
            Bukkit.getLogger().info("Player is null");
            return;
        }
        setHealth(player, hearts);
    }

    public static int getHearts(String UUID) throws IOException {
        FileConfiguration playerData = getPlayerFile(UUID);
        return playerData.getInt("hearts");
    }

    public static void changeHearts(String UUID, int hearts) throws IOException {
        int currentHearts = getHearts(UUID);
        setHearts(UUID, currentHearts + hearts);
    }

    public static void resetHearts(String UUID) throws IOException {
        setHearts(UUID, 20);
    }

}