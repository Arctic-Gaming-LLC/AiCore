package dev.arctic.aicore;

import dev.arctic.aicore.objects.AiCoreService;
import dev.arctic.aicore.utils.CommandManager;
import dev.arctic.aicore.utils.TabComplete;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class AiCore extends JavaPlugin {

    public static AiCore AICORE_PLUGIN;
    /**
     * A List of all open services, iterable for updating, viewing, and closing services
     */
    public static List<AiCoreService> openServices = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        AICORE_PLUGIN = this;

        // Register the CommandManager and TabComplete classes
        Objects.requireNonNull(this.getCommand("aicore")).setExecutor(new CommandManager());
        Objects.requireNonNull(this.getCommand("aicore")).setTabCompleter(new TabComplete());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
