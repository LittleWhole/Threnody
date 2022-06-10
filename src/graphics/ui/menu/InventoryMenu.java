package graphics.ui.menu;

import combat.artes.Card;
import core.Main;
import graphics.ui.Button;
import graphics.ui.UserInterfaceable;
import inventory.Inventory;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class InventoryMenu extends Menu implements UserInterfaceable {
    private ArrayList<Card> inventory;

    public Button getNext() {
        return next;
    }

    public Button getPrevious() {
        return previous;
    }

    public boolean renderNext;
    public boolean renderPrevious;

    private Button next;
    private Button previous;
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
        next = new Button("+");
        previous = new Button("-");
    }
    public InventoryMenu(int size) {
        super(1500, 900);
        contents = new Inventory(size);
        this.next = new Button("+");
        this.previous = new Button("-");
    }
    public InventoryMenu(ArrayList a) {
        super(1500, 900);
        inventory = a;
        next = new Button("+");
        previous = new Button("-");
    }
    public InventoryMenu(int w, int h, ArrayList<Card> a, int cols) {
        super(w, h);
        inventory = a;
        this.cols = cols;
        next = new Button(getX()+getWidth()/2-50, getY()+getHeight()/2 - 25,"+", () ->{});
        previous = new Button(getX() - getWidth()/2 + 50, getY()+getHeight()/2 - 25,"-", () ->{});
    }

    @Override
    protected void subrender(Graphics g) {
        for(int i = 0; i < inventory.size(); i++)   {
            inventory.get(i).getSprite().draw(x-(width/2) + (i%cols)*(210) + 25, y-height/2 + (i/cols)*(310) + 25);
        }
    }

    public void renderNext(GameContainer gc)    {
        next.render(gc.getGraphics(), gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }

    public void renderPrevious(GameContainer gc)    {
        previous.render(gc.getGraphics(), gc.getInput().getMouseX(), gc.getInput().getMouseY());
    }

    @Override
    protected void initializeFonts() {

    }
}
