package goldenshadow.warp;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equals("warp")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    if (DataManager.doesWarpExist(args[0])) {
                        Location location = DataManager.getLocation(args[0]);
                        if (location.getWorld() != null) {
                            MVWorldManager worldManager = Warp.multiverseCore.getMVWorldManager();
                            if (worldManager.getUnloadedWorlds().contains(location.getWorld().getName())) {
                                player.sendMessage(ChatColor.RED + "[Warp] Error: The world the warp location your are tying to travel to in not loaded. Please load the world before running this command again!");
                                return true;
                            }
                            player.teleport(location);
                            player.sendMessage(ChatColor.DARK_PURPLE + "[Warp] " + ChatColor.LIGHT_PURPLE + "You have been warped to: " + args[0] + "!");
                            return true;
                        }
                    }
                    player.sendMessage(ChatColor.RED + "[Warp] Error: The location you are trying to teleport to doesn't exist!");
                    return true;
                }
                player.sendMessage(ChatColor.RED + "[Warp] Usage: /warp <name>");
                return true;
            }
            sender.sendMessage("This command must be run by a player!");
            return true;
        }
        if (label.equals("create_warp")) {
            if (sender instanceof Player player) {
                if (args.length == 1) {
                    if (!DataManager.doesWarpExist(args[0])) {
                        DataManager.addLocation(args[0], player.getLocation());
                        player.sendMessage(ChatColor.DARK_PURPLE + "[Warp] " + ChatColor.LIGHT_PURPLE + "New warp location saved as: " + args[0] + "!");
                        return true;
                    }
                    player.sendMessage(ChatColor.RED + "[Warp] Error: The name you chose already exists! Please selected a different one or delete the warp currently holding this name!");
                    return true;
                }
                player.sendMessage(ChatColor.RED + "[Warp] Usage: /create_warp <name>");
                return true;
            }
            sender.sendMessage("This command must be run by a player!");
            return true;
        }
        if (label.equals("delete_warp")) {
            if (args.length == 1) {
                if (DataManager.doesWarpExist(args[0])) {
                    DataManager.removeLocation(args[0]);
                    sender.sendMessage(ChatColor.DARK_PURPLE + "[Warp] " + ChatColor.LIGHT_PURPLE + "Warp deleted");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "[Warp] Error: The warp you are trying to delete doesn't exist!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "[Warp] Usage: /delete_warp <name>");
            return true;
        }
        if (label.equals("rename_warp")) {
            if (args.length == 2) {
                if (DataManager.doesWarpExist(args[0])) {
                    if (!DataManager.doesWarpExist(args[1])) {
                        DataManager.renameKey(args[0], args[1]);
                        sender.sendMessage(ChatColor.DARK_PURPLE + "[Warp] " + ChatColor.LIGHT_PURPLE + "Renamed " + args[0] + " to: " + args[1]);
                        return true;
                    }
                    sender.sendMessage(ChatColor.RED + "[Warp] Error: The new name you picked already exists!");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "[Warp] Error: The warp you are trying to rename doesn't exist!");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "[Warp] Usage: /rename_warp <current_name> <new_name>");
            return true;
        }
        return true;
    }
}
