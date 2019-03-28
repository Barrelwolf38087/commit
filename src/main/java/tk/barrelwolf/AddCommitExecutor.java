package tk.barrelwolf;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddCommitExecutor implements CommandExecutor {
    private final Commit plugin;

    public AddCommitExecutor(Commit plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("addcommit")) {
            if (args.length != 0) {

                String argString = String.join(" ", args);

                sender.sendMessage(argString);
                // plugin.addCommit(args[1]);
                return true;
            } else {
                sender.sendMessage("&4Error: wrong number of arguments");
                return false;
            }
        }

        return false;
    }
}
