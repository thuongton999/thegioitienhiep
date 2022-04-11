package thegioitienhiep.nhatniemvinhhang.backend.manager;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import thegioitienhiep.nhatniemvinhhang.Main;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public final class QuanlyThongbao {
    private final JavaPlugin myPlugin;
    private final String consolePrefix = Main.getPluginManager().getConfig().getString("info.console.prefix");
    private final ScoreboardManager sideBarManager = Bukkit.getScoreboardManager();
    private final ScoreboardManager belowNameManager = Bukkit.getScoreboardManager();
    private final Map<String, Integer> taskIdByPlayerId = new HashMap<>();

    private Player getPlayer(String id) {
        return Bukkit.getPlayer(UUID.fromString(id));
    }
    public void console(String msg) {
        myPlugin
            .getServer()
            .getConsoleSender()
            .sendMessage(ChatColor.translateAlternateColorCodes('&', consolePrefix+msg));
    }
    public void sendTo(String playerId, String message) {
        getPlayer(playerId).sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    public void showTitle(String id, String main, String sub) {
        final Player target = getPlayer(id);
        final Title title = Title.title(
                Component.text(ChatColor.translateAlternateColorCodes('&', main)),
                Component.text(ChatColor.translateAlternateColorCodes('&', sub)));
        target.clearTitle();
        target.showTitle(title);
    }
    public void showTitleWithDurations(String id,
                                       String main,
                                       String sub,
                                       Duration fadeIn,
                                       Duration stay,
                                       Duration fadeOut) {
        final Player target = getPlayer(id);
        final Title.Times times = Title.Times.of(fadeIn, stay, fadeOut);
        final Title title = Title.title(
                Component.text(ChatColor.translateAlternateColorCodes('&', main)),
                Component.text(ChatColor.translateAlternateColorCodes('&', sub)),
                times
        );
        target.clearTitle();
        target.showTitle(title);
    }
    public void playSound(String id, Sound sound) {
        getPlayer(id).playSound(sound);
    }
    public void playSound(String id, Sound sound, double x, double y, double z) {
        getPlayer(id).playSound(sound, x, y, z);
    }
    /**
     * Play a sound that follows another emitter (usually an entity)
     * @param id The player id
     * @param sound The sound to play (see {@link Sound})
     * @param emitter The emitter to follow (Sound.Emmiter.self() to follow the player)
     */
    public void playSound(String id, Sound sound, Sound.Emitter emitter) {
        getPlayer(id).playSound(sound, emitter);
    }
    public void stopSound(String id, String soundName) {
        getPlayer(id).stopSound(soundName);
    }
    public void stopSound(String id, Sound sound) {
        getPlayer(id).stopSound(sound);
    }
    public void stopSound(String id, SoundStop sound) {
        getPlayer(id).stopSound(sound);
    }
    public void stopAllSounds(String id) {
        getPlayer(id).stopAllSounds();
    }

    public void showBoard(String id,
                          String name,
                          String criteria,
                          String displayName,
                          RenderType renderType,
                          DisplaySlot displaySlot,
                          String[] lines) {
        // *important*
        // The scoreboard text can't be longer then 16 characters (below 1.18)
        // and there can't be more than 15 scores
        // lines
        int scoreboardMaxWidth = 16;
        int scoreboardMaxHeight = 15;
        assert displayName.length() <= scoreboardMaxWidth;
        assert lines.length <= scoreboardMaxHeight;
        final Player target = getPlayer(id);
        final Scoreboard scoreboard = (displaySlot == DisplaySlot.BELOW_NAME ?
                belowNameManager.getMainScoreboard() :
                sideBarManager.getMainScoreboard());
        final Objective objective = scoreboard.registerNewObjective(
                name,
                criteria,
                ChatColor.translateAlternateColorCodes('&', displayName),
                renderType);
        objective.setDisplaySlot(displaySlot);
        for (int i = 0; i < lines.length; i++) {
            final String line = lines[lines.length - i - 1];
            // characters
            assert line.length() <= scoreboardMaxWidth;
            final Score score = objective.getScore(ChatColor.translateAlternateColorCodes('&', line));
            score.setScore(i);
        }
        target.setScoreboard(scoreboard);
    }
    public void showBoard(String id,
                          String displayName,
                          RenderType renderType,
                          DisplaySlot displaySlot,
                          String[] lines) {
        // random unique name (16 characters)
        final String uniqueName = UUID.randomUUID().toString().substring(0, 16);
        showBoard(id, uniqueName, "dummy", displayName, renderType, displaySlot, lines);
    }

    /**
     * Send a message to a player's action bar
     * @param id The player id
     * @param message The message to send
     */
    public void sendActionBar(String id, String message) {
        final Player target = getPlayer(id);
        if (target == null) return;
        target.sendActionBar(Component.text(ChatColor.translateAlternateColorCodes('&', message)));
    }

    /**
     * Display message on player's action bar forever
     * @param id The player id
     * @param message The message show on action bar
     */
    public void showActionBar(String id, String message) {
        if (taskIdByPlayerId.containsKey(id)) {
            int previousTaskId = taskIdByPlayerId.get(id);
            Main.getTaskManager().cancelTask(previousTaskId);
        }
        BukkitRunnable send = new BukkitRunnable() {
            @Override
            public void run() {
                sendActionBar(id, message);
            }
        };
        // action bar will show for 3 seconds,
        // so we need to re-send it again after 3 seconds
        Main.getTaskManager().setInterval(send, 3000);
        taskIdByPlayerId.put(id, send.getTaskId());
    }
}
