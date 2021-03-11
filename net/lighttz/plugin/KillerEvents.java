package net.lighttz.plugin;

import net.minecraft.server.v1_8_R3.ChatMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import static net.lighttz.plugin.KillerCommand.*;


public class KillerEvents implements Listener {

    World world = Bukkit.getWorld("World");

    Location event = new Location(world, 10, 55, 10);
    Location spawn = new Location(world, 100, 65, 10);
    Location camarote = new Location(world, 10, 90, 10);

    @EventHandler
    public void aoDesconectar(PlayerQuitEvent e){

        Player p = (Player) e.getPlayer();

        if (KillerCommand.onevent.contains(p.getName())) {
            KillerCommand.j = KillerCommand.j - 1;
            KillerCommand.onevent.remove(p.getName());
            if (iniciado) {
                if (KillerCommand.onevent.size() > 1) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (KillerCommand.onevent.contains(all.getName())) {
                            all.sendMessage(p.getDisplayName() + p.getName() + " §edesconectou-se");
                            all.sendMessage("§eAgora o evento possui " + KillerCommand.j + " jogadores participando");
                        }
                    }
                } else {
                    if (KillerCommand.onevent.size() == 1) {
                        iniciado = false;
                        KillerCommand.avisarTask.cancel();
                        KillerCommand.avisarTask2.cancel();
                        pvpoff = true;
                        
                        tempo = 300;
                        avisos = 0;
                        tempoini = 120;
                        j = 0;
                        KillerCommand.podeentrar = false;
                        KillerCommand.cancelado = false;
                        for (Player alls : Bukkit.getOnlinePlayers()) {
                            if (KillerCommand.onevent.contains(alls.getName())) {
                                alls.sendMessage(p.getDisplayName() + p.getName() + " §edesconectou-se");
                            }
                        }
                        KillerMensagens.killerfinalizado();
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (KillerCommand.onevent.contains(all.getName())) {
                                all.sendMessage("");
                                all.sendMessage("§aParabéns por vencer o evento killer!");
                                all.sendMessage("§aVocê será teleportado em 30 segundos");
                                all.sendMessage("");
                                all.getWorld().playSound(all.getLocation(), Sound.ENDERDRAGON_DEATH, 10, 1);
                                for (Player pw : Bukkit.getOnlinePlayers()) {
                                    if (!(onevent.contains(pw.getName()))) {
                                        pw.getWorld().playSound(pw.getLocation(), Sound.ANVIL_LAND, 10, 1);
                                    }
                                }
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        all.teleport(spawn);
                                        onevent.remove(all.getName());
                                    }
                                }.runTaskLater(Main.getPlugin(Main.class), 500);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void aoMorrer(PlayerDeathEvent e){
        e.setDeathMessage(null);

            Player p = e.getEntity();
            Player k = p.getKiller();
                if (KillerCommand.onevent.contains(p.getName())) {
                    KillerCommand.j = KillerCommand.j - 1;
                    KillerCommand.onevent.remove(p.getName());

                    k.sendMessage("");
                    k.sendMessage("§eVocê eliminou " + p.getDisplayName() + p.getName());
                    p.sendMessage("§eVocê foi eliminado pelo(a) " + k.getDisplayName() + k.getName());

                    if (KillerCommand.onevent.size() > 1) {

                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (KillerCommand.onevent.contains(all.getName())) {
                            all.sendMessage("");
                            all.sendMessage("§eAgora o evento possui " + KillerCommand.j + " jogadores participando");
                            all.sendMessage("");
                        }
                    }
                } else {
                        if (iniciado) {
                            if (!pvpoff) {
                                if (KillerCommand.onevent.size() == 0) {
                                    Bukkit.broadcastMessage("§c-");
                                    Bukkit.broadcastMessage(" §c§lEvento Killer Finalizado");
                                    Bukkit.broadcastMessage("");
                                    Bukkit.broadcastMessage(" §cNão houve vencedor");
                                    Bukkit.broadcastMessage("§c-");
                                } else {
                                    if (KillerCommand.onevent.size() == 1) {
                                        KillerCommand.avisarTask.cancel();
                                        KillerCommand.avisarTask2.cancel();
                                        iniciado = false;
                                        pvpoff = true;
                                        tempo = 300;
                                        avisos = 0;
                                        tempoini = 120;
                                        j = 0;
                                        KillerCommand.podeentrar = false;
                                        KillerCommand.cancelado = false;
                                        KillerMensagens.killerfinalizado();
                                        for (Player all : Bukkit.getOnlinePlayers()) {
                                            if (KillerCommand.onevent.contains(all.getName())) {
                                                all.sendMessage("");
                                                all.sendMessage("§aParabéns por vencer o evento killer!");
                                                all.sendMessage("§aVocê será teleportado em 30 segundos");
                                                all.sendMessage("");
                                                all.getWorld().playSound(all.getLocation(), Sound.ENDERDRAGON_DEATH, 10, 1);
                                                for (Player pw : Bukkit.getOnlinePlayers()) {
                                                    if (!(onevent.contains(pw.getName()))){
                                                        pw.getWorld().playSound(pw.getLocation(), Sound.ANVIL_LAND, 10, 1);
                                                    }
                                                }
                                                new BukkitRunnable(){

                                                    @Override
                                                    public void run() {
                                                        all.teleport(spawn);
                                                        onevent.remove(all.getName());
                                                    }
                                                }.runTaskLater(Main.getPlugin(Main.class), 500);
                                            }
                                        }
                                    }
                                }


                            }
                        }
                    }
            }
    }

    @EventHandler
    public void aoDropar(PlayerDropItemEvent e){
        e.setCancelled(true);
        e.getPlayer().sendMessage("§cVocê não pode dropar itens aqui!");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPvP(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
                if (KillerCommand.onevent.contains(p.getName())) {
                    if (pvpoff) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
    }

    @EventHandler
    public void mensagemCancelar(PlayerCommandPreprocessEvent e) {
                if (KillerCommand.onevent.contains(e.getPlayer().getName())) {
                    if (!e.getMessage().equalsIgnoreCase("/killer sair")) {
                        if (!iniciado) {
                            if (KillerCommand.podeentrar) {
                                e.setCancelled(true);
                                e.getPlayer().sendMessage("§cNão é permitido uso de comandos aqui! /killer sair");
                                return;
                            }
                        }
                        if (iniciado) {
                            if (e.getMessage().startsWith("/")) {
                                if (KillerCommand.onevent.contains(e.getPlayer().getName())) {
                                    e.setCancelled(true);
                                    e.getPlayer().sendMessage("§cNão é permitido uso de comandos aqui!");
                                    return;
                                }
                            }
                            if (e.getMessage().startsWith("/g")){
                                e.setCancelled(true);
                                e.getPlayer().sendMessage("§cO chat global está desativado por causa do Killer!");
                                return;
                            }
                        }


                    } else {
                        if (iniciado) {
                            e.getPlayer().sendMessage("§cO evento começou, você não pode sair!");
                            return;
                        }

                    }
                }
    }

    public void onPotionSplash(PotionSplashEvent e) {
        for (LivingEntity livingEntity : e.getAffectedEntities()) {
            if (livingEntity instanceof Player) {
                Player p = (Player) livingEntity;

                if (KillerCommand.onevent.contains(p.getName())) e.getAffectedEntities().remove(p);
            }
        }
    }
}
