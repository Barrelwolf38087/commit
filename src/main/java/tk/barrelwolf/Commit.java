package tk.barrelwolf;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

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
            logger.info("Got here!");
            String data = Unirest.get(urlInConfigFile).getBody().toString();
            for (String line : data.split("\n")) {

                commits.add(line);
            }
            logger.info("Got here as well!");
        } catch (NullPointerException e) {
            logger.info("That's a null!");
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
