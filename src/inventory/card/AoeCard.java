package inventory.card;

import Enums.CardType;

public class AoeCard extends Card{
    public AoeCard(String name, float cost, float atk, float cooldown) {
        super(name, CardType.OFFENSE, cost, atk, cooldown);
    }

    public void use()    {

    }
}
