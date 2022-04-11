package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.nhapmon;

import lombok.Getter;
import org.bukkit.ChatColor;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.Canhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.PhanloaiCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.ThongtinCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.thuoctinh.Thuoctinh;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

import java.util.Set;

@ThongtinCanhgioi(
    name = "&2&lTrúc cơ",
    description = "Muốn thành &2&lTrúc cơ, trước hết phải &eNgưng khí mười tầng 100%.",
    maxExp = 400,
    minExpGain = 10,
    maxExpGain = 20,
    secondsPerCycle = 30
)
@Getter
public class TrucCo extends Canhgioi {
    private final int subLevel;
    private final PhanloaiTrucCo type;
    public TrucCo(int subLevel, PhanloaiTrucCo type) {
        super();
        this.subLevel = subLevel;
        this.type = type;
    }
    @Override
    public boolean isEligible(Tusi cultivator) {
        if (cultivator.getLevel().getClass() == this.getClass()) {
            return cultivator.getExp() >= cultivator.getLevelInfo().maxExp();
        }
        Set<Thuoctinh> effects = cultivator.getEffects();
        if (cultivator.getExp() < cultivator.getLevelInfo().maxExp()) return false;
        if (!effects.contains(Thuoctinh.TrucCoDanBuff)) return false;
        if (!effects.contains(Thuoctinh.DiaMachKhiBuff)) return false;
        return effects.contains(Thuoctinh.ThienDaoKhiBuff);
    }

    @Override
    public Canhgioi getNextLevel(Tusi cultivator) {
        switch (type) {
            case PhamDao:
                switch (subLevel) {
                    case 1: return PhanloaiCanhgioi.PhamDaoTrucCoTrungKy;
                    case 2: return PhanloaiCanhgioi.PhamDaoTrucCoHauKy;
                    case 3: return PhanloaiCanhgioi.PhamDaoTrucCoDaiVienMan;
                }
            case DiaMach:
                switch (subLevel) {
                    case 1: return PhanloaiCanhgioi.DiaMachTrucCoTrungKy;
                    case 2: return PhanloaiCanhgioi.DiaMachTrucCoHauKy;
                    case 3: return PhanloaiCanhgioi.DiaMachTrucCoDaiVienMan;
                }
            case ThienDao:
                switch (subLevel) {
                    case 1: return PhanloaiCanhgioi.ThienDaoTrucCoTrungKy;
                    case 2: return PhanloaiCanhgioi.ThienDaoTrucCoHauKy;
                    case 3: return PhanloaiCanhgioi.ThienDaoTrucCoDaiVienMan;
                }
        }
        if (PhanloaiCanhgioi.ThienDaoKetDanSoKy.isEligible(cultivator))
            return PhanloaiCanhgioi.ThienDaoKetDanSoKy;
        if (PhanloaiCanhgioi.DiaMachKetDanSoKy.isEligible(cultivator))
            return PhanloaiCanhgioi.DiaMachKetDanSoKy;
        return PhanloaiCanhgioi.PhamDaoKetDanSoKy;
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&',
            switch (subLevel) {
                case 1 -> levelInfo.name() + " sơ kỳ " + type.toString();
                case 2 -> levelInfo.name() + " trung kỳ " + type.toString();
                case 3 -> levelInfo.name() + " hậu kỳ " + type.toString();
                default -> levelInfo.name() + " đại viên mãn " + type.toString();
            });
    }
}
