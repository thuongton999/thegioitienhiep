package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThongtinCanhgioi {
    String name();
    String description();
    int maxExp();
    int minExpGain();
    int maxExpGain();
    int secondsPerCycle();
}
