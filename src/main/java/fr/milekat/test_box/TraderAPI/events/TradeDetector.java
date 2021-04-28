package fr.milekat.test_box.TraderAPI.events;

import fr.milekat.test_box.TraderAPI.Trader;
import fr.milekat.test_box.TraderAPI.classes.TraderInventory;
import fr.milekat.test_box.TraderAPI.classes.TraderTrade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;

import java.util.Map;
import java.util.Objects;

public class TradeDetector implements Listener {
    private final TraderInventory inventory;

    public TradeDetector(TraderInventory inventory) {
        this.inventory = inventory;
        Bukkit.getServer().getPluginManager().registerEvents(this,
                Objects.requireNonNull(Bukkit.getPluginManager().getPlugin(Trader.PL_NAME)));
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.NOTHING) return;
        if (event.getWhoClicked().getUniqueId().equals(this.inventory.getForWho().getUniqueId())) {
            if (event.getRawSlot() == -999) return;
            TraderTrade trade = inventory.getTrades().get(((MerchantInventory)
                    event.getWhoClicked().getOpenInventory().getTopInventory()).getSelectedRecipeIndex());
            int tradescount = 1;
            if (event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                // This situation is where the player SHIFT+CLICKS the output item to buy multiple times at once.
                ItemStack itemOne = this.inventory.getForWho().getOpenInventory().getTopInventory().getItem(0);
                ItemStack itemTwo = this.inventory.getForWho().getOpenInventory().getTopInventory().getItem(1);
                ItemStack result = event.getCurrentItem();
                int cantrade;
                /* 1 - Get how many the player can trade with his items */
                if (itemOne == null || result==null) return;
                cantrade = (int) Math.floor((double) itemOne.getAmount() / (double) trade.getItemOne().getAmount());
                if (itemTwo != null) {
                    cantrade = Math.min(cantrade,
                            (int) Math.floor((double) itemTwo.getAmount() / (double) trade.getItemTwo().getAmount()));
                }
                /* 2 - Check max stack size of result */
                cantrade = Math.min(cantrade, result.getMaxStackSize());
                Bukkit.getLogger().info(String.valueOf(cantrade));
                /* 3 - Get how many times the player can store the trade result in his inventory */
                for (int i=1; i <= cantrade; i++) {
                    Bukkit.getLogger().info(String.valueOf(tradescount));
                    if (canStore(event.getWhoClicked().getInventory(), result, i)) {
                        tradescount = i;
                    } else break;
                }
            }
            if (event.getRawSlot() == 2 && !Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.AIR)) {
                TradeComplete tradeCompleteEvent = new TradeComplete(inventory, (Player) event.getWhoClicked(), trade, tradescount);
                Bukkit.getPluginManager().callEvent(tradeCompleteEvent);
                if (tradeCompleteEvent.isCancelled()) event.setCancelled(true);
            }
        }
    }

    /**
     *      Check if player can store ItemStack in his inventory
     */
    private boolean canStore(Inventory baseInv, ItemStack items, int nbAdd) {
        Inventory inv = Bukkit.createInventory(null, 36, "canStore");
        inv.setContents(baseInv.getStorageContents());
        for (int i=0;i<nbAdd;i++){
            final Map<Integer, ItemStack> map = inv.addItem(items); // Attempt to add in inventory
            if (!map.isEmpty()) { // If not empty, it means the player's inventory is full.
                return false;
            }
        }
        return true;
    }
}
