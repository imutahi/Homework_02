package ianjustin.hw2;

import java.util.*;

/**
* Inventory class uses calls a method to store list of inventory.
*/
public class InventoryList {

   // The items.
   private List<InventoryItem> itemList;

   /**
   * Instantiates a new list and populates with specified values.
   */
   public InventoryList() {
       itemList = new ArrayList<InventoryItem>();
       // Custom inventory class accepts parameters (id, description, price, quantity)
       itemList.add(new InventoryItem("00001", "New Inspiron 15", "379.99", 157));
       itemList.add(new InventoryItem("00002", "New Inspiron 17", "449.99", 128));
       itemList.add(new InventoryItem("00003", "New Inspiron 15R", "549.99", 202));
       itemList.add(new InventoryItem("00004", "New Inspiron 15z Ultrabook", "749.99", 315));
       itemList.add(new InventoryItem("00005", "XPS 14 Ultrabook", "999.99", 261));
       itemList.add(new InventoryItem("00006", "New XPS 12 UltrabookXPS", "1199.99", 178));
   }

   public InventoryItem getItem(String id) {
       for (int i = 0; i < itemList.size(); i++) {
           if (id.equals(itemList.get(i).getId())) {
               return itemList.get(i);
           }
       }
       return new InventoryItem();
   }

   public String toString() {
       String headers = "Item ID   Item Description\n";
       String body = "";
       for (InventoryItem item : itemList) {
           body += item.getId();
           body += "    ";
           body += item.getDescription();
           body += "\n";
       }
       return headers + body;
   }
}
