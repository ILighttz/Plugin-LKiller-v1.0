package net.lighttz.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static net.lighttz.plugin.KillerCommand.*;

public class KillerMensagens {

    public static void killerinciar() {
        Bukkit.broadcastMessage ("§c-");
        Bukkit.broadcastMessage (" §c§lEvento Killer Iniciando");
        Bukkit.broadcastMessage ("");
        Bukkit.broadcastMessage (" §cPremiação: §e" + premiação);

        Bukkit.broadcastMessage (" §f" + j + "§c jogadores participando");
        Bukkit.broadcastMessage (" §cIrá iniciar em §f" + tempo + " §csegundos");
        Bukkit.broadcastMessage ("");
        Bukkit.broadcastMessage (" §cPara participar: §f/killer entrar ");
        Bukkit.broadcastMessage ("§c-");
        for (Player all : Bukkit.getOnlinePlayers()){
            all.getWorld().playSound(all.getLocation(), Sound.ITEM_PICKUP, 10, 1);
        }
    }

    public static void killeriniciado(){
        Bukkit.broadcastMessage("§c-");
        Bukkit.broadcastMessage(" §c§lEvento Killer Iniciado");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(" §cEntradas e saidas bloquadas!");
        Bukkit.broadcastMessage(" §f" + j + "§c jogadores participando");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(" §cPara assistir: §f/killer camarote ");
        Bukkit.broadcastMessage("§c-");
        for (Player all : Bukkit.getOnlinePlayers()){
            all.getWorld().playSound(all.getLocation(), Sound.ANVIL_LAND, 10, 1);
        }
    }

    public static void killerfinalizado(){
        Bukkit.broadcastMessage("§c-");
        Bukkit.broadcastMessage(" §c§lEvento Killer Finalizado");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(" §cVencedor: §f" + onevent);
        Bukkit.broadcastMessage(" §cPremiação: §f" + premiação);
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§fParabéns a todos os participantes!");
        Bukkit.broadcastMessage("§c-");
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.getWorld().playSound(all.getLocation(), Sound.ANVIL_BREAK, 10, 1);
        }

        }

    public static void canceladoparticipantes(){
        Bukkit.broadcastMessage("§c-");
        Bukkit.broadcastMessage(" §cEvento Killer foi cancelado por falta de participantes");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(" §cTodos os particpantes foram teleportados");
        Bukkit.broadcastMessage("§c-");
        for (Player all : Bukkit.getOnlinePlayers()){
            all.getWorld().playSound(all.getLocation(), Sound.ANVIL_BREAK, 10, 1);
        }
    }

    public static void vaipvpon(){
        for (Player alls : Bukkit.getOnlinePlayers()) {
            if (onevent.contains(alls.getName())) {
                alls.sendMessage("§c-");
                alls.sendMessage(" §eEvento começando em " + tempoini);
                alls.sendMessage("§c-");
                alls.getWorld().playSound(alls.getLocation(), Sound.ARROW_HIT, 10, 1);
            }
        }
    }


    public static void cancelado(){
        Bukkit.broadcastMessage("§c-");
        Bukkit.broadcastMessage(" §cEvento Killer foi cancelado por um ADMINISTRADOR");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(" §cTodos os particpantes foram teleportados");
        Bukkit.broadcastMessage("§c-");
        for (Player all : Bukkit.getOnlinePlayers()){
            all.getWorld().playSound(all.getLocation(), Sound.ANVIL_BREAK, 10, 1);
        }
    }














}
