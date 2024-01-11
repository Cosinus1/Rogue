package com.mygdx.game.Back.Item;

public class Booster extends Item{
        private int typeboost; //0 pour potion de damage, 1 pour def et 2 pour les deux
        private int boostDam;
        private int boostDef;

        public Booster(int boostDam, int boostDef, int boostRange, int type, String name){
            super(ItemType.BOOSTER, name);
            this.typeboost = type;
            this.boostDam = boostDam;
            this.boostDef = boostDef;
        }

        public int getTypeBoost(){
            return typeboost;
        }

        public int getBoostDam(){
            return boostDam;
        }
        public int getBoostDef(){
            return boostDef;
        }

        public void description(){
            System.out.println("booster / +  "+boostDam + "/"+ boostDef);
        }

    }