package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.nhapmon;

import lombok.Getter;
import org.bukkit.ChatColor;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.Canhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.PhanloaiCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.ThongtinCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

@ThongtinCanhgioi(
    name = "&eNgưng khí",
    description = "Phân thành mười tầng, tầng một thấp nhất, tầng mười cao nhất",
    maxExp = 200,
    minExpGain = 5,
    maxExpGain = 20,
    secondsPerCycle = 90)
@Getter
public class NgungKhi extends Canhgioi {
    private final int subLevel;
    public NgungKhi(int level) {
        super();
        this.subLevel = level;
    }
    @Override
    public boolean isEligible(Tusi cultivator) {
        return cultivator.getExp() >= this.levelInfo.maxExp();
    }

    @Override
    public Canhgioi getNextLevel(Tusi cultivator) {
        switch (subLevel) {
            case 1: return PhanloaiCanhgioi.NgungKhiTang2;
            case 2: return PhanloaiCanhgioi.NgungKhiTang3;
            case 3: return PhanloaiCanhgioi.NgungKhiTang4;
            case 4: return PhanloaiCanhgioi.NgungKhiTang5;
            case 5: return PhanloaiCanhgioi.NgungKhiTang6;
            case 6: return PhanloaiCanhgioi.NgungKhiTang7;
            case 7: return PhanloaiCanhgioi.NgungKhiTang8;
            case 8: return PhanloaiCanhgioi.NgungKhiTang9;
            case 9: return PhanloaiCanhgioi.NgungKhiTang10;
        }
        if (PhanloaiCanhgioi.ThienDaoTrucCoSoKy.isEligible(cultivator))
            return PhanloaiCanhgioi.ThienDaoTrucCoSoKy;
        if (PhanloaiCanhgioi.DiaMachTrucCoSoKy.isEligible(cultivator))
            return PhanloaiCanhgioi.DiaMachTrucCoSoKy;
        return PhanloaiCanhgioi.PhamDaoTrucCoSoKy;
    }
    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&',
                String.format("%s Tầng &l&8%d",
                        this.levelInfo.name(),
                        subLevel));
    }
}
