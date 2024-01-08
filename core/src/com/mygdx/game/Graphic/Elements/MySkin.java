package com.mygdx.game.Graphic.Elements;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MySkin {
    public Skin createWhiteSkin(){
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);

        Pixmap pixmap = new Pixmap(200, 50, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("buttonBackground", new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font; // Définir la police pour le bouton
        textButtonStyle.up = skin.newDrawable("buttonBackground", Color.WHITE); // Utiliser la texture ou Pixmap pour le visuel du bouton
        skin.add("White", textButtonStyle); // Ajouter le style "default" pour les TextButton

        return skin;
    }

    public Skin createStyle(Color color){
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();
        font.setColor(color);

        Pixmap navyBluePixmap = new Pixmap(600, 50, Format.RGBA8888);
        navyBluePixmap.setColor(color); // Couleur bleu marine
        navyBluePixmap.fill();
        skin.add("buttonBackground", new Texture(navyBluePixmap));

        TextButton.TextButtonStyle customButtonStyle = new TextButton.TextButtonStyle();
        customButtonStyle.font = font;
        customButtonStyle.up = skin.newDrawable("buttonBackground", color); // Utiliser le fond bleu marine
        skin.add("default", customButtonStyle);

        return skin;

    }

    public Skin createPotionSkin(){
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();

        font.setColor(Color.GRAY);

        Pixmap GrayPixmap = new Pixmap(600, 50, Format.RGBA8888);
        GrayPixmap.setColor(Color.GRAY); // Couleur bleu marine
        GrayPixmap.fill();
        skin.add("buttonBackground", new Texture(GrayPixmap));

        TextButton.TextButtonStyle customButtonStyle = new TextButton.TextButtonStyle();
        customButtonStyle.font = font;
        customButtonStyle.up = skin.newDrawable("buttonBackground", Color.GRAY); // Utiliser le fond bleu marine
        skin.add("Gray", customButtonStyle);

        return skin;
    }

}
