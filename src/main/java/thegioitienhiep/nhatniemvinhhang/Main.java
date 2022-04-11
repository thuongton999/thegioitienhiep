package thegioitienhiep.nhatniemvinhhang;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import thegioitienhiep.nhatniemvinhhang.backend.core.command.Yeucau;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.Vatpham;
import thegioitienhiep.nhatniemvinhhang.backend.manager.QuanlyCongviec;
import thegioitienhiep.nhatniemvinhhang.backend.manager.QuanlyThongbao;
import thegioitienhiep.nhatniemvinhhang.backend.manager.QuanlyTusi;
import thegioitienhiep.nhatniemvinhhang.backend.manager.QuanlyVatpham;
import thegioitienhiep.nhatniemvinhhang.backend.manager.quanlyplugin.QuanlyPlugin;

public final class Main extends JavaPlugin {
    @Getter
    private static QuanlyPlugin pluginManager;
    @Getter
    private static QuanlyThongbao notificationManager;
    @Getter
    private static QuanlyCongviec taskManager;
    @Getter
    private static QuanlyTusi cultivatorManager;
    @Getter
    private static QuanlyVatpham itemManager;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.saveDefaultConfig();
        pluginManager = new QuanlyPlugin(this);
        notificationManager = new QuanlyThongbao(this);
        taskManager = new QuanlyCongviec(this);
        cultivatorManager = new QuanlyTusi(this);
        itemManager = new QuanlyVatpham(this);
        String packageName = getClass().getPackage().getName();
        // register all listeners
        pluginManager.forAllClasses(
                Listener.class,
                packageName+".backend.core.listener",
                pluginManager::registerListener);
        // register all commands
        pluginManager.forAllClasses(
                Yeucau.class,
                packageName+".backend.core.command.all",
                pluginManager::registerCommand);
        // register all items
        pluginManager.forAllClasses(
                Vatpham.class,
                packageName+".backend.datatype.vatpham.all",
                pluginManager::registerItem);
        notificationManager.console("&a~ Plugin is enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        cultivatorManager.onDisable();
        itemManager.onDisable();
        notificationManager.console("&c~ Plugin is disabled");
    }
}
