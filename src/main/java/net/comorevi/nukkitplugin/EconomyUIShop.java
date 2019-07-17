package net.comorevi.nukkitplugin;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.simple.Command;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class EconomyUIShop extends PluginBase {

    private SQLite3DataProvider provider;
    private Config config;
    private Config lang;

    // API //
    public boolean existsShop(int itemId, int damage) {
        return provider.existsShopData(itemId, damage);
    }

    public void addShop(int itemId, int damage, int price) {
        provider.addShopData(itemId, damage, price);
    }

    public void removeShop(int itemId, int damage) {
        provider.removeShopData(itemId, damage);
    }

    public int getItemPrice(int itemId, int damage) {
        return provider.getItemPrice(itemId, damage);
    }

    // PLUGIN //
    @Override
    public void onEnable() {
        initialize();
    }

    @Override
    public void onDisable() {
        provider.disConnectSQL();
    }

    private void initialize() {
        getDataFolder().mkdirs();
        provider = new SQLite3DataProvider(this);
        this.config = new Config(this.getDataFolder() + "config.yml", Config.YAML);
        if (config.get("lang").equals("jpn")) {
            this.lang = new Config(this.getDataFolder() + "lang-jpn.yml", Config.YAML);
        } else if (config.get("lang").equals("eng")) {
            this.lang = new Config(this.getDataFolder() + "lang-eng.yml", Config.YAML);
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.name().equals("shop")) {
            //
        }
        return true;
    }
}
