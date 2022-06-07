package graphics.ui.menu;

import core.Main;
import graphics.ui.UserInterfaceable;
import inventory.Inventory;
import org.newdawn.slick.Graphics;

public class InventoryMenu extends Menu implements UserInterfaceable {
    private Inventory contents;
    public InventoryMenu() {
        super(1500, 900);
        contents = new Inventory(10);
    }
    public InventoryMenu(int size) {
        super(1500, 900);
        contents = new Inventory(size);
    }

    @Override
    protected void subrender(Graphics g) {

    }

    @Override
    protected void initializeFonts() {

    }
}
