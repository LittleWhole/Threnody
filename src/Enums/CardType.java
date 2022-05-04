package Enums;

public enum CardType {
    OFFENSE, BUFF, DEFENSE, CROWD_CONTROL;

    public int GetID()  {
        switch(this)    {
            case OFFENSE : return 0;
            case BUFF : return 1;
            case DEFENSE : return 2;
            case CROWD_CONTROL : return 3;
            default : return -1;
        }
    }
}
