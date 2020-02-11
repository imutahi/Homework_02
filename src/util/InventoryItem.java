package ianjustin.hw2;

import java.util.*;
import com.google.gson.Gson;

/**
* Inventory class uses calls a method to store list of inventory.
*/
public class InventoryItem {

    // The resource id
    private String id;
    // The resource description.
    private String description;
    // The resource price (because it cannot be changed) 
    private String price;
    // The number available in inventory
    private Integer quantity;
      
   /**
   * Instantiates a new list item and populates with specified values.
   */
   public InventoryItem() {
       this.id = null;
       this.description = null;
       this.price = null;
       this.quantity = null;
   }

   /**
   * Instantiates a new list item and populates with specified values.
   */
   public InventoryItem(String id, String description, String price, int quantity) {
       this.id = id;
       this.description = description;
       this.price = price;
       this.quantity = quantity;
   }

   public String getId() {
       return this.id;
   }

   public String[] getValues() {
      return new String[] {this.id, this.description, this.price, this.quantity.toString()};
   }
   
   public String toJson() {
       Gson gson = new Gson();
       return gson.toJson(this);
   }
}
