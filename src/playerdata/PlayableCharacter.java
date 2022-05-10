package playerdata;

import combat.artes.Arte;
import core.Constants;
import entities.units.Unit;
import entities.units.player.PlayerOld;
import inventory.Inventory;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import java.io.Serializable;
import java.util.ArrayList;

enum state  {
    SELECTING,CASTING;
}

public abstract strictfp sealed class PlayableCharacter extends PlayerData implements Serializable permits Sigur, Phaedra {

    // PlayableCharacter is a data class. It will only be used to store characters' playerdata and will only be instantiated once per character.
    protected int level, exp, maxExp;

    protected int health;
    protected int attack;
    protected int defense;
    protected int critRate;
    protected int critDamage;
    protected int eAttack;
    protected int eDefense;
    protected state state;
    // final ArrayList<Arte> dRINGU = new ArrayList<Arte>();

    protected Image sprite;
    protected ArrayList<Arte> arteDeck;
    protected ArrayList<Arte> arteHand;
    protected Arte move;
    protected PlayerOld entity;

    protected PlayableCharacter() {
        // Default character constructor
        this.level = 1;
        this.exp = 0;
        this.maxExp = Constants.LevelingConstants.MAX_EXP(1);
    }

    protected PlayableCharacter(int level, int exp) {
        this();
        // Default character constructor
        this.level = level;
        this.exp = exp;
         this.maxExp = Constants.LevelingConstants.MAX_EXP(level);
    }

    public void move(Unit target, GameContainer gc, Graphics g) throws InterruptedException {
        for(int i = 0; i < arteHand.size(); i++)    {
            g.drawImage(arteHand.get(i).getSprite(), );
        }
        this.state = playerdata.state.SELECTING;
        move = cardSelect(gc.getInput());
        this.state = playerdata.state.CASTING;
        move.use(target);
    }

    public Arte cardSelect(Input input) throws InterruptedException {
        Arte selected = null;
        while(selected == null) {
            if(input.isKeyDown(Input.KEY_1))    {
                selected = arteHand.get(0);
                arteHand.remove(0);
            }
            if(input.isKeyDown(Input.KEY_2))    {
                selected = arteHand.get(1);
                arteHand.remove(1);
            }
            if(input.isKeyDown(Input.KEY_3))    {
                selected = arteHand.get(2);
                arteHand.remove(2);
            }
            if(input.isKeyDown(Input.KEY_4))    {
                selected = arteHand.get(3);
                arteHand.remove(3);
            }
            if(input.isKeyDown(Input.KEY_5)) {
                selected = arteHand.get(4);
                arteHand.remove(4);
            }
            if(input.isKeyDown(Input.KEY_6))    {
                selected = arteHand.get(5);
                arteHand.remove(5);
            }
            wait();
        }
        return selected;
    }

    public PlayerOld getEntity() {
        return entity;
    }

    public void setEntity(PlayerOld entity) {
        this.entity = entity;
    }

    public int getAttack() {
        return  attack;
    }
}
