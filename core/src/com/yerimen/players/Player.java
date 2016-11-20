package com.yerimen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.yerimen.powers.FireBall;
import com.yerimen.powers.Power;
import com.yerimen.server.Observable;
import com.yerimen.textures.PlayerTexture;
import com.yerimen.textures.TextureManager;

public class Player extends Character implements Observable {
    TiledMapTileLayer collisionObjectLayer ;
    public Player(PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position) {
        super(playerTexture, playerStatus, position);
        collisionObjectLayer =(TiledMapTileLayer)TextureManager.getInstance().getMap().getLayers().get("Blocking prueba");
    }

    @Override
    public void update(float delta, OrthographicCamera camera) {
        stateTime += delta;
        detectCollisionsWithMapObjects();
        processMove();

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
        return cell!=null && cell.getTile()!=null && cell.getTile().getProperties().containsKey("block");
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
        //int objectLayerId = 3;
        //Rectangle rectPlayer = new Rectangle();
        //rectPlayer.set(getXPosition(),getYPosition(),getSprite().getWidth(),getSprite().getHeight());
        //TiledMapTileLayer collisionObjectLayer =(TiledMapTileLayer)TextureManager.getInstance().getMap().getLayers().get(objectLayerId);
       // MapObjects objects = collisionObjectLayer.getObjects();
        float oldX=getXPosition();
        float oldY=getYPosition();
        boolean collision=false;

        if(isHeadingUpAndRight()){
            collision= isCellBlocked(getXPosition()+getSprite().getWidth(),getYPosition()+getSprite().getHeight());
        }
        if(isHeadingUpAndLeft()){
           collision= isCellBlocked(getXPosition(),getYPosition()+getSprite().getHeight());
        }
        if(isHeadingDownAndRight()){
            collision= isCellBlocked(getXPosition()+getSprite().getWidth(),getYPosition());
        }
        if(isHeadingDownAndLeft()){
            collision= isCellBlocked(getXPosition(),getYPosition());
        }
        if(isHeadingUp()){
            collision= isCellBlocked(getXPosition()+getSprite().getWidth()/2,getYPosition()+getSprite().getHeight());
        }
        if(isHeadingDown()){
            collision= isCellBlocked(getXPosition()+getSprite().getWidth()/2,getYPosition());
        }
        if(isHeadingLeft()){
            collision= isCellBlocked(getXPosition(),getYPosition()+getSprite().getHeight()/2);
        }
        if(isHeadingRight()){
            collision= isCellBlocked(getXPosition()+getSprite().getWidth(),getYPosition()+getSprite().getHeight()/2);
        }

        if(collision){
            this.getSprite().setPosition(oldX,oldY);
        }
        /*
        //TiledMapTileLayer.Cell object;
        //Rectangle rect = new Rectangle();
        for (int y = 0; y <= collisionObjectLayer.getHeight(); y++) {
            for (int x = 0; x <= collisionObjectLayer.getWidth(); x++) {
                TiledMapTileLayer.Cell cell = collisionObjectLayer.getCell(x, y);
                if (cell != null) {
                   rect.set(x,y,1,1);
                    if(rect.overlaps(getSprite().getBoundingRectangle())){
                        getSprite().setPosition(1,1);
                    }
                }
            }
        }

*/



        /*switch (getDirection()){
            case "up":
                rect.set(getSprite().getX(),getSprite().getY()+1,1,1);

            case "down":
                rect.set(getSprite().getX(),getSprite().getY()-1,1,1);

            case "left":
                rect.set(getSprite().getX()-1,getSprite().getY(),1,1);
            case "right":
                rect.set(getSprite().getX()+1,getSprite().getY(),1,1);
        }
         if(getSprite().getBoundingRectangle().overlaps(rect)){
             this.getSprite().setX(0);
             this.getSprite().setY(0);
         }
*/
        /*for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();

            if (rectangle.overlaps(rectPlayer)) {

               this.getSprite().setX(0);
                this.getSprite().setY(0);

            }

        }*/
    }

}
