package com.bumpjammy.lifesteal;

import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WithdrawHeartCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Takes a heart from the player if they have one and turns it into a nether star
        if(!(sender instanceof Player)){
            sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "You must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;
        if(!sender.hasPermission("lifesteal.withdraw")){
            sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        try {
            if(PlayerManager.getHearts(player.getUniqueId().toString()) <= 1){
                sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "You do not have enough hearts to withdraw!");
                return true;
            }
            ItemStack heart = new ItemStack(Material.NETHER_STAR, 1);
            ItemMeta heartMeta = heart.getItemMeta();
            heartMeta.setDisplayName(ChatColor.GREEN + ChatColor.BOLD.toString() + "1 Heart");
            heart.setItemMeta(heartMeta);
            player.getInventory().addItem(heart);
            PlayerManager.changeHearts(player.getUniqueId().toString(), -1);
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage(LifeSteal.prefix + ChatColor.RED + "Error: Could not withdraw hearts");
        }
        return true;
    }
    
}
