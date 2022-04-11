package thegioitienhiep.nhatniemvinhhang.backend.manager;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public final class QuanlyCongviec {
    private final JavaPlugin myPlugin;
    public void setTimeout(BukkitRunnable runnable, long millisecond) {
        runnable.runTaskLater(myPlugin, millisecond);
    }
    public void setInterval(BukkitRunnable runnable, long millisecond) {
        runnable.runTaskTimer(myPlugin, 0, millisecond);
    }
    public void cancelTask(int taskId) {
        myPlugin.getServer().getScheduler().cancelTask(taskId);
    }
}
