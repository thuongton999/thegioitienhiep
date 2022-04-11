package thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThongtinVatpham {
    Material icon();
    String name();
    String[] description();
    PhanloaiVatpham type() default PhanloaiVatpham.KhongRo;
    boolean unbreakable() default true;
    ItemFlag[] flags() default {};
}
