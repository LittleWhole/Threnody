package entities.units.npc;

import combat.artes.Arte;
import combat.artes.Card;
import combat.artes.martial.SonicSlash;
import core.Main;
import entities.core.Coordinate;
import entities.units.player.Player;
import org.newdawn.slick.SlickException;
import playerdata.characters.Sigur;

import java.util.List;
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
