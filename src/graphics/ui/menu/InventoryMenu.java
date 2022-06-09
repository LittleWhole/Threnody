package graphics.ui.menu;

import combat.artes.Card;
import core.Main;
import graphics.ui.UserInterfaceable;
import inventory.Inventory;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class InventoryMenu extends Menu implements UserInterfaceable {
    private ArrayList<Card> inventory;

    public void addToInv(Card c)    {
        inventory.add(c);
    }

    public ArrayList<Card> getInventory()   {
        return inventory;
    }
    private int cols;
    private Inventory contents;
    public InventoryMenu() {
        super(1500, 900);
        contents = new Inventory(10);
    }
    public InventoryMenu(int size) {
        super(1500, 900);
        contents = new Inventory(size);
    }
    public InventoryMenu(ArrayList a) {
        super(1500, 900);
        inventory = a;
    }
    public InventoryMenu(int w, int h, ArrayList<Card> a, int cols) {
        super(w, h);
        inventory = a;
        this.cols = cols;
    }

    @Override
    protected void subrender(Graphics g) {
        for(int i = 0; i < inventory.size(); i++)   {
            inventory.get(i).getSprite().draw(x-(width/2) + (i%cols)*(210) + 50, y-height/2 + (i/cols)*(310) + 50);
        }
    }

    @Override
    protected void initializeFonts() {

    }
}
