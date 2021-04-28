package fr.milekat.test_box.TraderAPI;

import fr.milekat.test_box.TraderAPI.classes.TraderInventory;
import fr.milekat.test_box.TraderAPI.events.TradeDetector;
import fr.milekat.test_box.Utils.NMSUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Merchant;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Trader {
    public static final String PL_NAME = "Villager";
    private final TraderInventory inventory;
    private final Class<?> nms_merchant;

    public Trader(Player player) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        inventory = new TraderInventory(null, player, new ArrayList<>(), 0);
        Constructor<?> craftMerchantCustom = NMSUtils.getNMSCraftBukkit("CraftMerchantCustom").getConstructor(String.class);
        this.nms_merchant = (Class<?>) craftMerchantCustom.newInstance(inventory.getName());
        this.nms_merchant.getDeclaredMethod("setRecipes", NMSUtils.getNMSRecipes(inventory).getClass());
        new TradeDetector(inventory);
    }

    public TraderInventory getInventory() {
        return this.inventory;
    }

    public void open() {
        inventory.getForWho().openMerchant((Merchant) nms_merchant.getClassLoader(), true);
    }
}
