package com.mygdx.game.Graphic;

//import java.util.ArrayList;
//import java.util.Iterator;

//import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.GraphicCharacter;
//import com.mygdx.game.Graphic.GraphicObject.GraphicCharacter.GraphicEnnemie;
import com.mygdx.game.Graphic.World.Map.Map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
//import com.badlogic.gdx.maps.MapObject;
//import com.badlogic.gdx.maps.MapObjects;
//import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class RendererBW {

    private SpriteBatch spriteBatch;
    private ShaderProgram blackAndWhiteShader;
    private OrthogonalTiledMapRenderer mapRenderer;

    public RendererBW() {
        spriteBatch = new SpriteBatch();
        blackAndWhiteShader = createBlackAndWhiteShader();
    }

    private ShaderProgram createBlackAndWhiteShader() {
        // Shader code to convert colors to grayscale
        String vertexShaderCode =
                "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
                        "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
                        "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
                        "uniform mat4 u_projTrans;\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" +
                        "    v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" +
                        "    gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" +
                        "}\n";

        String fragmentShaderCode =
                "#ifdef GL_ES\n" +
                        "precision mediump float;\n" +
                        "#endif\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "uniform sampler2D u_texture;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    vec4 texColor = texture2D(u_texture, v_texCoords);\n" +
                        "    float gray = dot(texColor.rgb, vec3(0.2, 0.187, 0.014));\n" +
                        "    gl_FragColor = vec4(gray, gray, gray, texColor.a) * v_color;\n" +
                        "}\n";

        ShaderProgram shader = new ShaderProgram(vertexShaderCode, fragmentShaderCode);

        if (!shader.isCompiled()) {
            Gdx.app.log("ShaderError", shader.getLog());
        }

        return shader;
    }

    public void render(Map map, OrthographicCamera camera) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.setShader(blackAndWhiteShader);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        if (map.getTiledMap() != null) {
            camera.translate(map.getcamX(), map.getcamY());
            camera.update();
            mapRenderer = new OrthogonalTiledMapRenderer(map.getTiledMap(), spriteBatch);
            mapRenderer.setView(camera);
            mapRenderer.render();
            camera.translate(-map.getcamX(), -map.getcamY());

        }
        

    }
    /* 
    private void renderObjects(MapObjects objects, OrthographicCamera camera) {
        spriteBatch.begin();

        for (MapObject object : objects) {
            if (object instanceof TextureMapObject) {
                TextureMapObject textureObject = (TextureMapObject) object;
                TextureRegion textureRegion = textureObject.getTextureRegion();
                float objectX = (float) object.getProperties().get("x");
                float objectY = (float) object.getProperties().get("y");
                float objectWidth = textureRegion.getRegionWidth();
                float objectHeight = textureRegion.getRegionHeight();
                spriteBatch.draw(textureRegion, objectX, objectY, objectWidth, objectHeight);
            } else {
                Gdx.app.log("Error", "Object Texture not found");
            }
        }

        spriteBatch.end();
    }

    private void renderDeadObjects(MapObjects deadObjects, ArrayList<GraphicEnnemie> deadPNJs, OrthographicCamera camera) {
        spriteBatch.begin();

        Iterator<MapObject> iterator = deadObjects.iterator();
        Iterator<GraphicEnnemie> characterIterator = deadPNJs.iterator();

        while (iterator.hasNext() && characterIterator.hasNext()) {
            MapObject object = iterator.next();
            GraphicCharacter deadPNJ = characterIterator.next();

            if (deadPNJ.getDeathTimer() < 60) {
                if (object instanceof TextureMapObject) {
                    int timer = deadPNJ.getDeathTimer();
                    TextureRegion textureRegion = deadPNJ.getDeathTexture_List().get(timer);
                    float objectX = (float) object.getProperties().get("x");
                    float objectY = (float) object.getProperties().get("y");
                    float objectWidth = textureRegion.getRegionWidth();
                    float objectHeight = textureRegion.getRegionHeight();
                    spriteBatch.draw(textureRegion, objectX, objectY, objectWidth, objectHeight);
                } else {
                    Gdx.app.log("Error", "Object Texture not found");
                }
            } else {
                iterator.remove();
                characterIterator.remove();
            }
        }

        spriteBatch.end();
    }

    public void dispose() {
        spriteBatch.dispose();
        if (mapRenderer != null) {
            mapRenderer.dispose();
        }
    }
    */
}
