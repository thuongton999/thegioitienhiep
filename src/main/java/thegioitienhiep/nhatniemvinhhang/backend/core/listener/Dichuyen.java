package thegioitienhiep.nhatniemvinhhang.backend.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.core.event.all.KetthucTuluyen;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

public class Dichuyen implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent evt) {
        String id = evt.getPlayer().getUniqueId().toString();
        Tusi cultivator = Main.getCultivatorManager().getOnlineCultivator(id);
        if (cultivator != null && cultivator.isCultivating())
            Main.getPluginManager().callEvent(new KetthucTuluyen(cultivator));
    }
}
