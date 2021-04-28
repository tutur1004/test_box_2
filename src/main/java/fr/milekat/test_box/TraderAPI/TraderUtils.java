package fr.milekat.test_box.TraderAPI;

import fr.milekat.test_box.TraderAPI.classes.TraderInventory;
import fr.milekat.test_box.TraderAPI.classes.TraderTrade;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class TraderUtils {
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
}
