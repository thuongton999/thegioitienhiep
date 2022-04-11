package thegioitienhiep.nhatniemvinhhang.backend.core.command;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import thegioitienhiep.nhatniemvinhhang.Main;

import java.util.Objects;

@Getter
public class Yeucau implements CommandExecutor {
    private final ThongtinYeucau commandInfo;
    public Yeucau() {
        this.commandInfo = getClass().getDeclaredAnnotation(ThongtinYeucau.class);
        Objects.requireNonNull(commandInfo, "Thông tin yêu cầu chưa được định nghĩa");
    }
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {
        String notHavePermission = ChatColor.translateAlternateColorCodes(
                '&',
                Objects.requireNonNull(Main
                        .getPluginManager()
                        .getConfig()
                        .getString("messages.command.not_have_permission")));
        String notPlayer = ChatColor.translateAlternateColorCodes(
                '&',
                Objects.requireNonNull(Main
                        .getPluginManager()
                        .getConfig()
                        .getString("messages.command.player_only")));
        if (!commandInfo.permission().isEmpty()) {
            if (!sender.hasPermission(commandInfo.permission())) {
                sender.sendMessage(notHavePermission);
                return true;
            }
        }
        if (commandInfo.requiresPlayer()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(notPlayer);
                return true;
            }
            return execute((Player) sender, args);
        }
        return execute(sender, args);
    }

    /**
     * Executes the given command if only the sender is a player.
     * @param player    Player who executed the command
     * @param args      Passed command arguments
     */
    public boolean execute(Player player, String[] args) {return true;}

    /**
     * Executes the given command if the sender is not a player.
     * @param sender    Who executed the command
     * @param args      Passed command arguments
     */
    public boolean execute(CommandSender sender, String[] args) {return true;}

    /**
     * @return A tab completer for the given command sender
     */
    public TabCompleter getTabCompleter() {return null;}
}
