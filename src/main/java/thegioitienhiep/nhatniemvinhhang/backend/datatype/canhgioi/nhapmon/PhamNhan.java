package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.nhapmon;

import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.Canhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.PhanloaiCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.ThongtinCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

@ThongtinCanhgioi(
    name = "Phàm nhân",
    description = "Người thường, yếu đuối vô dụng",
    maxExp = 100,
    minExpGain = 1,
    maxExpGain = 5,
    secondsPerCycle = 60
)
public class PhamNhan extends Canhgioi {
    @Override
    public boolean isEligible(Tusi cultivator) {
        return true;
    }

    @Override
    public Canhgioi getNextLevel(Tusi cultivator) {
        return PhanloaiCanhgioi.NgungKhiTang1;
    }

    @Override
    public String toString() {
        return levelInfo.name();
    }
}
