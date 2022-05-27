package managers;

import entities.units.Direction;
import entities.units.Unit;

public class AnimationManager {

    public static void animationSelect(Unit u ) {
        //some terribly structured code my monkey brain cant do any better
        if(u.getEwDir() == Direction.NONE)   {
            if(u.getNsDir() == Direction.NORTH) {

                u.setSpriteY(1);
            }
            else if(u.getNsDir() == Direction.SOUTH)    {

                u.setSpriteY(0);
            }
        }
        else if(u.getEwDir() == Direction.EAST) {
            if(u.getNsDir() == Direction.NORTH) {

                u.setSpriteY(7);
            }
            else if(u.getNsDir() == Direction.SOUTH)    {

                u.setSpriteY(4);
            }
            else {

                u.setSpriteY(3);
            }
        }
        else {
            if(u.getNsDir() == Direction.NORTH) {

                u.setSpriteY(6);
            }
            else if(u.getNsDir() == Direction.SOUTH)    {

                u.setSpriteY(5);
            }
            else {

                u.setSpriteY(2);
            }
        }
    }

    public static void animationCycle(Unit u)   {
        u.setFrame((u.getFrame() + 1)/10 >= u.getSheet().getHorizontalCount()?10:u.getFrame()+1);
        u.setSprite(u.getSheet().getSprite((u.getFrame()/10),u.getSpriteY()));

    }

}
