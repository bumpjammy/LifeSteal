package com.bumpjammy.lifesteal;

import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class EventManager implements Listener{
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        try {
            PlayerManager.changeHearts(player.getUniqueId().toString(), -1);
            player.sendMessage(LifeSteal.prefix + ChatColor.RED + "You have lost a heart!");
        } catch (IOException e) {
            player.sendMessage(LifeSteal.prefix + ChatColor.RED + "Error: Could not take heart");
            e.printStackTrace();
        }
        Player killer = player.getKiller();
        if(killer == null){
            return;
        }
        try {
            PlayerManager.changeHearts(killer.getUniqueId().toString(), 1);
            killer.sendMessage(LifeSteal.prefix + ChatColor.GREEN + "You have gained a heart!");
        } catch (IOException e) {
            player.sendMessage(LifeSteal.prefix + ChatColor.RED + "Error: Could not give heart");
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() == null){
            return;
        }
        if(event.getItem().getType() != Material.NETHER_STAR){
            return;
        }
        if(event.getItem().getItemMeta().getDisplayName() == null){
            return;
        }
        if(!event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + ChatColor.BOLD.toString() + "1 Heart")){
            return;
        }
        try {
            PlayerManager.changeHearts(player.getUniqueId().toString(), 1);
            player.sendMessage(LifeSteal.prefix + ChatColor.GREEN +"You have gained a heart!");
            event.getItem().setAmount(event.getItem().getAmount() - 1);
        } catch (IOException e) {
            player.sendMessage(LifeSteal.prefix + ChatColor.RED + "Error: Could not gain heart");
            e.printStackTrace();
        }
    }

    private void setHealth(Player player, double hearts) {
        if(hearts == 0){
            player.kickPlayer("You have no hearts left!");
            return;
        }
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hearts);
        if(player.getHealth() > hearts){
            player.setHealth(hearts);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        try{
            double hearts = PlayerManager.getHearts(player.getUniqueId().toString());
            setHealth(player, hearts);
        } catch (IOException e) {
            player.sendMessage(ChatColor.RED + "Error: Could not get hearts");
            e.printStackTrace();
        }
    }
    
}