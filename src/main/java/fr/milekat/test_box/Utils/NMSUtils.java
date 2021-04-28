package fr.milekat.test_box.Utils;

import fr.milekat.test_box.TraderAPI.classes.TraderInventory;
import fr.milekat.test_box.TraderAPI.classes.TraderTrade;
import org.bukkit.Bukkit;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class NMSUtils {
    public static List<MerchantRecipe> getNMSRecipes(TraderInventory inventory) {
        List<MerchantRecipe> result = new ArrayList<>();
        for (TraderTrade trd : inventory.getTrades()) {
            MerchantRecipe toAdd = new MerchantRecipe(trd.getResult(), trd.getMaxUses());
            toAdd.addIngredient(trd.getItemOne());
            if (trd.requiresTwoItems()) toAdd.addIngredient(trd.getItemTwo());
            result.add(toAdd);
        }
        return result;
    }

    public static Class<?> getNMSCraftBukkit(String name) throws ClassNotFoundException {
        return Class.forName("org.bukkit.craftbukkit." +
                Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + "." + name);
    }
}
