package net.lighttz.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {


    public static Main plugin;
    public static String qualquercoisa;

    @Override
    public void onEnable() {


        //plugin = this;
        //loadConfig ();
        //qualquercoisa = getConfig().getString("qualquer_coisa");






        Bukkit.getConsoleSender().sendMessage("§a[LKiller] iniciado, desenvolvido por ©ILighttz#0002 ");

        Bukkit.getPluginManager().registerEvents(new KillerCommand(), this);

        Bukkit.getPluginManager().registerEvents(new KillerEvents(), this);

        getCommand("killer").setExecutor(new KillerCommand ());
    }


    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage("§c[LKiller] Desativando, aguarde, criado por ©ILighttz#0002");

    }

    public void loadConfig(){
        File file = new File (getDataFolder (), "config.yml");
        if (!(file.exists ())){
            getConfig ().options ().copyDefaults (true);
            saveDefaultConfig ();
        }
        saveDefaultConfig ();

    }

}
