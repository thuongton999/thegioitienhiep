package thegioitienhiep.nhatniemvinhhang.backend.manager.quanlyplugin;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.core.command.Yeucau;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.Vatpham;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@RequiredArgsConstructor
public final class QuanlyPlugin {
    private final JavaPlugin myPlugin;
    public void forAllClasses(Class<?> type, String packagePath, Runnable runnable) {
        for (Class<?> clazz : new Reflections(packagePath).getSubTypesOf(type)) {
            runnable.run(clazz);
        }
    }
    public void registerListener(Class<?> clazz) {
        assert clazz.isAssignableFrom(Listener.class);
        try {
            Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
            PluginManager pluginManager = myPlugin.getServer().getPluginManager();
            pluginManager.registerEvents(listener, myPlugin);
            String loaded = Main.getPluginManager().getConfig().getString("messages.data.loaded");
            assert loaded != null;
            Main.getNotificationManager().console(loaded.replace("%name%", clazz.getSimpleName()));
        } catch (
                InvocationTargetException |
                InstantiationException |
                IllegalAccessException |
                NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public void registerCommand(@NotNull Class<?> clazz) {
        assert clazz.isAssignableFrom(CommandExecutor.class);
        try {
            Yeucau command = (Yeucau) clazz.getDeclaredConstructor().newInstance();
            String commandName = command.getCommandInfo().name();
            PluginCommand pluginCommand = Objects.requireNonNull(myPlugin.getCommand(commandName));
            pluginCommand.setExecutor(command);
            pluginCommand.setTabCompleter(command.getTabCompleter());
            String loaded = Main.getPluginManager().getConfig().getString("messages.data.loaded");
            assert loaded != null;
            Main.getNotificationManager()
                    .console(loaded.replace("%name%", String.format("Lệnh /%s", commandName)));
        } catch (
                InvocationTargetException |
                InstantiationException |
                IllegalAccessException |
                NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public void registerItem(@NotNull Class<?> clazz) {
        assert clazz.isAssignableFrom(Vatpham.class);
        try {
            Vatpham item = (Vatpham) clazz.getDeclaredConstructor().newInstance();
            Main.getItemManager().register(item);
            String loaded = Main.getPluginManager().getConfig().getString("messages.data.loaded");
            assert loaded != null;
            Main.getNotificationManager()
                    .console(loaded.replace(
                            "%name%",
                            String.format("Vật phẩm %s", item.getClass().getSimpleName())));
        } catch (
                InvocationTargetException |
                InstantiationException |
                IllegalAccessException |
                NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    public void callEvent(Event evt) {
        myPlugin.getServer().getPluginManager().callEvent(evt);
    }
    public FileConfiguration getConfig() {
        return myPlugin.getConfig();
    }
}
