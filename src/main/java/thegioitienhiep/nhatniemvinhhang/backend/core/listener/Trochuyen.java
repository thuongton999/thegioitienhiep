package thegioitienhiep.nhatniemvinhhang.backend.core.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

public class Trochuyen implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent evt) {
        Player player = evt.getPlayer();
        String id = player.getUniqueId().toString();
        Tusi cultivator = Main.getCultivatorManager().getOnlineCultivator(id);
        String level = cultivator.getLevelInfo().name();
        evt.message(Component
                .text(ChatColor.translateAlternateColorCodes('&',String.format("&6[&r%s&6]&r: ", level)))
                .append(evt.message()));
    }
}
