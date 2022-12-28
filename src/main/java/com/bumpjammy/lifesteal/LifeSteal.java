package com.bumpjammy.lifesteal;
import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public final class LifeSteal extends JavaPlugin {

    public static String prefix = ChatColor.DARK_RED + "[" + ChatColor.RED + "LifeSteal" + ChatColor.DARK_RED + "] " + ChatColor.RESET + " ";

    @Override
    public void onEnable() {
        getLogger().info("LifeSteal has been enabled!");
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        getCommand("withdrawheart").setExecutor(new WithdrawHeartCommand());
        getCommand("revive").setExecutor(new ReviveCommand());
        File dataFolder = LifeSteal.getPlugin(LifeSteal.class).getDataFolder();
        if(!dataFolder.exists()){
            dataFolder.mkdir();
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("LifeSteal has been disabled!");
    }
}
