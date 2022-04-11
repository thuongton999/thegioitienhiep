package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.soky;

import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;

@AllArgsConstructor
public enum PhanLoaiNguyenAnh {
    PhamDao("&f&l(Phàm Anh)"),
    DiaMach("&6&l(Địa Anh)"),
    ThienDao("&9&l◆Thiên Anh◆");
    final String name;
    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', name);
    }
}
