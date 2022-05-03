package inventory;

public abstract class Card {
    protected int level, timeLastUsed;
    protected float cost, atk, cooldown;
    protected String name, type;

    public Card(String name, String type, float cost, float atk, float cooldown) {
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
}
