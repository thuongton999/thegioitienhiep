package thegioitienhiep.nhatniemvinhhang.backend.core.event.all;

import lombok.AllArgsConstructor;
import lombok.Getter;
import thegioitienhiep.nhatniemvinhhang.backend.core.event.CustomEvent;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.Canhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

@Getter
@AllArgsConstructor
public class ThangcapCanhgioi extends CustomEvent {
    private final Tusi cultivator;
    private final Canhgioi level;
}
