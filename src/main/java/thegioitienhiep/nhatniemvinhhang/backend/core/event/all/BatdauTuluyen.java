package thegioitienhiep.nhatniemvinhhang.backend.core.event.all;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import thegioitienhiep.nhatniemvinhhang.backend.core.event.CustomEvent;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

@Getter
@AllArgsConstructor
public class BatdauTuluyen extends CustomEvent {
    private Tusi cultivator;
    private Location location;
}
