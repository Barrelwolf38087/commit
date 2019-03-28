package tk.barrelwolf;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Random;

public class CommitExecutor implements CommandExecutor {
    private final Commit plugin;
    private final Random rand = new Random();

    public CommitExecutor(Commit plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("commit")) {
            if (plugin.getCommits().size() == 0) {
                sender.sendMessage("Delete META-INF");
                return true;
            }
            sender.sendMessage(plugin.getCommits().get(rand.nextInt(plugin.getCommits().size())));
            return true;
        }

        return false;
    }
}

