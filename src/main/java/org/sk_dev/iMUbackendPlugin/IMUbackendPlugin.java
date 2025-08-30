package org.sk_dev.iMUbackendPlugin;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public final class IMUbackendPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new OneTimeCodeProvider(this), this);
        try {
            Server server = ServerBuilder.forPort(50051)
                    .addService(new IdTokenProviderImpl(this))
                    .build()
                    .start();
            getLogger().info("Started IMU backend gRPC server :50051.");
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Cannot start gRPC server", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
    }
}
