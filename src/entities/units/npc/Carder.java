package entities.units.npc;

import combat.artes.Card;
import combat.artes.strike.SonicSlash;
import core.Main;
import entities.units.player.Player;
import org.newdawn.slick.SlickException;

import java.util.Map;

public class Carder extends NPC {
    private Map<Card<?>, Integer> stock;

    public Carder(float x, float y) throws SlickException {
        super(x, y);
        stock.put(new Card<SonicSlash>(), 20);

    }

    public void buy(Player p, Card<?> card)   {
        if(Main.stats.gold >= stock.get(card))  {
            Main.stats.gold -= stock.get(card);
            p.addToDeck(card.getArte());
        }
    }
}
