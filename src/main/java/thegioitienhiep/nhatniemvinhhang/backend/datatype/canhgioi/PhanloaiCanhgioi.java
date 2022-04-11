package thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi;

import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.nhapmon.*;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.soky.NguyenAnh;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.soky.PhanLoaiNguyenAnh;

import java.lang.reflect.Field;

public final class PhanloaiCanhgioi {
    public static final Canhgioi PhamNhan = new PhamNhan();
    public static final Canhgioi NgungKhiTang1 = new NgungKhi(1);
    public static final Canhgioi NgungKhiTang2 = new NgungKhi(2);
    public static final Canhgioi NgungKhiTang3 = new NgungKhi(3);
    public static final Canhgioi NgungKhiTang4 = new NgungKhi(4);
    public static final Canhgioi NgungKhiTang5 = new NgungKhi(5);
    public static final Canhgioi NgungKhiTang6 = new NgungKhi(6);
    public static final Canhgioi NgungKhiTang7 = new NgungKhi(7);
    public static final Canhgioi NgungKhiTang8 = new NgungKhi(8);
    public static final Canhgioi NgungKhiTang9 = new NgungKhi(9);
    public static final Canhgioi NgungKhiTang10 = new NgungKhi(10);
    public static final Canhgioi PhamDaoTrucCoSoKy = new TrucCo(1, PhanloaiTrucCo.PhamDao);
    public static final Canhgioi PhamDaoTrucCoTrungKy = new TrucCo(2, PhanloaiTrucCo.PhamDao);
    public static final Canhgioi PhamDaoTrucCoHauKy = new TrucCo(3, PhanloaiTrucCo.PhamDao);
    public static final Canhgioi PhamDaoTrucCoDaiVienMan = new TrucCo(4, PhanloaiTrucCo.PhamDao);
    public static final Canhgioi DiaMachTrucCoSoKy = new TrucCo(1, PhanloaiTrucCo.DiaMach);
    public static final Canhgioi DiaMachTrucCoTrungKy = new TrucCo(2, PhanloaiTrucCo.DiaMach);
    public static final Canhgioi DiaMachTrucCoHauKy = new TrucCo(3, PhanloaiTrucCo.DiaMach);
    public static final Canhgioi DiaMachTrucCoDaiVienMan = new TrucCo(4, PhanloaiTrucCo.DiaMach);
    public static final Canhgioi ThienDaoTrucCoSoKy = new TrucCo(1, PhanloaiTrucCo.ThienDao);
    public static final Canhgioi ThienDaoTrucCoTrungKy = new TrucCo(2, PhanloaiTrucCo.ThienDao);
    public static final Canhgioi ThienDaoTrucCoHauKy = new TrucCo(3, PhanloaiTrucCo.ThienDao);
    public static final Canhgioi ThienDaoTrucCoDaiVienMan = new TrucCo(4, PhanloaiTrucCo.ThienDao);
    public static final Canhgioi PhamDaoKetDanSoKy = new KetDan(1, PhanloaiKetDan.PhamDao);
    public static final Canhgioi PhamDaoKetDanTrungKy = new KetDan(2, PhanloaiKetDan.PhamDao);
    public static final Canhgioi PhamDaoKetDanHauKy = new KetDan(3, PhanloaiKetDan.PhamDao);
    public static final Canhgioi PhamDaoKetDanDaiVienMan = new KetDan(4, PhanloaiKetDan.PhamDao);
    public static final Canhgioi DiaMachKetDanSoKy = new KetDan(1, PhanloaiKetDan.DiaMach);
    public static final Canhgioi DiaMachKetDanTrungKy = new KetDan(2, PhanloaiKetDan.DiaMach);
    public static final Canhgioi DiaMachKetDanHauKy = new KetDan(3, PhanloaiKetDan.DiaMach);
    public static final Canhgioi DiaMachKetDanDaiVienMan = new KetDan(4, PhanloaiKetDan.DiaMach);
    public static final Canhgioi ThienDaoKetDanSoKy = new KetDan(1, PhanloaiKetDan.ThienDao);
    public static final Canhgioi ThienDaoKetDanTrungKy = new KetDan(2, PhanloaiKetDan.ThienDao);
    public static final Canhgioi ThienDaoKetDanHauKy = new KetDan(3, PhanloaiKetDan.ThienDao);
    public static final Canhgioi ThienDaoKetDanDaiVienMan = new KetDan(4, PhanloaiKetDan.ThienDao);
    public static final Canhgioi PhamDaoNguyenAnhSoKy = new NguyenAnh(1, PhanLoaiNguyenAnh.PhamDao);
    public static final Canhgioi PhamDaoNguyenAnhTrungKy = new NguyenAnh(2, PhanLoaiNguyenAnh.PhamDao);
    public static final Canhgioi PhamDaoNguyenAnhHauKy = new NguyenAnh(3, PhanLoaiNguyenAnh.PhamDao);
    public static final Canhgioi PhamDaoNguyenAnhDaiVienMan = new NguyenAnh(4, PhanLoaiNguyenAnh.PhamDao);
    public static final Canhgioi DiaMachNguyenAnhSoKy = new NguyenAnh(1, PhanLoaiNguyenAnh.DiaMach);
    public static final Canhgioi DiaMachNguyenAnhTrungKy = new NguyenAnh(2, PhanLoaiNguyenAnh.DiaMach);
    public static final Canhgioi DiaMachNguyenAnhHauKy = new NguyenAnh(3, PhanLoaiNguyenAnh.DiaMach);
    public static final Canhgioi DiaMachNguyenAnhDaiVienMan = new NguyenAnh(4, PhanLoaiNguyenAnh.DiaMach);
    public static final Canhgioi ThienDaoNguyenAnhSoKy = new NguyenAnh(1, PhanLoaiNguyenAnh.ThienDao);
    public static final Canhgioi ThienDaoNguyenAnhTrungKy = new NguyenAnh(2, PhanLoaiNguyenAnh.ThienDao);
    public static final Canhgioi ThienDaoNguyenAnhHauKy = new NguyenAnh(3, PhanLoaiNguyenAnh.ThienDao);
    public static final Canhgioi ThienDaoNguyenAnhDaiVienMan = new NguyenAnh(4, PhanLoaiNguyenAnh.ThienDao);
}
