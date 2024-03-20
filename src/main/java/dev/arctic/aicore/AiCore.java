package dev.arctic.aicore;

import dev.arctic.aicore.objects.AiCoreService;
import dev.arctic.aicore.utils.CommandManager;
import dev.arctic.aicore.utils.TabComplete;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class AiCore extends JavaPlugin {

    public static AiCore plugin;
    /**
     * A set of all open services, iterable for updating, viewing, and closing services
     */
    public static Set<AiCoreService> openServices;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        openServices = new HashSet<>();

        // Register the CommandManager and TabComplete classes
        Objects.requireNonNull(this.getCommand("aicore")).setExecutor(new CommandManager());
        Objects.requireNonNull(this.getCommand("aicore")).setTabCompleter(new TabComplete());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
