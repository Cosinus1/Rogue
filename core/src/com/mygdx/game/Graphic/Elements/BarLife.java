package com.mygdx.game.Graphic.Elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Back.Character.Character;
import com.mygdx.game.Back.Character.Ennemie.Ennemie;
import com.mygdx.game.Back.Character.Hero.Hero;
import com.mygdx.game.Graphic.GraphicCharacter.*;

public class BarLife {

    private Texture barTexture;
    private Texture backgroundTexture;
    private BitmapFont font;

    public BarLife(){
        createTexture();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    public void drawHeroLifeBar(SpriteBatch batch, Hero hero){
        float remainingLifePercentage = (float) hero.getPV() / hero.getPV_max();
        //dessiner la barre de vie
        batch.draw(backgroundTexture,5,595);
        batch.draw(barTexture,10,600,400*remainingLifePercentage, 20);
        font.draw(batch, hero.getPV() + " / " + hero.getPV_max(), 190, 615);
    }

    public void drawPNJLifeBar(SpriteBatch batch, GraphicEnnemie ennemie){
        TextureRegion textureRegion = ennemie.getObject().getTextureRegion();
        float objectX = (float) ennemie.getObject().getProperties().get("x");
        float objectY = (float) ennemie.getObject().getProperties().get("y");
        float objectWidth = textureRegion.getRegionWidth();
        float objectHeight = textureRegion.getRegionHeight();
        int lifeBarWidth = ennemie.getCharacter().getPV();
        int lifeBarHeight = 1;
        float lifeBarX = objectX + (objectWidth - lifeBarWidth) / 2; 
        float lifeBarY = objectY + objectHeight + 5; // Place the life bar above the character
        Pixmap pixmap = new Pixmap(lifeBarWidth, lifeBarHeight, Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, lifeBarWidth, lifeBarHeight);
        Texture lifebar = new Texture(pixmap);
        pixmap.dispose();

        batch.setColor(1,0,0,1);
        batch.draw(lifebar,lifeBarX, lifeBarY, lifeBarWidth, lifeBarHeight);
        batch.setColor(1,1,1,1);
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
