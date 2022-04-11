package thegioitienhiep.nhatniemvinhhang.backend.core.command.all;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.core.command.ThongtinYeucau;
import thegioitienhiep.nhatniemvinhhang.backend.core.command.Yeucau;
import thegioitienhiep.nhatniemvinhhang.backend.core.event.all.BatdauTuluyen;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

@ThongtinYeucau(name = "tuluyen", requiresPlayer = true)
public class YeucauTuluyen extends Yeucau {
    @Override
    public boolean execute(Player player, String[] args) {
        String id = player.getUniqueId().toString();
        Tusi cultivator = Main.getCultivatorManager().getOnlineCultivator(id);
        Location location = player.getLocation();
        Main.getPluginManager().callEvent(new BatdauTuluyen(cultivator, location));
        return true;
    }
}
