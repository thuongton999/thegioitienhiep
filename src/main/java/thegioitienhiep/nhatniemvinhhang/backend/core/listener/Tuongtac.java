package thegioitienhiep.nhatniemvinhhang.backend.core.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.Vatpham;

public class Tuongtac implements Listener {
    /**
     * Called twice (once for main-hand, once for off-hand)
     * when a player interacts with an item.
     * @param evt The interact event.
     */
    @EventHandler
    public void onRightClick(PlayerInteractEvent evt) {
        String id = evt.getPlayer().getUniqueId().toString();
        ItemStack itemStack = evt.getItem();
        if (itemStack != null) {
            Vatpham item = Main.getItemManager().fromItemStack(itemStack);
            if (item != null) item.onUse(evt);
        }
    }
}
