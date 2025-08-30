package org.sk_dev.iMUbackendPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class IMUbackendPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new OneTimeCodeProvider(this), this);
    }

    @Override
    public void onDisable() {
    }
}
