package me.leddev.holymobs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class GiveItems implements CommandExecutor {

    private final JavaPlugin plugin;

    public GiveItems(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage("Пожалуйста, укажите никнейм.");
            return false;
        }

        String nickname = args[0];

        Player player = Bukkit.getPlayer(nickname);

        if (player == null) {
            sender.sendMessage("Игрок не найден.");
            return true;
        }

        if (!sender.hasPermission("ld.admin")) {
            sender.sendMessage("У вас недостаточно прав для использования этой команды.");
            return true;
        }

        ItemStack itemStack = new ItemStack(Material.GHAST_SPAWN_EGG, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(HexUtil.translate(plugin.getConfig().getString("egg_name")));
        itemStack.setItemMeta(itemMeta);

        player.getInventory().addItem(itemStack);

        return true;
    }
}
