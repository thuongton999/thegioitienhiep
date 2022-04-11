package thegioitienhiep.nhatniemvinhhang.backend.manager;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.ThongtinVatpham;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham.Vatpham;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class QuanlyVatpham {
    private final JavaPlugin myPlugin;
    private final String key = "custom-item__name";
    private final Map<String, Vatpham> itemByName = new HashMap<>();
    @Nullable
    public Vatpham fromItemStack(@NotNull ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;
        // get class path from item
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(myPlugin, this.key);
        String itemName = data.get(key, PersistentDataType.STRING);
        if (itemName == null) return null;
        return getItem(itemName);
    }
    public ItemStack toItemStack(@NotNull Vatpham item) {
        ThongtinVatpham itemInfo = item.getItemInfo();
        ItemStack itemStack = new ItemStack(itemInfo.icon());
        ItemMeta meta = itemStack.getItemMeta();

        // set enchantments
        HashMap<Enchantment, Integer> enchantments = item.getEnchantments();
        for (Enchantment enchantment : enchantments.keySet()) {
            meta.addEnchant(enchantment, enchantments.get(enchantment), true);
        }
        // set item flags
        for (ItemFlag flag : itemInfo.flags()) {
            meta.addItemFlags(flag);
        }
        // store class path to item
        NamespacedKey key = new NamespacedKey(myPlugin, this.key);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(key, PersistentDataType.STRING, item.getClass().getSimpleName());

        meta.displayName(item.getName());
        meta.lore(item.getLores());
        meta.setUnbreakable(itemInfo.unbreakable());
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public ItemStack toItemStack(@NotNull String itemName) {
        Vatpham item = itemByName.get(itemName);
        if (item == null) return null;
        return toItemStack(item);
    }
    public void register(@NotNull Vatpham item) {
        itemByName.put(item.getClass().getSimpleName(), item);
    }
    public void unregister(@NotNull Vatpham item) {
        itemByName.remove(item.getClass().getSimpleName());
    }
    public List<String> getAllNames() {
        return new ArrayList<>(itemByName.keySet());
    }
    public Vatpham getItem(@NotNull String itemName) {
        return itemByName.get(itemName);
    }

    public void onDisable() {
        String saving = Main.getPluginManager().getConfig().getString("messages.data.saving");
        String saved = Main.getPluginManager().getConfig().getString("messages.data.saved");
        assert saving != null;
        assert saved != null;
        Main.getNotificationManager().console(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        saving.replace("%name%", "toàn bộ vật phẩm")));
        itemByName.clear();
        Main.getNotificationManager().console(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        saved.replace("%name%", "Toàn bộ vật phẩm")));
    }
}
