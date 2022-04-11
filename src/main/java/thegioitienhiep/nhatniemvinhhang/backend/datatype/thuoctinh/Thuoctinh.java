package thegioitienhiep.nhatniemvinhhang.backend.datatype.thuoctinh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;

@Getter
@AllArgsConstructor
public enum Thuoctinh {
    TrucCoDanBuff(
        "&2◈&r",
        "Trúc cơ dược thủy",
        "Hội tụ linh khí trong thiên địa giúp Ngưng khí tầng 10 đột phá Trúc cơ",
        30000),
    DiaMachKhiBuff(
        "&4◈&r",
        "Địa mạch linh khí",
        "Hội tụ Địa mạch khí trong thiên địa, giúp Ngưng khí tầng 10 đột phá Địa mạch Trúc cơ",
        30000
    ),
    ThienDaoKhiBuff(
        "&b◈&r",
        "Thiên đạo linh khí",
        "Hội tụ Thiên đạo khí trong thiên địa, giúp Ngưng khí tầng 10 đột phá Thiên đạo Trúc cơ",
        30000
    );
    private final String icon;
    private final String name;
    private final String description;
    private final long millisecondsAffect;
    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', this.icon);
    }
}
