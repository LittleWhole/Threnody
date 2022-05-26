package managers;

import entities.units.Direction;
import entities.units.Unit;

public class AnimationManager {

    public static void animationSelect(Unit u ) {
        //some terribly structured code my monkey brain cant do any better
        if(u.getEwDir() == Direction.NONE)   {
            if(u.getNsDir() == Direction.NORTH) {
                u.setSprite(u.getSheet().getSprite(0,1));
            }
            else if(u.getNsDir() == Direction.SOUTH)    {
                u.setSprite(u.getSheet().getSprite(0,0));
            }
        }
        else if(u.getEwDir() == Direction.EAST) {
            if(u.getNsDir() == Direction.NORTH) {
                u.setSprite(u.getSheet().getSprite(0,1));
            }
            else if(u.getNsDir() == Direction.SOUTH)    {
                u.setSprite(u.getSheet().getSprite(0,4));
            }
            else {
                u.setSprite(u.getSheet().getSprite(0,3));
            }
        }
        else {
            if(u.getNsDir() == Direction.NORTH) {
                u.setSprite(u.getSheet().getSprite(0,1));
            }
            else if(u.getNsDir() == Direction.SOUTH)    {
                u.setSprite(u.getSheet().getSprite(0,4).getFlippedCopy(true, false));
            }
            else {
                u.setSprite(u.getSheet().getSprite(0,2));
            }
        }
    }
}
