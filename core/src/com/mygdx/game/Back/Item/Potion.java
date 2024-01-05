package com.mygdx.game.Back.Item;

public class Potion extends Item{
        private int pvSoigner;

        public Potion(ItemType t, int soin){
            super(t);
            this.pvSoigner=soin;
        }
        
        public int getPvSoigner(){
            return pvSoigner;
        }

        public void description(){
            System.out.println("potion de vie / +  "+pvSoigner+"PV");
        }
    }
