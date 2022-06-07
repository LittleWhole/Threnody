package entities.units.npc;

import combat.artes.Card;
import combat.artes.strike.SonicSlash;
import core.Main;
import entities.units.player.Player;
import graphics.ui.menu.Menu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

public class Carder extends NPC {
    private Map<Card<?>, Integer> stock;
    private Menu storeMenu;
    private boolean displayMenu;
    public Carder(float x, float y) throws SlickException {
        super(x, y);
        stock = new HashMap<Card<?>, Integer>();
        stock.put(new Card<SonicSlash>(), 20);
        stock.put(new Card<SonicSlash>(), 20);
        stock.put(new Card<SonicSlash>(), 20);
        stock.put(new Card<SonicSlash>(), 20);
        storeMenu = new Menu(500, 900) {
            @Override
            protected void subrender(Graphics g) {
                int col = 0;
                int row = 0;
                for(Map.Entry<Card<?>, Integer> entry: stock.entrySet())  {
                    Card<?> product = entry.getKey();
                    int price = entry.getValue();
                    if(product.getArte() != null) {
                        product.getArte().getCard().drawCentered(storeMenu.getX() + 10 + (product.getArte().getCard().getWidth() / 2) + col * 200,
                                storeMenu.getY() + 10 + (product.getArte().getCard().getHeight() / 2) + row * 300);
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

    public void interact()  {
        displayMenu = true;
    }
    public void exit()  {
        displayMenu = false;
    }
    public void render(GameContainer gc, float plrX, float plrY)    {
        renderSprite(gc, plrX, plrY);
        if(displayMenu) {
            updateMenu(gc.getInput());
            storeMenu.render(gc.getGraphics(), gc.getInput().getMouseX(), gc.getInput().getMouseY());
        }
    }

    private void updateMenu(Input input)   {
        //later add mouseclick shit
    }

    public void buy(Player p, Card<?> card)   {
        if(Main.stats.gold >= stock.get(card))  {
            Main.stats.gold -= stock.get(card);
            p.addToDeck(card.getArte());
        }
        else    {
            //add dialog that says can't buy
        }
    }
}
