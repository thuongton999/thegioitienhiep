package thegioitienhiep.nhatniemvinhhang.backend.core.command.all;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.core.command.ThongtinYeucau;
import thegioitienhiep.nhatniemvinhhang.backend.core.command.Yeucau;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

import java.util.Objects;

@ThongtinYeucau(name = "thongtin")
public class YeucauThongtin extends Yeucau {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 1) return false;
        if (args.length == 1) {
            String targetId = args[0];
            Tusi onlineCultivator = Main.getCultivatorManager().getOnlineCultivator(targetId);
            Tusi offlineCultivator = Main.getCultivatorManager().getOfflineCultivator(targetId);
            if (onlineCultivator != null) {
                sender.sendMessage(onlineCultivator.toString());
            } else if (offlineCultivator != null) {
                sender.sendMessage(offlineCultivator.toString());
            } else {
                String notFound = Objects.requireNonNull(Main
                                .getPluginManager()
                                .getConfig()
                                .getString("messages.command.not_found"))
                        .replace("%name%", targetId);
                if (!(sender instanceof Player)) {
                    Main.getNotificationManager().console(notFound);
                } else {
                    Player player = (Player) sender;
                    String id = player.getUniqueId().toString();
                    Main.getNotificationManager().sendTo(id, notFound);
                }
            }
            return true;
        }
        String playerOnly = Main.getPluginManager()
                .getConfig()
                .getString("messages.command.player_only");
        if (!(sender instanceof Player)) {
            assert playerOnly != null;
            Main.getNotificationManager().console(playerOnly);
            return true;
        }
        Player player = (Player) sender;
        String id = player.getUniqueId().toString();
        Tusi selfCultivator = Main.getCultivatorManager().getOnlineCultivator(id);
        player.sendMessage(selfCultivator.toString());
        return true;
    }
}
