package me.leddev.holymobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitListener implements Listener {

    private final JavaPlugin plugin;

    public BukkitListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }

        Location spawnLocation2 = event.getClickedBlock().getLocation();
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock2 = event.getClickedBlock();
            if (clickedBlock2 != null && clickedBlock2.getType() == Material.SPAWNER) {
                CreatureSpawner spawner = (CreatureSpawner) clickedBlock2.getState();

                EntityType entityType = EntityType.IRON_GOLEM;
                spawner.setSpawnedType(entityType);

                spawner.update();

                event.setCancelled(true);
                ItemStack item2 = new ItemStack(Material.GHAST_SPAWN_EGG, 1);
                ItemMeta meta2 = item2.getItemMeta();
                meta2.setDisplayName(HexUtil.translate(plugin.getConfig().getString("egg_name")));
                item2.setItemMeta(meta2);
                item2.setAmount(1);
                player.getInventory().removeItem(item2);
                return;
            }

            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.GHAST_SPAWN_EGG
                    && item.getItemMeta().getDisplayName().equals(HexUtil.translate(plugin.getConfig().getString("egg_name")))) {
                event.setCancelled(true);
                ItemStack item3 = new ItemStack(Material.GHAST_SPAWN_EGG, 1);
                ItemMeta meta3 = item3.getItemMeta();
                meta3.setDisplayName(HexUtil.translate(plugin.getConfig().getString("egg_name")));
                item3.setItemMeta(meta3);
                item3.setAmount(1);
                player.getInventory().removeItem(item3);
                spawnGolem(spawnLocation2);
            }
        }
    }

    public void spawnGolem(Location location) {
        World world = location.getWorld();
        location.add(0, 1, 0);
        IronGolem golem = (IronGolem) world.spawnEntity(location, EntityType.IRON_GOLEM);
    }
}
