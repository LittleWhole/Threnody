package entities.units.npc;

import combat.artes.Arte;
import combat.artes.Card;
import combat.artes.strike.SonicSlash;
import core.Main;
import entities.units.player.Player;
import graphics.ui.menu.Menu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Carder extends NPC {
    private Map<Card, Integer> stock;
    private Menu storeMenu;
    private boolean displayMenu;
    public Carder(float x, float y) throws SlickException {
        super(x, y);
        stock = new HashMap<Card, Integer>();
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        stock.put(new Card(SonicSlash.class), 20);
        storeMenu = new Menu(1200, 1000) {
            @Override
            protected void subrender(Graphics g) {
                int col = 0;
                int row = 0;
                for(Map.Entry<Card, Integer> entry: stock.entrySet())  {
                    Card product = entry.getKey();
                    int price = entry.getValue();
                    if(product.getArte() != null) {
                        product.getSprite().drawCentered(storeMenu.getX()- storeMenu.getWidth()/2 + 75 + (product.getSprite().getWidth() / 2) + col * 210,
                                storeMenu.getY() - storeMenu.getHeight()/2 + 75 + (product.getSprite().getHeight() / 2) + row * 300);
                        col++;
                        if (col % 5 == 0) {
                            row++;
                            col = 0;
                        }
                    }
                }
            }

            @Override
            protected void initializeFonts() {

            }
        };

        displayMenu = false;

    }

    public void render(GameContainer gc, float plrX, float plrY)    {//add title later
        renderSprite(gc, plrX, plrY);
        if(isInteracting()) {
            updateMenu(gc.getInput());
            storeMenu.render(gc.getGraphics(), gc.getInput().getMouseX(), gc.getInput().getMouseY());
        }
    }

    private void updateMenu(Input input)   {
        //later add mouseclick shit
    }

    public void buy(Player p, Card card) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(Main.stats.gold >= stock.get(card))  {
            Main.stats.gold -= stock.get(card);
            p.addToDeck(card.getArte().getConstructor(Player.class).newInstance(p));
        }
        else    {
            //add dialog that says can't buy
        }
    }
}
