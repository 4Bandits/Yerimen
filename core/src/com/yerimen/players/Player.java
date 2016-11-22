package com.yerimen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.yerimen.powers.FireBall;
import com.yerimen.powers.Power;
import com.yerimen.server.Observable;
import com.yerimen.textures.PlayerTexture;
import com.yerimen.textures.TextureManager;

public class Player extends Character implements Observable {
    TiledMapTileLayer collisionObjectLayer ;
    float tileHeight, tileWidth;
    float oldX, oldY;
    public Player(PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position) {
        super(playerTexture, playerStatus, position);
        collisionObjectLayer =(TiledMapTileLayer)TextureManager.getInstance().getMap().getLayers().get("Blocking prueba");
        tileHeight=collisionObjectLayer.getTileHeight();
        tileWidth=collisionObjectLayer.getTileWidth();
         oldX=getXPosition();
         oldY=getYPosition();
    }

    @Override
    public void update(float delta, OrthographicCamera camera) {
        stateTime += delta;
        processMove();
        detectCollisionsWithMapObjects();

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 mousePosition = this.getMousePosition(camera, Gdx.input.getX(), Gdx.input.getY());
            this.attack(mousePosition);
        }

        if (this.isTakenDamage()) {

        }
    }

    private void processMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.translate(0, 1).setDirection("up");
            this.notify(this.toJson());
            setCurrentFrame(playerTexture.getWalkBackAnimation().getKeyFrame(stateTime, true));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.translate(1, 0).setDirection("right");
            this.notify(this.toJson());
            setCurrentFrame(playerTexture.getWalkRightAnimation().getKeyFrame(stateTime, true));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.translate(0, -1).setDirection("down");
            this.notify(this.toJson());
            setCurrentFrame(playerTexture.getWalkFrontAnimation().getKeyFrame(stateTime, true));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.translate(-1, 0).setDirection("left");
            this.notify(this.toJson());
            setCurrentFrame(playerTexture.getWalkLeftAnimation().getKeyFrame(stateTime, true));
        }
    }

    private void attack(Vector3 vector3) {
        Vector2 vector2 = new Vector2(vector3.x, vector3.y);
        Float distance = this.getPosition().dst(vector2);
        Power power = new FireBall(distance, vector2, this.getPosition());
        this.notify(power);
    }

    private boolean isTakenDamage() {
        return false;
    }

    private Vector3 getMousePosition(OrthographicCamera camera, float x, float y) {
        Vector3 vector3 = new Vector3();
        vector3.set(x, y, 0);
        return camera.unproject(vector3);
    }
    private boolean isCellBlocked(float x, float y){
        TiledMapTileLayer.Cell cell= collisionObjectLayer.getCell((int)(x/collisionObjectLayer.getTileWidth()),(int)(y/collisionObjectLayer.getTileHeight()));
        return cell!=null && cell.getTile()!=null && cell.getTile().getProperties().containsKey("block")&&(boolean)cell.getTile().getProperties().get("block");
    }
    private boolean isHeadingUpAndRight() {
        return (Gdx.input.isKeyPressed(Input.Keys.UP) ||
                Gdx.input.isKeyPressed(Input.Keys.W)) &&
                (!(Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                        Gdx.input.isKeyPressed(Input.Keys.A))) &&
                ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                        Gdx.input.isKeyPressed(Input.Keys.D)));
    }
    private boolean isHeadingUpAndLeft() {
        return (Gdx.input.isKeyPressed(Input.Keys.UP) ||
                Gdx.input.isKeyPressed(Input.Keys.W)) &&
                ((Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                        Gdx.input.isKeyPressed(Input.Keys.A)) &&
                        !((Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                                Gdx.input.isKeyPressed(Input.Keys.D))));
    }
    private boolean isHeadingDownAndRight() {
        return (Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                Gdx.input.isKeyPressed(Input.Keys.S)) &&
                !(Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                        Gdx.input.isKeyPressed(Input.Keys.A)) &&
                ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                        Gdx.input.isKeyPressed(Input.Keys.D)));
    }
    private boolean isHeadingDownAndLeft() {
        return (Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                Gdx.input.isKeyPressed(Input.Keys.S)) &&
                (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                        Gdx.input.isKeyPressed(Input.Keys.A)) &&
                !((Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                        Gdx.input.isKeyPressed(Input.Keys.D)));
    }
    private boolean isHeadingUp() {
        return (Gdx.input.isKeyPressed(Input.Keys.UP) ||
                Gdx.input.isKeyPressed(Input.Keys.W)) &&
                !(Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                        Gdx.input.isKeyPressed(Input.Keys.A)) &&
                !((Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                        Gdx.input.isKeyPressed(Input.Keys.D)));
    }
    private boolean isHeadingDown() {
        return (Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                Gdx.input.isKeyPressed(Input.Keys.S)) &&
                !(Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                        Gdx.input.isKeyPressed(Input.Keys.A)) &&
                !((Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                        Gdx.input.isKeyPressed(Input.Keys.D)));
    }
    private boolean isHeadingLeft() {
        return (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
                Gdx.input.isKeyPressed(Input.Keys.A)) &&
                !(Gdx.input.isKeyPressed(Input.Keys.UP) ||
                        Gdx.input.isKeyPressed(Input.Keys.W)) &&
                !((Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                        Gdx.input.isKeyPressed(Input.Keys.S)));
    }
    private boolean isHeadingRight() {
        return (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
                Gdx.input.isKeyPressed(Input.Keys.D)) &&
                !(Gdx.input.isKeyPressed(Input.Keys.UP) ||
                        Gdx.input.isKeyPressed(Input.Keys.W)) &&
                !((Gdx.input.isKeyPressed(Input.Keys.DOWN) ||
                        Gdx.input.isKeyPressed(Input.Keys.S)));
    }
    protected void detectCollisionsWithMapObjects() {
        int objectLayerId = 3;
        Rectangle rectPlayer = new Rectangle();
        Rectangle rect = new Rectangle();
        rectPlayer.set(getXPosition(),getYPosition(),getSprite().getWidth(),getSprite().getHeight());
        //TiledMapTileLayer collisionObjectLayer =(TiledMapTileLayer)TextureManager.getInstance().getMap().getLayers().get(objectLayerId);

        MapObjects objects = collisionObjectLayer.getObjects();
        if (isCellBlocked(getXPosition(),getYPosition())){
            this.getSprite().setPosition(500,500);
        }
/*      */

        //TiledMapTileLayer.Cell object;
        //Rectangle rect = new Rectangle();
       /* for (int y = 0; y <= collisionObjectLayer.getHeight(); y++) {
            for (int x = 0; x <= collisionObjectLayer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = collisionObjectLayer.getCell(x, y);
                if (isCellBlocked(x,y) && isCollidingWithCellIn(x,y) ) {
                   /*rect.set(x,y,tileHeight,tileWidth);
                    if(rect.overlaps(rectPlayer)){
                        getSprite().setPosition(500,500);
                    }
                }
            }
        }*/

        /*for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();

            if (rectangle.overlaps(rectPlayer)) {

               this.getSprite().setPosition(0,0);

            }

        }*/
    }

    private boolean isCollidingWithCellIn(float x, float y){

        boolean collision=false;

        if(isHeadingUpAndRight()){
            collision= getXPosition()+tileWidth ==x && getYPosition()+tileHeight ==y;
        }
        if(isHeadingUpAndLeft()){
            collision= getXPosition()-tileHeight ==x && getYPosition()+tileHeight ==y;
        }
        if(isHeadingDownAndRight()){
            collision= getXPosition()+tileWidth ==x && getYPosition()-tileHeight ==y;
        }
        if(isHeadingDownAndLeft()){
            collision= getXPosition()+tileWidth ==x && getYPosition()+tileHeight ==y;
        }
        if(isHeadingUp()){
            collision= getXPosition() ==x && getYPosition()+tileHeight ==y;
        }
        if(isHeadingDown()){
            collision= getXPosition() ==x && getYPosition()-tileHeight ==y;
        }
        if(isHeadingLeft()){
            collision= getXPosition()-tileWidth ==x && getYPosition() ==y;
        }
        if(isHeadingRight()){
            collision= getXPosition()+tileWidth ==x && getYPosition() ==y;
        }

        return collision;
    }

}
