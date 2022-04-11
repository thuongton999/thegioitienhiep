package thegioitienhiep.nhatniemvinhhang.backend.core.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThongtinYeucau {
    String name();
    String permission() default "";
    boolean requiresPlayer() default false;
}
