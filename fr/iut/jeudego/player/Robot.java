package fr.iut.jeudego.player;

import fr.iut.jeudego.IPlayer;

public class Robot extends IPlayer {
    public Robot(char pawnRepresentation, String color) {
        super(pawnRepresentation, color);
    }

    @Override
    public boolean isHuman() {
        return false;
    }
}