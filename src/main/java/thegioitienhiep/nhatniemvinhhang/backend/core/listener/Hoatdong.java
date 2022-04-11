package thegioitienhiep.nhatniemvinhhang.backend.core.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.RenderType;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.core.event.all.ThaydoiTrangthai;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

import java.util.Objects;

public class Hoatdong implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt) {
        String id = evt.getPlayer().getUniqueId().toString();
        String name = evt.getPlayer().getName();
        Main.getCultivatorManager().active(id, name);
        // init status
        Tusi cultivator = Main.getCultivatorManager().getOnlineCultivator(id);
        Main.getPluginManager().callEvent(new ThaydoiTrangthai(cultivator));
        // log welcome message
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        String id = evt.getPlayer().getUniqueId().toString();
        Main.getCultivatorManager().deactivate(id);
    }
    @EventHandler
    public void onPlayerStatusChange(ThaydoiTrangthai evt) {
        Tusi cultivator = evt.getCultivator();
        String id = cultivator.getId();
        // sidebar scoreboard
        String name = Objects.requireNonNull(Main.getPluginManager()
                        .getConfig()
                        .getString("info.scoreboard.display_name"))
                .replace("%name%", cultivator.getName());
        String displayName = ChatColor.translateAlternateColorCodes('&', name);
        String[] lines = cultivator.shortDescription().split("\n");
        Main.getNotificationManager().showBoard(id, displayName, RenderType.INTEGER, DisplaySlot.SIDEBAR, lines);
        // below name scoreboard
        String level = cultivator.getLevelInfo().name();
        Main.getNotificationManager().showBoard(id, level, RenderType.INTEGER, DisplaySlot.BELOW_NAME, new String[]{});
        // action bar (display effects)
        Main.getNotificationManager().showActionBar(id, cultivator.getEffects().toString());
    }
}
