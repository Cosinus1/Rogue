package com.mygdx.game.Graphic.Elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.Back.Character.Character;

public class BarLife {

    private Texture barTexture;
    private Texture backgroundTexture;
    private BitmapFont font;

    public BarLife(){
        createTexture();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    public void drawLifeBar(SpriteBatch batch, Character character){
        float remainingLifePercentage = (float) character.getPV() / character.getPV_max();
        //dessiner la barre de vie
        batch.draw(backgroundTexture,5,595);
        batch.draw(barTexture,10,600,400*remainingLifePercentage, 20);
        font.draw(batch, character.getPV() + " / " + character.getPV_max(), 190, 615);
    }
 

    private void createTexture(){
        int barWidth = 400;
        int barHeight = 20;

        // Créer la texture de la barre de vie colorée
        Pixmap barPixmap = new Pixmap(barWidth, barHeight, Pixmap.Format.RGBA8888);
        barPixmap.setColor(Color.GREEN); // Couleur de la barre de vie (par défaut)
        barPixmap.fillRectangle(0, 0, barWidth, barHeight);
        barTexture = new Texture(barPixmap);
        barPixmap.dispose();

        // Créer la texture de l'arrière-plan de la barre de vie
        Pixmap backgroundPixmap = new Pixmap(barWidth+10, barHeight+10, Pixmap.Format.RGBA8888);
        backgroundPixmap.setColor(Color.BLACK); // Couleur de l'arrière-plan de la barre de vie (par défaut)
        backgroundPixmap.fillRectangle(0,0, barWidth+10, barHeight+10);
        backgroundTexture = new Texture(backgroundPixmap);
        backgroundPixmap.dispose();
    }

    public void dispose(){
        barTexture.dispose();
        backgroundTexture.dispose();
    }
}
