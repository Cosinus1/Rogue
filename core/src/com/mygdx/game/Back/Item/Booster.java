package com.mygdx.game.Back.Item;

public class Booster extends Item{
        private int boostDam;
        private int boostDef;
        private int boostRange;

        public Booster(int boostDam, int boostDef, int boostRange){
            super(ItemType.BOOSTER);
            this.boostDam = boostDam;
            this.boostDef = boostDef;
            this.boostRange = boostRange;
        }

        public int getBoostDam(){
            return boostDam;
        }
        public int getBoostDef(){
            return boostDef;
        }
        public int getBoostRange(){
            return boostRange;
        }

        public void description(){
            System.out.println("booster / +  "+boostDam + "/"+ boostDef+"/"+boostRange);
        }

    }