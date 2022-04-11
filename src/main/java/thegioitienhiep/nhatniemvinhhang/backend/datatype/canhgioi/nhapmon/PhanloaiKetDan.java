package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.nhapmon;

import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;

@AllArgsConstructor
public enum PhanloaiKetDan {
    PhamDao("&f&l(Phàm Đan)"),
    DiaMach("&6&l(Địa Đan)"),
    ThienDao("&9&l◉Thiên Đan◉");
    final String name;
    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', name);
    }
}
