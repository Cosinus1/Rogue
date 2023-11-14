package com.mygdx.game.Character;

public interface CharacterInterface {
    int getRange();
    int getMove();
    int getDefense();
    String getName();
    int getPV();
    int attaquer();
    void recevoir(int degats);
}
