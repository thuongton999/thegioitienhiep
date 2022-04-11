package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.nhapmon;

import lombok.Getter;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.Canhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.PhanloaiCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.ThongtinCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

@ThongtinCanhgioi(
    name = "Kết Đan",
    description = "Chỉ có người đạt tới Địa mạch Trúc cơ trở lên mới có thể Kết đan. " +
            "Tu sĩ Kết đan chỉ cần giơ tay nhấc chân đều dẫn đến thiên băng địa liệt, " +
            "có được sức mạnh to lớn, vô cùng hiếm hoi tu sĩ Trúc cơ có thể địch nổi Kết đan.",
    maxExp = 800,
    minExpGain = 10,
    maxExpGain = 50,
    secondsPerCycle = 30)
@Getter
public class KetDan extends Canhgioi {
    private final int subLevel;
    private final PhanloaiKetDan type;

    public KetDan(int subLevel, PhanloaiKetDan type) {
        super();
        this.subLevel = subLevel;
        this.type = type;
    }

    @Override
    public boolean isEligible(Tusi cultivator) {
        return false;
    }

    @Override
    public Canhgioi getNextLevel(Tusi cultivator) {
        switch (type) {
            case PhamDao:
                switch (subLevel) {
                    case 1: return PhanloaiCanhgioi.PhamDaoKetDanTrungKy;
                    case 2: return PhanloaiCanhgioi.PhamDaoKetDanHauKy;
                    case 3: return PhanloaiCanhgioi.PhamDaoKetDanDaiVienMan;
                }
            case DiaMach:
                switch (subLevel) {
                    case 1: return PhanloaiCanhgioi.DiaMachKetDanTrungKy;
                    case 2: return PhanloaiCanhgioi.DiaMachKetDanHauKy;
                    case 3: return PhanloaiCanhgioi.DiaMachKetDanDaiVienMan;
                }
            case ThienDao:
                switch (subLevel) {
                    case 1: return PhanloaiCanhgioi.ThienDaoKetDanTrungKy;
                    case 2: return PhanloaiCanhgioi.ThienDaoKetDanHauKy;
                    case 3: return PhanloaiCanhgioi.ThienDaoKetDanDaiVienMan;
                }
        }
        if (PhanloaiCanhgioi.ThienDaoNguyenAnhSoKy.isEligible(cultivator))
            return PhanloaiCanhgioi.ThienDaoNguyenAnhSoKy;
        if (PhanloaiCanhgioi.DiaMachNguyenAnhSoKy.isEligible(cultivator))
            return PhanloaiCanhgioi.DiaMachNguyenAnhSoKy;
        return PhanloaiCanhgioi.PhamDaoNguyenAnhSoKy;
    }

    @Override
    public String toString() {
        switch (subLevel) {
            case 1: return levelInfo.name() + " sơ kỳ " + type.name();
            case 2: return levelInfo.name() + " trung kỳ " + type.name();
            case 3: return levelInfo.name() + " hậu kỳ " + type.name();
        }
        return levelInfo.name() + " đại viên mãn " + type.name();
    }
}
