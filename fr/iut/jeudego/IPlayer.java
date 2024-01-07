package fr.iut.jeudego;

public abstract class IPlayer {

    protected int score;
    protected char pawnRepresentation;
    protected String color;

    public IPlayer(char pawnRepresentation, String color) {
        this.score = 0;
        this.pawnRepresentation = pawnRepresentation;
        this.color = color;
    }

    public int getScore() {
        return this.score;
    }
    public char getPawnRepresentation() {
        return pawnRepresentation;
    }

    public boolean isHuman () {
        return true;
    }

    private void addScore(int score) {
        this.score += score;
    }

    public String getColor() {
        return this.color;
    }
}
