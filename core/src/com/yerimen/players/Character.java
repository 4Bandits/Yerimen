package com.yerimen.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.json.PlayerJsonBuilder;
import com.yerimen.textures.PlayerTexture;
import org.json.JSONObject;

public class Character {

    private String characterID;
    private CharacterStatus status;
    protected PlayerTexture playerTexture;
    private String direction;
    private Sprite sprite;
    private TextureRegion currentFrame;
    protected float stateTime;
    protected boolean isMoving;

    public Character(String characterID, PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position) {
        this.characterID = characterID;
        this.sprite = new Sprite(playerTexture.getTexture());
        this.playerTexture = playerTexture;
        this.sprite.setPosition(position.x, position.y);
        this.status = playerStatus;
        this.direction = "left";
        this.currentFrame = playerTexture.getStandFrontAnimation().getKeyFrame(0, true);
        this.isMoving = false;
    }

    public String getCharacterID(){ return characterID; }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Color color) {
        spriteBatch.draw(currentFrame, sprite.getX(), sprite.getY());
        renderOnMiniMap(spriteBatch, shapeRenderer, color);
    }

    private void renderOnMiniMap(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Color color) {
        spriteBatch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        Vector2 v2 = this.getPosition();
        float x = 10 + (v2.x / 25);
        float y = 10 + (v2.y / 25);
        shapeRenderer.rect(x, y, 5, 5);
        shapeRenderer.end();

        spriteBatch.begin();
    }

    public Vector2 getPosition() {
        return new Vector2(this.sprite.getX(), this.sprite.getY());
    }

    public CharacterStatus getStatus() {
        return status;
    }

    public String getName() {
        return this.playerTexture.getName();
    }

    public String getDirection() {
        return this.direction;
    }

    public Character setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public Character move(float x, float y) {
        sprite.setPosition(x, y);
        return this;
    }

    public Character setMoving(boolean moving) {
        isMoving = moving;
        return this;
    }

    public float getXPosition() {
        return sprite.getX();
    }

    public float getYPosition() {
        return sprite.getY();
    }

    public void setCharacterID(String characterID) {
        this.characterID = characterID;
    }

    JSONObject toJson() {
        return new PlayerJsonBuilder(this).build();
    }

    public Character translate(float x, float y) {
        sprite.translate(x, y);
        return this;
    }

    public void setCurrentFrame(TextureRegion frame) {
        currentFrame = frame;
    }

    public void update(float delta, OrthographicCamera camera) {
        stateTime += delta;
        if (isMoving) {//todo poner direcciones en un enum o algo asi
            if (direction.equals("up")) {
                setCurrentFrame(playerTexture.getWalkBackAnimation().getKeyFrame(stateTime, true));
            }
            if (direction.equals("right")) {
                setCurrentFrame(playerTexture.getWalkRightAnimation().getKeyFrame(stateTime, true));
            }
            if (direction.equals("down")) {
                setCurrentFrame(playerTexture.getWalkFrontAnimation().getKeyFrame(stateTime, true));
            }
            if (direction.equals("left")) {
                setCurrentFrame(playerTexture.getWalkLeftAnimation().getKeyFrame(stateTime, true));
            }
            setMoving(false);
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(getXPosition() + 22, getYPosition(), 30, 65);
    }

    public void isAttacked(){
        // Restar vida - quizas pasar el poder para saber cuanta vida sacar
    }

}
