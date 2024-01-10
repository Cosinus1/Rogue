// package com.mygdx.game.Graphic.Screen;

// import java.util.ArrayList;

// import com.badlogic.gdx.Gdx;
// import com.badlogic.gdx.Input.Keys;
// import com.badlogic.gdx.Screen;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.GL20;
// import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
// import com.badlogic.gdx.scenes.scene2d.InputEvent;
// import com.badlogic.gdx.scenes.scene2d.Stage;
// import com.badlogic.gdx.scenes.scene2d.ui.Table;
// import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
// import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
// import com.badlogic.gdx.utils.viewport.ScreenViewport;
// import com.mygdx.game.MyGame;
// import com.mygdx.game.Back.Inventory.Inventory;
// import com.mygdx.game.Back.Inventory.InventoryIteratorInterface;
// import com.mygdx.game.Back.Item.Item;
// import com.mygdx.game.Back.Item.ItemType;
// import com.mygdx.game.Graphic.Elements.CreateButton;
// import com.mygdx.game.Graphic.Elements.MySkin;
// import com.mygdx.game.Graphic.GraphicCharacter.GraphicHero;

// public class InventoryScreenSave implements Screen {
//     private final MyGame game;
//     private Stage stage;
//     private Table potionTable;
//     private Table weaponTable;

//     private GraphicHero hero;
//     private Inventory inventory;
//     private ShapeRenderer shapeRenderer;
//     private boolean showPotion; 
//     private boolean showWeapon;
//     private ArrayList<TextButton> potionList;
//     private ArrayList<TextButton> weaponList;
//     private ArrayList<TextButton> useList;
//     private CreateButton myButton;
//     private TextButton potionButton;
//     private TextButton weaponButton;
//     private MySkin mySkin;

    
//     public InventoryScreenSave(final MyGame game, GraphicHero hero){
//         this.game = game;
//         this.inventory = hero.getCharacter().getBag();
//         shapeRenderer = new ShapeRenderer();
//         this.stage = new Stage(new ScreenViewport());
//         potionTable = new Table();
//         weaponTable = new Table();
//         potionList = new ArrayList<>();
//         weaponList = new ArrayList<>();
//         useList = new ArrayList<>();
//         myButton = new CreateButton();
//         mySkin = new MySkin();
//         //on affiche les armes par default
//         showWeapon =true;
//         showPotion=false;

//         //création du bouton Arme
//         Color newNavy = new Color(0,50f/255f,1,1); 
//         weaponButton = new TextButton("Weapon", mySkin.createStyle(newNavy)); 
//         InventoryIteratorInterface<Item> iterator = inventory.getIterator(ItemType.WEAPON);
//         weaponList = myButton.createItemButton(iterator, mySkin, weaponTable, stage);
//         //création du bouton pour les potions
//         potionButton = new TextButton("Potion", mySkin.createStyle(Color.NAVY));
//         weaponButton.addListener(new MyClickListenerWeapon());
//         potionButton.addListener(new MyClickListenerPotion());
        
//         //positionnement des boutons
//         Table table = new Table();
//         table.add(weaponButton).padRight(5);
//         table.add(potionButton);
//         stage.addActor(table);
//         table.setPosition( 600,stage.getHeight()-30);
//     }

   

//     @Override
//     public void render(float delta) {
//         Gdx.gl.glClearColor(0, 0, 0, 1);
//         Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//         shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//             shapeRenderer.setColor(Color.WHITE);
//             shapeRenderer.rect(0,stage.getHeight()- 5, stage.getWidth(), 3);
//             shapeRenderer.rect(0,stage.getHeight()- 60, stage.getWidth(), 3);
//             shapeRenderer.rect(598,stage.getHeight()- 60, 4,56 );
//         shapeRenderer.end();

//         stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
//         stage.draw();

//         if(Gdx.input.isKeyJustPressed(Keys.E)){
//             game.setScreen(game.gameScreen);
//         }

//     }


