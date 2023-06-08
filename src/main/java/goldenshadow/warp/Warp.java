package goldenshadow.warp;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Warp extends JavaPlugin {

    public static MultiverseCore multiverseCore;
    private static Warp plugin;

    @Override
    public void onEnable() {
        plugin = this;
        multiverseCore = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        Objects.requireNonNull(getCommand("warp")).setExecutor(new WarpCommand());
        Objects.requireNonNull(getCommand("warp")).setTabCompleter(new TabComplete());
        Objects.requireNonNull(getCommand("create_warp")).setExecutor(new WarpCommand());
        Objects.requireNonNull(getCommand("create_warp")).setTabCompleter(new TabComplete());
        Objects.requireNonNull(getCommand("delete_warp")).setExecutor(new WarpCommand());
        Objects.requireNonNull(getCommand("delete_warp")).setTabCompleter(new TabComplete());
        Objects.requireNonNull(getCommand("rename_warp")).setExecutor(new WarpCommand());
        Objects.requireNonNull(getCommand("rename_warp")).setTabCompleter(new TabComplete());
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, DataManager::loadFromFile, 1L);
    }

    @Override
    public void onDisable() {
        DataManager.saveToFile();
    }

    public static Warp getPlugin() {
        return plugin;
    }
}
