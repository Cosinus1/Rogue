package com.mygdx.game.Graphic.Sound;

public class Volume {
    private static Volume instance;
    private float value;

    private Volume(){
        this.value = 100;
        instance = this;
    }

    public static Volume getV(){
        if (instance == null){
            instance = new Volume();
        }
        return instance;
    }
    public static float get(){
        if (instance == null){
            instance = new Volume();
        }
        return instance.value;
    }
    public static String getString(){
        float volume = instance.value;
        return String.format("%f ", volume);
    }
    public void set(float value){
        instance.value = value;
    }
    
    public void add(float value){
        instance.value += value;
    }

}
