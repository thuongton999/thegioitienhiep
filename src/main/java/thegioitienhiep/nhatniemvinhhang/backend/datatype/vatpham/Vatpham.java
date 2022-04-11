package thegioitienhiep.nhatniemvinhhang.backend.datatype.vatpham;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import thegioitienhiep.nhatniemvinhhang.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class Vatpham {
    private final ThongtinVatpham itemInfo;
    public Vatpham() {
        this.itemInfo = getClass().getDeclaredAnnotation(ThongtinVatpham.class);
        Objects.requireNonNull(this.itemInfo, "Thông tin vật phẩm chưa được định nghĩa");
    }
    public Component getName() {
        String name = ChatColor.translateAlternateColorCodes('&', itemInfo.name());
        return Component.text(name);
    }
    public List<Component> getLores() {
        String prefix = Main.getPluginManager().getConfig().getString("info.item.lore_prefix");
        List<Component> lores = new ArrayList<>();
        String[] description = itemInfo.description();
        lores.add(Component.text(ChatColor.translateAlternateColorCodes(
                '&',
                prefix + "Phân loại: " + itemInfo.type().toString())));
        lores.add(Component.text(ChatColor.translateAlternateColorCodes(
                '&',
                prefix + description[0])));
        for (int i = 1; i < description.length; i++) {
            lores.add(Component.text(ChatColor.translateAlternateColorCodes('&', description[i])));
        }
        return lores;
    }
    /**
     * @return A map of enchantments and their levels
     */
    public HashMap<Enchantment, Integer> getEnchantments() {
        return new HashMap<>();
    }
    /**
     * Called when the player right-clicks with this item in hand
     * @param evt The right-click event
     */
    public abstract void onUse(PlayerInteractEvent evt);
}