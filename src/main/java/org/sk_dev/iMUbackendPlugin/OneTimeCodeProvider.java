package org.sk_dev.iMUbackendPlugin;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import idtoken.IDTokenProvider;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.plugin.java.JavaPlugin;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.GREEN;

public class OneTimeCodeProvider implements Listener {
    private final JavaPlugin plugin;
    private ItemStack mapItem = new ItemStack(Material.FILLED_MAP, 1);
    private final UserCodeSet userCodeSet = UserCodeSet.getInstance();

    public OneTimeCodeProvider(JavaPlugin plugin) {
        this.plugin = plugin;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType,Object> hints = new HashMap();
        hints.put(EncodeHintType.MARGIN, 0);

        World world = plugin.getServer().getWorlds().getFirst();
        MapView mapView = Bukkit.createMap(world);

        MapMeta meta = (MapMeta) mapItem.getItemMeta();
        meta.setMapView(mapView);
        mapItem.setItemMeta(meta);

        mapView.getRenderers().clear();
        try {
            mapView.addRenderer(new QRMapRenderer());
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "QR Code generation err", e);
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SecureRandom random = new SecureRandom();
        String oneTimeCode = String.format("%d%d%d%d%d%d",
                random.nextInt(10),
                random.nextInt(10),
                random.nextInt(10),
                random.nextInt(10),
                random.nextInt(10),
                random.nextInt(10));

        userCodeSet.put(IDTokenProvider.UserCode.newBuilder()
                .setUuid(player.getUniqueId().toString())
                .setCode(oneTimeCode)
                .build()
        );

        String imuLink = System.getenv().get("IMU_WEBAPP");
        player.sendMessage(
                text("インモラル大学へようこそ！公式サイトにログインしましょう！\n", GREEN, TextDecoration.BOLD)
                        .append(
                                text(imuLink)
                                        .clickEvent(ClickEvent.openUrl(imuLink))
                        )
                        .append(
                                text("\n認証コード: " + oneTimeCode, AQUA, TextDecoration.BOLD)
                        )
        );
        player.getInventory().addItem(mapItem);
    }
}
