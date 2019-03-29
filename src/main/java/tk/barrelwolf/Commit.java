package tk.barrelwolf;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Commit extends JavaPlugin {

    private URL defaultCommitListURL;
    private Logger logger = getLogger();

    private static List<String> commits;

    public List<String> getCommits() {
        return commits;
    }

    public void addCommit(String commit) {
        commits.add(commit);
        updateConfig();
    }

    public void resetCommits() {
        commits = new ArrayList<String>();
        updateConfig();
    }

    public void loadDefaultCommits() {

    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("commit").setExecutor(new CommitExecutor(this));
        this.getCommand("addcommit").setExecutor(new AddCommitExecutor(this));
        this.getCommand("clearcommits").setExecutor(new ClearCommitsExecutor(this));

        // Add commits from the config file
        commits = this.getConfig().getStringList("commits");

        // Add commits from the URL in the config file
        String urlInConfigFile = this.getConfig().getString("defaultCommitListURL");
        logger.info("Loading commits from \"" + urlInConfigFile + "\"");
        try {
            defaultCommitListURL = new URL(urlInConfigFile);
            

        } catch (MalformedURLException e) {
            logger.warning("Error: Malformed URL \"" + urlInConfigFile + "\", ignoring...");
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void onDisable() {
        this.saveConfig();
    }

    private void updateConfig() {
        this.getConfig().set("commits", commits);
    }
}
