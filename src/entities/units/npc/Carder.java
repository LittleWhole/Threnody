package entities.units.npc;

import combat.artes.Arte;
import combat.artes.Card;
import combat.artes.elemental.AquaLimit;
import combat.artes.elemental.DualTheSol;
import combat.artes.elemental.Flamberge;
import combat.artes.elemental.RendingGale;
import combat.artes.mystic.*;
import combat.artes.strike.DragonFang;
import combat.artes.strike.ImpactCross;
import combat.artes.strike.SonicSlash;
import combat.artes.strike.TwinWhip;
import combat.artes.support.Elixir;
import combat.artes.support.Fortification;
import combat.artes.support.Heal;
import combat.artes.support.Mana;
import core.Main;
import entities.units.player.Player;
import graphics.ui.menu.CloseButton;
import graphics.ui.menu.DialogBox;
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
    public Map<Card, Integer> getStock()    {
        return stock;
    }
    private Menu storeMenu;
    private boolean displayMenu;
    public Carder(float x, float y) throws SlickException {
        super(x, y);
        stock = new HashMap<Card, Integer>();
        stock.put(new Card(SonicSlash.class), 10);
        stock.put(new Card(DragonFang.class), 30);
        stock.put(new Card(ImpactCross.class), 40);
        //stock.put(new Card(TwinWhip.class), 20);
        stock.put(new Card(AquaLimit.class), 100);
        stock.put(new Card(DualTheSol.class), 200);
        stock.put(new Card(RendingGale.class), 90);
        //stock.put(new Card(Flamberge.class), 20);
        stock.put(new Card(Heal.class), 50);
        stock.put(new Card(Mana.class), 30);
        //stock.put(new Card(Fortification.class), 20);
        stock.put(new Card(Elixir.class), 70);
        stock.put(new Card(Expiation.class), 500);
        //stock.put(new Card(InnumerableWounds.class), 20);
        //stock.put(new Card(TrillionDrive.class), 20);
        //stock.put(new Card(GardenOfInnocence.class), 20);
        //stock.put(new Card(DivineConqueror.class), 20);
        storeMenu = new Menu(1200, 1000) {
            @Override
            protected void subrender(Graphics g) {

            }

            @Override
            protected void initializeFonts() {

            }
        };

        displayMenu = false;

    }

    public void render(GameContainer gc, float plrX, float plrY, Player p)    {//add title later
        renderSprite(gc, plrX, plrY);
        if(isInteracting()) {
            storeMenu.render(gc.getGraphics(), gc.getInput().getMouseX(), gc.getInput().getMouseY());
            int col = 0;
            int row = 0;
            int cardX;
            int cardY;
            for(Map.Entry<Card, Integer> entry: stock.entrySet())  {
                Card product = entry.getKey();
                int price = entry.getValue();
                cardX = storeMenu.getX()- storeMenu.getWidth()/2 + 75 + (product.getSprite().getWidth() / 2) + col * 210;
                cardY =storeMenu.getY() - storeMenu.getHeight()/2 + 75 + (product.getSprite().getHeight() / 2) + row * 300;
                if(product.getArte() != null) {
                    if ((gc.getInput().getMouseX() > cardX - product.getSprite().getWidth()/ 2 && gc.getInput().getMouseX() < cardX + product.getSprite().getWidth() / 2) &&
                            (gc.getInput().getMouseY() > cardY - product.getSprite().getHeight() / 2 && gc.getInput().getMouseY() < cardY + product.getSprite().getHeight() / 2)) {
                        product.getSprite().getScaledCopy(1.3f).drawCentered(cardX + product.getSprite().getWidth()/2f, cardY + product.getSprite().getHeight()/2f);
                        if (gc.getInput().isMousePressed(0)) {
                            try {
                                buy(p, product);
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (InstantiationException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else product.getSprite().drawCentered(cardX, cardY);
                    col++;
                    if (col % 5 == 0) {
                        row++;
                        col = 0;
                    }
                }
            }
        }
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
