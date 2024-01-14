package com.mygdx.game.Graphic.Decorator;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MySkin {

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

    public Skin createMerchantSkin(){
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();

        font.setColor(Color.BLACK);

        Pixmap GrayPixmap = new Pixmap(370, 25, Format.RGBA8888);
        GrayPixmap.setColor(Color.NAVY); // Couleur bleu marine
        GrayPixmap.fill();
        skin.add("buttonMerchant", new Texture(GrayPixmap));

        TextButton.TextButtonStyle customButtonStyle = new TextButton.TextButtonStyle();
        customButtonStyle.font = font;
        customButtonStyle.up = skin.newDrawable("buttonMerchant", Color.GRAY); // Utiliser le fond bleu marine
        skin.add("default", customButtonStyle);

        return skin;
    }

    public Skin createBuyButton(){
        Skin skin = new Skin();
        BitmapFont font = new BitmapFont();

        font.setColor(Color.BLACK);

        Pixmap GrayPixmap = new Pixmap(100, 50, Format.RGBA8888);
        GrayPixmap.setColor(Color.RED); // Couleur bleu marine
        GrayPixmap.fill();
        skin.add("buybutton", new Texture(GrayPixmap));

        TextButton.TextButtonStyle customButtonStyle = new TextButton.TextButtonStyle();
        customButtonStyle.font = font;
        customButtonStyle.up = skin.newDrawable("buybutton", Color.RED); // Utiliser le fond bleu marine
        skin.add("default", customButtonStyle);

        return skin;
    }

}
