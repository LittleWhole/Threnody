package playerdata;

import inventory.Inventory;

import java.io.Serializable;

public class PlayerInventory extends PlayerData implements Serializable {
    private final Inventory itemInventory;
    private Inventory cardInventory;

    public PlayerInventory() {
        this.itemInventory = new Inventory(100);

    }
}
