package thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.all;

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
    icon = Material.ORANGE_DYE,
    flags = {ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE},
    name = "&4&lĐịa mạch khí",
    description = {
            "Khí trong lòng đất uẩn dục mà thành, có thể giúp",
            "&eNgưng khí tầng 10 &rđột phá đến &4&lĐịa mạch &2&lTrúc cơ."},
    type = PhanloaiVatpham.ThienTaiDiaBao)
public class DiaMachKhi extends Vatpham {
    @Override
    public void onUse(PlayerInteractEvent evt) {
        if (!(evt.getAction() == Action.RIGHT_CLICK_AIR || evt.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        String id = evt.getPlayer().getUniqueId().toString();
        Tusi cultivator = Main.getCultivatorManager().getOnlineCultivator(id);
        cultivator.addEffect(Thuoctinh.DiaMachKhiBuff);
        BukkitRunnable removeBuff = new BukkitRunnable() {
            @Override
            public void run() {
                cultivator.removeEffect(Thuoctinh.DiaMachKhiBuff);
            }
        };
        Main.getTaskManager().setTimeout(removeBuff, Thuoctinh.DiaMachKhiBuff.getMillisecondsAffect());
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
        HashMap<Enchantment, Integer> enchantments = new HashMap<>();
        enchantments.put(Enchantment.KNOCKBACK, 5);
        return enchantments;
    }
}
