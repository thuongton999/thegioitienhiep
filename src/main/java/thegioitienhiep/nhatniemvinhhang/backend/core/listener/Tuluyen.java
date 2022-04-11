package thegioitienhiep.nhatniemvinhhang.backend.core.listener;

import dev.geco.gsit.api.GSitAPI;
import dev.geco.gsit.objects.GetUpReason;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.intellij.lang.annotations.Subst;
import thegioitienhiep.nhatniemvinhhang.Main;
import thegioitienhiep.nhatniemvinhhang.backend.core.event.all.*;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.Canhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.canhgioi.ThongtinCanhgioi;
import thegioitienhiep.nhatniemvinhhang.backend.datatype.tusi.Tusi;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Tuluyen implements Listener {
    @EventHandler
    public void onStartCultivating(BatdauTuluyen evt) {
        Tusi cultivator = evt.getCultivator();
        if (cultivator.isCultivating()) {
            Main.getPluginManager().callEvent(new KetthucTuluyen(cultivator));
            return;
        }
        cultivator.getLevel().onStartCultivate(cultivator);
        cultivator.setCultivating(true);
        cultivator.setLastCultivateTime(Instant.now().getEpochSecond());
        Main.getPluginManager().callEvent(new ThaydoiTrangthai(cultivator));
        // send title
        String message = Main.getPluginManager().getConfig().getString("messages.cultivation.start");
        Main.getNotificationManager().showTitle(cultivator.getId(), message, "");
        // sit animation
        Location location = evt.getLocation();
        Block block = location.getBlock();
        Block sitBlock = block.isPassable() ? location.subtract(0, 0.0625, 0).getBlock() : block;
        Player player = Bukkit.getPlayer(UUID.fromString(cultivator.getId()));
        GSitAPI.createSeat(sitBlock, player);
    }

    @EventHandler
    public void onFinishCultivating(KetthucTuluyen evt) {
        Tusi cultivator = evt.getCultivator();
        FileConfiguration config = Main.getPluginManager().getConfig();
        // get difference between last cultivation time and current time
        long lastCultivationTime = cultivator.getLastCultivateTime();
        long currentTime = Instant.now().getEpochSecond();
        long duration = currentTime - lastCultivationTime;
        // calculate the experience gained based on the spentTime
        // much experience as much spent time
        ThongtinCanhgioi levelInfo = cultivator.getLevelInfo();
        long totalCycles = duration / levelInfo.secondsPerCycle();
        long maxExperience = totalCycles * levelInfo.maxExpGain();
        long minExperience = totalCycles * levelInfo.minExpGain();
        long expGain = (long) (Math.random() * (maxExperience - minExperience) + minExperience);
        cultivator.increaseExp(expGain);
        // cancel sit animation
        Player player = Bukkit.getPlayer(UUID.fromString(cultivator.getId()));
        GSitAPI.stopPlayerSit(player, GetUpReason.GET_UP);
        // reset the last cultivation time
        cultivator.getLevel().onFinishCultivate(cultivator);
        cultivator.setCultivating(false);
        cultivator.setLastCultivateTime(currentTime);
        Main.getPluginManager().callEvent(new ThaydoiTrangthai(cultivator));
        // play sound
        @Subst("entity.experience_orb.pick_up")
        String soundName = config.getString("sounds.cultivation.finish.name");
        float soundVolume = (float) config.getDouble("sounds.cultivation.finish.volume");
        float soundPitch = (float) config.getDouble("sounds.cultivation.finish.pitch");
        assert soundName != null;
        Sound expSound = Sound.sound(Key.key(soundName), Sound.Source.AMBIENT, soundVolume, soundPitch);
        // send title
        String mainTitle = config.getString("messages.cultivation.finish");
        String subTitle = Objects.requireNonNull(Main.getPluginManager()
                .getConfig()
                .getString("messages.cultivation.gain_exp"))
                .replace("%number%", String.valueOf(expGain));
        Main.getNotificationManager().showTitle(cultivator.getId(), mainTitle, subTitle);
        Main.getNotificationManager().playSound(cultivator.getId(), expSound);
        // check if the level is up
        if (cultivator.getExp() < cultivator.getLevelInfo().maxExp()) return;
        Canhgioi nextLevel = cultivator.getLevel().getNextLevel(cultivator);
        if (nextLevel != null && nextLevel.isEligible(cultivator))
            Main.getPluginManager().callEvent(new ThangcapCanhgioi(cultivator, nextLevel));
        else Main.getPluginManager().callEvent(new GapBinhcanh(cultivator));
    }

    @EventHandler
    public void onObstacle(GapBinhcanh evt) {
        String id = evt.getCultivator().getId();
        FileConfiguration config = Main.getPluginManager().getConfig();
        // play sound
        @Subst("block.bubble_column.whirlpool_inside")
        String soundName = config.getString("sounds.cultivation.obstacle.name");
        float soundVolume = (float) config.getDouble("sounds.cultivation.obstacle.volume");
        float soundPitch = (float) config.getDouble("sounds.cultivation.obstacle.pitch");
        assert soundName != null;
        Sound obstacleSound = Sound.sound(Key.key(soundName), Sound.Source.AMBIENT, soundVolume, soundPitch);
        Main.getNotificationManager().playSound(id, obstacleSound);
        // send title
        String mainTitle = config.getString("messages.cultivation.obstacle.main");
        String subTitle = config.getString("messages.cultivation.obstacle.sub");
        Main.getNotificationManager().showTitle(id, mainTitle, subTitle);
    }

    @EventHandler
    public void onBreakThrough(ThangcapCanhgioi evt) {
        Tusi cultivator = evt.getCultivator();
        // call break through event with player old level
        cultivator.getLevel().onBreakthrough(cultivator);
        // set new level
        cultivator.setLevel(evt.getLevel());
        // call reach level event with player new level
        cultivator.getLevel().onReach(cultivator);
        // play sound
        FileConfiguration config = Main.getPluginManager().getConfig();
        @Subst("block.anvil.use")
        String soundName = config.getString("sounds.cultivation.breakthrough.name");
        float soundVolume = (float) config.getDouble("sounds.cultivation.breakthrough.volume");
        float soundPitch = (float) config.getDouble("sounds.cultivation.breakthrough.pitch");
        assert soundName != null;
        Sound breakthroughSound = Sound.sound(Key.key(soundName), Sound.Source.AMBIENT, soundVolume, soundPitch);
        Main.getNotificationManager().playSound(cultivator.getId(), breakthroughSound);
        // send title
        String message = Main.getPluginManager().getConfig().getString("messages.cultivation.breakthrough");
        Main.getNotificationManager().showTitle(cultivator.getId(), message, "");
    }
}
