package dev.arctic.aicore.utils;

import dev.arctic.aicore.AiCore;
import dev.arctic.aicore.objects.AiCoreService;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

import static dev.arctic.aicore.AiCore.plugin;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0 && args[0].equalsIgnoreCase("services")) {
                if (sender.hasPermission("aicore.admin") || sender.isOp()) {
                    String names = "";
                    for (AiCoreService service : AiCore.openServices) {
                        names += service.getName() + ", ";
                    }

                    Component component = Component.text().content("[AiCore] Open Services: " + names).build();
                    sender.sendMessage(component);

                } else {
                    sender.sendMessage("You do not have permission to use this command");
                    return true;
                }
            }

        } else {
            String names = "";
            for (AiCoreService service : plugin.openServices) {
                names += service.getName() + ", ";
            }
            plugin.getLogger().log(Level.WARNING, "Open Services: " + names);
        }
        return true;
    }
}
