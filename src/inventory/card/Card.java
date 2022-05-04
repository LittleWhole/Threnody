package inventory.card;

import Enums.CardType;
import org.newdawn.slick.Graphics;

public abstract class Card {
    protected int level, timeLastUsed;
    protected float cost, atk, cooldown;
    protected String name;
    protected CardType type;

    public Card(String name, CardType type, float cost, float atk, float cooldown) {
        level = 1;
        this.cost = cost;
        this.atk = atk;
        this.cooldown = cooldown;
        this.name = name;
        this.type = type;
        this.timeLastUsed = 0;
    }
    public void use()   {

    }

    public void inventoryRender(Graphics g)   {

    }

    public void useRender(Graphics g)   {

    }
}
