package tk.barrelwolf;

import com.mashape.unirest.http.Unirest;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Commit extends JavaPlugin {

    private String defaultCommitListURL = null;
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

    public void loadCommits(String url) {
        logger.info("Loading commits from \"" + url + "\"");
        try {
            logger.info("Got here!");
            String data = Unirest.get(url).getBody().toString();
            for (String line : data.split("\n")) {
                commits.add(line);
            }
            logger.info("Got here as well!");
        } catch (NullPointerException e) {
            logger.info("That's a null!");
        }
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("commit").setExecutor(new CommitExecutor(this));
        this.getCommand("addcommit").setExecutor(new AddCommitExecutor(this));
        this.getCommand("clearcommits").setExecutor(new ClearCommitsExecutor(this));

        // Add commits from the config file
        logger.info("Loading local commits...");
        commits = this.getConfig().getStringList("commits");

        // Add commits from the URL in the config file
        logger.info("Loading remote commits...");
        List<String> configFileURLs = this.getConfig().getStringList("remoteCommitURLs");

        if (configFileURLs.isEmpty()) {
            logger.info("No remote commits specified");
            return;
        }

        for (String url : configFileURLs) {
            loadCommits(url);
        }
    }

    @Override
    public void onDisable() {
        updateConfig();
        this.saveConfig();
    }

    private void updateConfig() {
        this.getConfig().set("commits", commits);
    }
}
