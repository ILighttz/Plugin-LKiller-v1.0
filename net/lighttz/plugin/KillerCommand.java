package net.lighttz.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class KillerCommand implements CommandExecutor, Listener {

    public static boolean iniciado = false;  // quando o n podem mais entrar e o n pvp ta solto
    public static boolean podeentrar = false; // quando o evento ainda n começou e a rapaziada pode entrar e o cara de /killer iniciar
    public static boolean pvpoff = true;
    public static boolean cancelado = false;

    public static BukkitTask avisarTask;
    public static BukkitTask avisarTask2;

    World world = Bukkit.getWorld("World");

    Location event = new Location(world, 10, 55, 10);
    Location spawn = new Location(world, 100, 65, 10);
    Location camarote = new Location(world, 10, 90, 10);

    //Location setentrada = new Location(Main.getConfig().get("setentrada"));

    //Location setsaida = new Location(Main.getConfig().get("setsaida"));


    public static ArrayList<String> onevent = new ArrayList<>();

    public static int tempoini = 120;
    public static int premiação = 200000;
    public static int tempo = 300;
    public static int j = 0;
    public static int avisos  = 0;

    public static Player vencedor = Bukkit.getServer().getPlayer(String.valueOf(onevent));

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {

        Player p = (Player) sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cPrecisa ser um jogador");
        }

        if (cmd.getName().equalsIgnoreCase("killer")) {
            if (args.length < 1) {
                p.sendMessage("");
                p.sendMessage(" §c/killer entrar - Entre no killer");
                p.sendMessage(" §c/killer sair - Saia do killer");
                p.sendMessage(" §c/killer info - Veja informações do killer");
                p.sendMessage(" §c/killer camarote - Vá para o camarote do killer");
                p.sendMessage("");
                if (p.hasPermission("killeradmin.re")) {
                    p.sendMessage("  §eFunções para Administradores");
                    p.sendMessage("");
                    p.sendMessage(" §c/killer iniciar - Inicie o killer");
                    p.sendMessage(" §c/killer cancelar - Cancele o killer");
                    p.sendMessage(" §c/killer setentrada - Defina a entrada do killer");
                    p.sendMessage(" §c/killer setsaida - Defina a saida do killer");
                    p.sendMessage("");
                    return true;
                }
                return true;
            }

            if (args.length == 1) {
                String e = args[0];

                if (e.equalsIgnoreCase("setentrada")) {
                    if (p.hasPermission("killeradmin.re")) {
                        //p.getLocation(setentrada);
                        p.sendMessage("§aEntrada do Killer setada!");
                        return true;
                    }
                }

                if (e.equalsIgnoreCase("setsaida")) {
                    if (p.hasPermission("killeradmin.re")) {
                        //p.getLocation(setsaida);
                        p.sendMessage("§aSaida do Killer setada!");
                        return true;
                    }
                }

                if (e.equalsIgnoreCase("iniciar")) {

                    if (podeentrar) {
                        p.sendMessage("§cJá há um Killer sendo iniciado!");
                        return true;
                    }

                    if (iniciado) {
                        p.sendMessage("§cJá há um Killer ocorrendo!");
                        return true;
                    }

                    podeentrar = true;
                    avisarTask = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (avisos < 5) {
                                KillerMensagens.killerinciar();
                                avisos++;
                                tempo = tempo - 60;
                                return;
                            }
                            cancel();
                            podeentrar = false;
                            iniciado = true;

                            if (onevent.size() <= 1) {
                                KillerMensagens.canceladoparticipantes();
                                tempo = 300;
                                avisos = 0;
                                tempoini = 120;
                                avisarTask.cancel();
                                j = 0;
                                podeentrar = false;
                                iniciado = false;
                                for (Player all : Bukkit.getOnlinePlayers()){
                                    if (onevent.contains(all.getName())){
                                        all.teleport(spawn);
                                        onevent.remove(all.getName());
                                    }
                                }
                                iniciado = false;
                                return;
                            }

                            KillerMensagens.killeriniciado();
                            avisos = 0;
                            avisarTask2 = new BukkitRunnable(){

                                @Override
                                public void run() {
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            if (avisos < 2) {
                                                KillerMensagens.vaipvpon();
                                                avisos++;
                                                tempoini = tempoini - 60;
                                                return;
                                            }
                                            cancel();
                                            pvpoff = false;
                                            for (Player all : Bukkit.getOnlinePlayers()){
                                                if (onevent.contains(all.getName())){
                                                    all.sendMessage("§c-");
                                                    all.sendMessage(" §eO PVP foi ativado!");
                                                    all.sendMessage(" §eBoa sorte aos participantes!");
                                                    all.sendMessage("§c-");
                                                    all.getWorld().playSound(all.getLocation(), Sound.EXPLODE, 10, 1);
                                                }
                                            }
                                        }

                                    }.runTaskTimer(Main.getPlugin(Main.class), 0, 20L * /* TODO: 30 */ 5); //mudar aq para 1200 = 60s , 20L = 1s * 5 = 5s
                                }
                            }.runTaskLater(Main.getPlugin(Main.class), 100);
                        }
                    }.runTaskTimer(Main.getPlugin(Main.class), 0, 20L * /* TODO: 30 */ 5);

                }


                if (e.equalsIgnoreCase("cancelar")) {
                    if (!iniciado) {
                        if (podeentrar) {
                            avisarTask.cancel();
                            podeentrar = false;
                            cancelado = true;
                            tempo = 300;
                            avisos = 0;
                            j = 0;
                            tempoini = 120;
                            KillerMensagens.cancelado();
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                if (onevent.contains(all.getName())) {
                                    all.teleport(spawn);
                                    onevent.remove(all.getName());
                                }
                            }
                        } else {
                            p.sendMessage("§cNão há evento killer ocorrendo para ser cancelado!");
                        }
                    } else {
                        p.sendMessage("§cVocê não pode cancelar um killer que já começou");
                    }
                    return true;
                }

                if (e.equalsIgnoreCase("info")) {
                    if (podeentrar) {
                        p.sendMessage("§cO evento possui §f" + j + "§c jogadores participando");
                    } else {
                        p.sendMessage("§cNão há evento killer ocorrendo!");
                        return true;
                    }
                    if (iniciado) {
                        p.sendMessage("§cO evento possui §f" + j + "§c jogadores participando");
                    }
                    return true;
                }

                if (e.equalsIgnoreCase("camarote")) {
                    p.sendMessage("");
                    p.sendMessage("§eVocê foi teleportado para o camarote");
                    p.sendMessage("");
                    p.teleport(camarote);
                    return true;
                }


                if (!iniciado) {
                    if (podeentrar) {
                        if (!onevent.contains(p.getName())) {
                            if (e.equalsIgnoreCase("entrar")) {
                                p.sendMessage("");
                                p.sendMessage("§eVocê entrou no killer!");
                                p.sendMessage("§ePara sair digite: /killer sair");
                                p.sendMessage("");
                                p.teleport(event);
                                j = j + 1;
                                onevent.add(p.getName());

                                for (Player all : Bukkit.getOnlinePlayers()) {
                                    if (onevent.contains(all.getName())) {
                                        all.sendMessage("§eO evento possui " + j + " jogadores participando");
                                    }
                                }
                                return true;
                            }
                            return true;
                        }


                        if (e.equalsIgnoreCase("sair")) {
                            if (onevent.contains(p.getName())) {
                                if (!iniciado) {
                                    p.teleport(spawn);
                                    onevent.remove(p.getName());
                                    p.sendMessage("");
                                    p.sendMessage("§cVocê saiu do killer!");
                                    p.sendMessage("");

                                    j = j - 1;

                                    for (Player all : Bukkit.getOnlinePlayers()) {
                                        if (onevent.contains(all.getName())) {
                                            all.sendMessage("§eAgora o evento possui " + j + " jogadores participando");
                                        }
                                    }
                                    return true;
                                }
                            } else {
                                p.sendMessage("§cVocê não entrou no evento!");
                                return true;
                            }
                        }

                    } else {
                        p.sendMessage("§cNão há evento killer ocorrendo!");
                        return true;
                    }
                }
                return true;
            }


        }
        return false;
    }

}
