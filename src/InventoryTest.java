package ianjustin.hw2;

import java.util.*;

public class InventoryTest {

    public static void main(String[] args) {
        InventoryList inventory = new InventoryList();
        final Object[][] table = new String[7][]; 
        
        table[0] = new String[] {"Item ID","Item Description","Unit Price","Inventory"};
        table[1] = inventory.getItem("00001");
        table[2] = inventory.getItem("00002");
        table[3] = inventory.getItem("00003");
        table[4] = inventory.getItem("00004");
        table[5] = inventory.getItem("00005");
        table[6] = inventory.getItem("00006");

        for (final Object[] row : table) {
            System.out.format("%-15s%-15s%-15s\n", row);
        }
    }
}
