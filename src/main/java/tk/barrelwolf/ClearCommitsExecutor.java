package tk.barrelwolf;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;
import java.util.logging.Level;

public class ClearCommitsExecutor implements CommandExecutor {
    private Commit plugin;
    private Logger logger;

    public ClearCommitsExecutor(Commit plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("clearcommits")) {
            plugin.resetCommits();
            logger.log(Level.INFO, "Commits cleared by " + sender.getName());
            sender.sendMessage("Commits cleared!");
            return true;
        }

        return false;
    }
}
