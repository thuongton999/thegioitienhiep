package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.soky;

import lombok.Getter;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.Canhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.PhanloaiCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.ThongtinCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

@ThongtinCanhgioi(
    name = "Nguyên Anh",
    description = "Trở thành một sự tồn tại siêu việt, linh khí trong cơ thể hóa Nguyên anh. " +
            "Có thể dịch chuyển tức thời, nghịch thiên cải mệnh, sức mạnh vô cùng vô cùng to lớn.",
    maxExp = 1000,
    minExpGain = 50,
    maxExpGain = 100,
    secondsPerCycle = 90)
@Getter
public class NguyenAnh extends Canhgioi {
    private final int subLevel;
    private final PhanLoaiNguyenAnh type;
    public NguyenAnh(int subLevel, PhanLoaiNguyenAnh type) {
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
                    case 1: return PhanloaiCanhgioi.PhamDaoNguyenAnhTrungKy;
                    case 2: return PhanloaiCanhgioi.PhamDaoNguyenAnhHauKy;
                    case 3: return PhanloaiCanhgioi.PhamDaoKetDanDaiVienMan;
                }
            case DiaMach:
                switch (subLevel) {
                    case 1: return PhanloaiCanhgioi.DiaMachNguyenAnhTrungKy;
                    case 2: return PhanloaiCanhgioi.DiaMachNguyenAnhHauKy;
                    case 3: return PhanloaiCanhgioi.DiaMachKetDanDaiVienMan;
                }
            case ThienDao:
                switch (subLevel) {
                    case 1: return PhanloaiCanhgioi.ThienDaoNguyenAnhTrungKy;
                    case 2: return PhanloaiCanhgioi.ThienDaoNguyenAnhHauKy;
                    case 3: return PhanloaiCanhgioi.ThienDaoKetDanDaiVienMan;
                }
        }
        return null;
    }

    @Override
    public String toString() {
        switch (subLevel) {
            case 1: return levelInfo.name() + " sơ kỳ " + type.toString();
            case 2: return levelInfo.name() + " trung kỳ " + type.toString();
            case 3: return levelInfo.name() + " hậu kỳ " + type.toString();
        }
        return levelInfo.name() + " đại viên mãn " + type.toString();
    }
}
