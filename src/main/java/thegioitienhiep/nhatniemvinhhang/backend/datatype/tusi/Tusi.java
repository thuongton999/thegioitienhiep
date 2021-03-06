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
            "???".repeat(maxLineLength),
            "&7??? &r&lID: " + this.id,
            "&7??? &rT??n: " + this.name,
            "??? &rC???nh gi???i: " + this.level.toString(),
            "???".repeat(maxLineLength),
            "&b??? EXP: &r" + this.exp + "/" + this.getLevelInfo().maxExp(),
            "&9??? Linh kh??: &r" + this.mana,
            "&c??? S???c m???nh: &r" + this.strength,
            "&e??? S??t th????ng v???t l??: &r" + this.getAttackDamage(),
            "&d?? S??t th????ng ma thu???t: &r" + this.getMagicDamage(),
            "{color}??? &7Tr???ng th??i: {color}{status}"
                    .replace("{color}", this.isCultivating ? "&a" : "&c")
                    .replace("{status}", this.isCultivating ? "??ang tu luy???n" : "Kh??ng tu luy???n"),
            "&6??? Hi???u ???ng: &r" + this.effects.toString(),
            "???".repeat(maxLineLength)
        };
        return ChatColor.translateAlternateColorCodes('&', String.join("\n&r", info));
    }
    // use for scoreboard display
    // max line length is 16 (below 1.18)
    // max lines count is 15
    public String shortDescription() {
        String[] info = {
                "???&r " + this.getLevelInfo().name(),
                "???".repeat(16),
                "&b??? EXP: &r" + this.exp + "/" + this.getLevelInfo().maxExp(),
                "&9??? LK: &r" + this.mana,
                "&c??? SM: &r" + this.strength,
                "&e??? AD: &r" + this.getAttackDamage(),
                "&d?? AP: &r" + this.getMagicDamage(),
                "{color}??? {status}"
                        .replace("{color}", this.isCultivating ? "&a" : "&c")
                        .replace("{status}", this.isCultivating ? "??ang tu luy???n" : "Kh??ng tu luy???n"),
                "=".repeat(16),
                "&8Nh???p &7&l/thongtin",
                "&8????? xem th??m..."
        };
        return ChatColor.translateAlternateColorCodes('&', String.join("\n&r", info));
    }
}
