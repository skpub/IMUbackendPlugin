package org.sk_dev.iMUbackendPlugin;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class QRMapRenderer extends MapRenderer {
    private BitMatrix matrix;

    public QRMapRenderer() throws IllegalStateException, WriterException {
        super(true);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType,Object> hints = new HashMap();
        hints.put(EncodeHintType.MARGIN, 0);
        this.matrix = qrCodeWriter.encode(System.getenv().get("IMU_WEBAPP"), BarcodeFormat.QR_CODE, 128, 128, hints);
    }

    @Override
    public void render(MapView mapView, MapCanvas canvas, Player player) {
        final int width = 128;
        final int height = 128;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                canvas.setPixelColor(x, y, this.matrix.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
    }
}
