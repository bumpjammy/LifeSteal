package com.bumpjammy.lifesteal;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ReviveCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Lets a player revive another player if they have a heart to spare
        if(!(sender instanceof Player)){
            sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "You must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;
        if(!sender.hasPermission("lifesteal.revive")){
            sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }
        if(args.length != 1){
            sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "You must specify a player to revive!");
            return true;
        }
        OfflinePlayer target = LifeSteal.getPlugin(LifeSteal.class).getServer().getOfflinePlayer(args[0]);
        if(!target.hasPlayedBefore()){
            sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "That player has never played before!");
            return true;
        }
        try{
            if(PlayerManager.getHearts(player.getUniqueId().toString()) <= 1){
                sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "You do not have enough hearts to revive!");
                return true;
            }
            if(PlayerManager.getHearts(target.getUniqueId().toString()) != 0){
                sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "That player is not dead!");
                return true;
            }
            PlayerManager.changeHearts(player.getUniqueId().toString(), -1);
            PlayerManager.changeHearts(target.getUniqueId().toString(), 1);
        } catch (Exception e){
            e.printStackTrace();
            sender.sendMessage(LifeSteal.prefix + ChatColor.RED + "Error: Could not revive player");
            return true;
        }
        sender.sendMessage(LifeSteal.prefix + ChatColor.GREEN + "You have revived " + target.getName() + "!");
        return true;
    }
    
}
