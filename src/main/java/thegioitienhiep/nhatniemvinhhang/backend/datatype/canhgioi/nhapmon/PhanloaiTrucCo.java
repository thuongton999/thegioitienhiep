package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.nhapmon;

import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;

@AllArgsConstructor
public enum PhanloaiTrucCo {
    PhamDao("&f&l(Phàm Đạo)"),
    DiaMach("&6&l(Địa Mạch)"),
    ThienDao("&9&l◈Thiên Đạo◈");
    final String name;
    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', name);
    }
}
