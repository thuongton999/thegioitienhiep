package thegioitienhiep.nhatniemvinhhang.backend.manager;

import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.java.JavaPlugin;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.ChuyendoiDulieu;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public final class QuanlyTusi {
    private final Map<String, Tusi> cultivatorById = new HashMap<>();
    private final JavaPlugin myPlugin;
    private String getFilePath(String id) {
        String pluginFolder = myPlugin.getDataFolder().getAbsolutePath();
        String folderPath = myPlugin.getConfig().getString("data.cultivator.folder");
        String extension = myPlugin.getConfig().getString("data.cultivator.extension");
        return String.format("%s/%s.%s", pluginFolder+folderPath, id, extension);
    }
    private Tusi loadCultivatorData(String id, String name) {
        // get data from nosql database (document)
        String filePath = getFilePath(id);
        ChuyendoiDulieu<Tusi> converter = new ChuyendoiDulieu<>();
        File file = new File(filePath);
        if (!file.exists()) return new Tusi(id, name);
        // read bytes from file
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);
            byte[] bytes = dataInputStream.readAllBytes();
            // convert bytes to object
            return converter.deserialize(bytes);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Tusi(id, name);
    }
    private void saveCultivatorData(Tusi cultivator) {
        // save data to nosql database (document)
        String filePath = getFilePath(cultivator.getId());
        ChuyendoiDulieu<Tusi> converter = new ChuyendoiDulieu<>();
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                boolean createSuccess = file.getParentFile().mkdirs() && file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(converter.serialize(cultivator));
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tusi getOfflineCultivator(String id) {
        String notFound = "Not found （︶^︶）";
        Tusi cultivator = loadCultivatorData(id, notFound);
        return cultivator.getName().equals(notFound) ? null : cultivator;
    }
    public Tusi getOnlineCultivator(String id) {
        return cultivatorById.get(id);
    }
    public List<String> getAllIds() {
        return new ArrayList<>(cultivatorById.keySet());
    }

    public void active(String id, String name) {
        if (cultivatorById.containsKey(id)) return;
        Tusi cultivator = loadCultivatorData(id, name);
        cultivatorById.put(id, cultivator);
    }
    public void deactivate(String id) {
        Tusi cultivator = cultivatorById.get(id);
        if (cultivator == null) return;
        cultivator.setCultivating(false);
        saveCultivatorData(cultivator);
        cultivatorById.remove(id);
    }

    public void onDisable() {
        String saving = myPlugin.getConfig().getString("messages.data.saving");
        String saved = myPlugin.getConfig().getString("messages.data.saved");
        assert saving != null;
        assert saved != null;
        Main.getNotificationManager().console(saving.replace("%name%", "toàn bộ tu sĩ"));
        for (Tusi cultivator : cultivatorById.values()) {
            saveCultivatorData(cultivator);
            Main.getNotificationManager().console(saved.replace("%name%", cultivator.getId()));
        }
        Main.getNotificationManager().console(saved.replace("%name%", "Toàn bộ tu sĩ"));
    }
}
