package ianjustin.hw2;

import java.util.*;
import com.google.gson.Gson;

public class InventoryTest {

    public static void main(String[] args) {
        InventoryList inventory = new InventoryList();
        final Object[][] table = new String[7][]; 
        
        table[0] = new String[] {"Item ID","Item Description","Unit Price","Inventory"};
        table[1] = inventory.getItem("00001").getValues();
        table[2] = inventory.getItem("00002").getValues();
        table[3] = inventory.getItem("00003").getValues();
        table[4] = inventory.getItem("00004").getValues();
        table[5] = inventory.getItem("00005").getValues();
        table[6] = inventory.getItem("00006").getValues();

        for (final Object[] row : table) {
            System.out.format("%-10s %-30s %-20s %-25s\n", row);
        }

        System.out.println("");

        List<String> inventoryJsonList = new LinkedList<String>();
        String[] ids = {"00001","00002","00003","00004","00005","00006"};
        for (String id : ids) {
            String json = inventory.getItem(id).toJson();
            System.out.println(json);
            inventoryJsonList.add(json);
        }

        List<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
        for (String json : inventoryJsonList) {
            Gson gson = new Gson();
            InventoryItem inventoryItem = gson.fromJson(json,InventoryItem.class);
            inventoryItems.add(inventoryItem);
        }

        System.out.println("");

        table[0] = new String[] {"Item ID","Item Description","Unit Price","Inventory"};
        for (int i=1; i<7; i++) {
            table[i] = inventoryItems.get(i-1).getValues();
        }

        for (final Object[] row : table) {
            System.out.format("%-10s %-30s %-20s %-25s\n", row);
        }
    }
}
