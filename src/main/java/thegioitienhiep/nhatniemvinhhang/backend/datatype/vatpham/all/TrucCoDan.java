package thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.all;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.scheduler.BukkitRunnable;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.thuoctinh.Thuoctinh;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.PhanloaiVatpham;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.ThongtinVatpham;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.Vatpham;

import java.util.HashMap;

@ThongtinVatpham(
    icon = Material.SLIME_BALL,
    flags = {ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE},
    name = "&2&lTrúc Cơ đan",
    description = {"Dùng cho &eNgưng khí tầng 10 &rđột phá &2&lTrúc Cơ"},
    type = PhanloaiVatpham.DanDuoc)
public class TrucCoDan extends Vatpham {
    @Override
    public void onUse(PlayerInteractEvent evt) {
        if (!(evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        String id = evt.getPlayer().getUniqueId().toString();
        Tusi cultivator = Main.getCultivatorManager().getOnlineCultivator(id);
        cultivator.addEffect(Thuoctinh.TrucCoDanBuff);
        // remove effect after effect.getMilisecondsAffect() miliseconds
        BukkitRunnable removeBuff = new BukkitRunnable() {
            @Override
            public void run() {
                cultivator.removeEffect(Thuoctinh.TrucCoDanBuff);
            }
        };
        Main.getTaskManager().setTimeout(removeBuff, Thuoctinh.TrucCoDanBuff.getMillisecondsAffect());
        String used = Main.getPluginManager().getConfig().getString("messages.item.used");
        String timeRemain = Main.getPluginManager().getConfig().getString("messages.effect.time_remain");
        assert timeRemain != null;
        assert used != null;
        String seconds = String.valueOf(Thuoctinh.TrucCoDanBuff.getMillisecondsAffect() / 1000);
        Main.getNotificationManager().sendTo(id,
                used.replace("%name%", this.getItemInfo().name()) + " &r| " +
                timeRemain.replace("%time%", seconds + " giây"));
    }
    @Override
    public HashMap<Enchantment, Integer> getEnchantments() {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        enchants.put(Enchantment.VANISHING_CURSE, 1);
        return enchants;
    }
}
