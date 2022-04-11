package thegioitienhiep.nhatniemvinhhang.backend.core.command.all;

import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.core.command.ThongtinYeucau;
import thegioitienhiep.nhatniemvinhhang.backend.core.command.Yeucau;
import thegioitienhiep.nhatniemvinhhang.backend.core.tabcompleter.HoanthienThamsoVatpham;

@ThongtinYeucau(name = "vatpham", requiresPlayer = true)
public class YeucauVatpham extends Yeucau {
    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length == 0) return false;
        String name = args[0];
        if (name == null || name.isEmpty() || name.isBlank()) return false;
        int amount = args.length > 1 ? Integer.parseInt(args[1]) : 1;
        ItemStack item = Main.getItemManager().toItemStack(name);
        item.setAmount(amount);
        player.getInventory().addItem(item);
        return true;
    }
    @Override
    public TabCompleter getTabCompleter() {
        return new HoanthienThamsoVatpham();
    }
}