//     // public void showItems(SpriteBatch batch, ItemType type){
//     //     int i=0;
//     //     InventoryIteratorInterface<Item> iterator = inventory.getIterator(type);
//     //     while(iterator.hasnext()){
//     //         Item item = iterator.next();
//     //         if(type == ItemType.POTION){
//     //             font.draw(batch,"Potion /" + iterator.getPosition() , 10, stage.getHeight()-70 - i );
//     //         }
//     //         else if(type == ItemType.WEAPON){
//     //             font.draw(batch,"Arme /" + iterator.getPosition() , 10, stage.getHeight()-70 - i );
//     //         }
//     //         i+=30;
//     //     }
//     // }




//     @Override
//     public void resize(int width, int height) {
//     }


//     private class MyClickListenerWeapon extends ClickListener{
 
//         @Override
//         public void clicked(InputEvent event, float x, float y) {
//             if(!showWeapon){
//                 showWeapon = true;
//                 showPotion = false;
//                 //On efface les boutons correspondant aus potions
//                 potionTable.clear();
//                 //On change la couleur du bouton d'arme
//                 Color newNavy = new Color(0,0.1f,1,1 );
//                 myButton.changeColor(mySkin, weaponButton, newNavy);
//                 //on change la couleur du bouton potion
//                 myButton.changeColor(mySkin, potionButton, Color.NAVY);
//                 //On créer les boutons correspondant aux items
//                 InventoryIteratorInterface<Item> iterator = inventory.getIterator(ItemType.WEAPON);
//                 weaponList = myButton.createItemButton(iterator, mySkin, weaponTable, stage);
//                 for(TextButton weaponButton2 : weaponList){
//                     weaponButton2.addListener(new MyClickListenerItemList(weaponButton2,myButton));
//                 }
//             }
//         }
        
//     }

//     private class MyClickListenerPotion extends ClickListener{

//         @Override
//         public void clicked(InputEvent event, float x, float y) {
//             if(!showPotion){
//                 showPotion = true;
//                 showWeapon = false;
//                 //On efface les bouttons correspondants aux armes
//                 weaponTable.clear();

//                 Color newNavy = new Color(0,0.1f,1,1 );
//                 //On change la couleur du bouton d'arme
//                 myButton.changeColor(mySkin, weaponButton, Color.NAVY);
//                 //on change la couleur du bouton potion
//                 myButton.changeColor(mySkin, potionButton, newNavy);

//                 //On créer les boutons de rendu
//                 InventoryIteratorInterface<Item> iterator = inventory.getIterator(ItemType.POTION);
//                 potionList = myButton.createItemButton(iterator, mySkin, potionTable, stage);
//                 for(TextButton potionButton2 : potionList){
//                     potionButton2.addListener(new MyClickListenerItemList(potionButton2,myButton));
//                 }
//             }
//         }
//     }  

//     private class MyClickListenerItemList extends ClickListener{
//         private MySkin mySkin;
//         private CreateButton myButton;
//         private TextButton Button;

//         public MyClickListenerItemList( TextButton Button, CreateButton myButton){
//             this.Button = Button;
//             this.myButton = myButton;
//             mySkin = new MySkin();
//         }

//         @Override
//         public void clicked(InputEvent event, float x, float y) {
//             //change la couleur et le text du bouton
//             Color newGreen = new Color(0,1,0,0.7f );
//             myButton.changeTexte(mySkin, Button,"USE");
//             myButton.changeColor(mySkin, Button, newGreen);
//             //met a jour les list
//             useList.add(Button);
//             for(TextButton useButton : useList){
//                 useButton.addListener(new ClickListener(){
//                     @Override
//                     public void clicked(InputEvent event, float x, float y) {
//                         //System.out.println("indice de l'Item utilisé" +  );
//                         stage.getActors().removeValue(Button, true);
//                         useList.remove(Button);
//                         InventoryIteratorInterface<Item> iterator = inventory.getIterator(ItemType.POTION);
//                         potionList = myButton.createItemButton(iterator, mySkin, potionTable, stage);
//                     }
//                 });
//             }
//         }
//     }
//         @Override
//     public void pause() {
//     }
//     @Override
//     public void resume() {
//     }
//      @Override
//     public void show() {
//         Gdx.input.setInputProcessor(stage);
//     }
//     @Override
//     public void hide() {
//         Gdx.input.setInputProcessor(null);
//     }
//     @Override
//     public void dispose() {
//     }

// }   
