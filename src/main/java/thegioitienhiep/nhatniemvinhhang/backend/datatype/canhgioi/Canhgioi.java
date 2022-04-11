package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi;

import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

import java.io.Serializable;
import java.util.Objects;

public abstract class Canhgioi implements Serializable {
    public final ThongtinCanhgioi levelInfo;
    public Canhgioi() {
        levelInfo = getClass().getDeclaredAnnotation(ThongtinCanhgioi.class);
        Objects.requireNonNull(levelInfo, "Thông tin cảnh giới chưa được định nghĩa");
    }
    public abstract boolean isEligible(Tusi cultivator);
    /**
     * This method will be call when cultivator reached this level
     * @param cultivator The cultivator that reached this level
     */
    public void onReach(Tusi cultivator) {};
    /**
     * This method will be call when cultivator breakthrough(level-up) higher level
     * @param cultivator The cultivator that breakthrough higher level
     */
    public void onBreakthrough(Tusi cultivator) {};
    /**
     * This method will be call when cultivator do cultivation
     * @param cultivator The cultivator that do cultivation
     */
    public void onStartCultivate(Tusi cultivator) {};
    /**
     * This method will be call when cultivator finish cultivating
     * @param cultivator The cultivator that finish cultivating
     */
    public void onFinishCultivate(Tusi cultivator) {};
    public abstract Canhgioi getNextLevel(Tusi cultivator);
    public abstract String toString();
}
