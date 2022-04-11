package thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi;

import lombok.*;
import org.bukkit.ChatColor;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.core.event.all.ThaydoiTrangthai;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.Canhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.PhanloaiCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.ThongtinCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.thuoctinh.Thuoctinh;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
public class Tusi implements Serializable {
    private final String id;
    private final String name;
    private long exp = 0;
    private Canhgioi level = PhanloaiCanhgioi.PhamNhan;
    private Canhgioi previousLevel = null;
    private boolean isCultivating = false;
    private long lastCultivateTime = Instant.now().getEpochSecond();
    private Set<Thuoctinh> effects = new HashSet<>();

    // all the following stats must base on the exp
    private int mana = 0;
    private int baseMana = 0;
    private int strength = 0;
    private int baseStrength = 10;
    private int baseAttackDamage = 5;
    private int baseMagicDamage = 5;

    // the following stats are not based on exp
    private double ratioBetweenManaAndExp = 0.01;
    private double ratioBetweenStrengthAndExp = 0.01;
    private double ratioBetweenAttackDamageAndStrength = 0.01;
    private double ratioBetweenMagicDamageAndMana = 0.01;

    public int getAttackDamage() {
        return (int) (strength * ratioBetweenAttackDamageAndStrength) + baseAttackDamage;
    }
    public int getMagicDamage() {
        return (int) (mana * ratioBetweenMagicDamageAndMana) + baseMagicDamage;
    }

    private void updateStats() {
        // re-calculate all the stats
        this.mana = (int) (this.exp * this.ratioBetweenManaAndExp) + this.baseMana;
        this.strength = (int) (this.exp * this.ratioBetweenStrengthAndExp) + this.baseStrength;
        Main.getPluginManager().callEvent(new ThaydoiTrangthai(this));
    }

    public void addEffect(Thuoctinh effect) {
        this.effects.add(effect);
        Main.getPluginManager().callEvent(new ThaydoiTrangthai(this));
    }
    public void removeEffect(Thuoctinh effect) {
        this.effects.remove(effect);
        Main.getPluginManager().callEvent(new ThaydoiTrangthai(this));
    }

    public ThongtinCanhgioi getLevelInfo() {
        return this.level.levelInfo;
    }
    public void increaseExp(long exp) {
        this.exp = Math.min(this.exp + exp, this.getLevelInfo().maxExp());
        this.updateStats();
    }
    @Override
    public String toString() {
        int maxLineLength = 40;
        String[] info = {
            "", // first line will be replaced with \n
            "—".repeat(maxLineLength),
            "&7◈ &r&lID: " + this.id,
            "&7◈ &rTên: " + this.name,
            "☯ &rCảnh giới: " + this.level.toString(),
            "—".repeat(maxLineLength),
            "&b✦ EXP: &r" + this.exp + "/" + this.getLevelInfo().maxExp(),
            "&9✦ Linh khí: &r" + this.mana,
            "&c✦ Sức mạnh: &r" + this.strength,
            "&e⚔ Sát thương vật lý: &r" + this.getAttackDamage(),
            "&d۞ Sát thương ma thuật: &r" + this.getMagicDamage(),
            "{color}❂ &7Trạng thái: {color}{status}"
                    .replace("{color}", this.isCultivating ? "&a" : "&c")
                    .replace("{status}", this.isCultivating ? "Đang tu luyện" : "Không tu luyện"),
            "&6⨝ Hiệu ứng: &r" + this.effects.toString(),
            "—".repeat(maxLineLength)
        };
        return ChatColor.translateAlternateColorCodes('&', String.join("\n&r", info));
    }
    // use for scoreboard display
    // max line length is 16 (below 1.18)
    // max lines count is 15
    public String shortDescription() {
        String[] info = {
                "☯&r " + this.getLevelInfo().name(),
                "—".repeat(16),
                "&b✦ EXP: &r" + this.exp + "/" + this.getLevelInfo().maxExp(),
                "&9✦ LK: &r" + this.mana,
                "&c✦ SM: &r" + this.strength,
                "&e⚔ AD: &r" + this.getAttackDamage(),
                "&d۞ AP: &r" + this.getMagicDamage(),
                "{color}❂ {status}"
                        .replace("{color}", this.isCultivating ? "&a" : "&c")
                        .replace("{status}", this.isCultivating ? "Đang tu luyện" : "Không tu luyện"),
                "=".repeat(16),
                "&8Nhập &7&l/thongtin",
                "&8để xem thêm..."
        };
        return ChatColor.translateAlternateColorCodes('&', String.join("\n&r", info));
    }
}
