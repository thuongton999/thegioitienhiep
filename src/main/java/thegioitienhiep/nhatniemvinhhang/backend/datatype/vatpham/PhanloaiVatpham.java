package thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
@AllArgsConstructor
public enum PhanloaiVatpham {
    KhongRo("&7Không rõ"),
    VuKhi("&6Vũ khí"),
    DanDuoc("&dĐan dược"),
    ThienTaiDiaBao("&9Thiên tài Địa bảo");
    final String name;
    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', name);
    }
}
