package com.yerimen.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.yerimen.bars.HealthBar;
import com.yerimen.json.PlayerJsonBuilder;
import com.yerimen.powers.Power;
import com.yerimen.screens.game.GameContent;
import com.yerimen.textures.PlayerTexture;
import org.json.JSONObject;

public class Character {

    private  String userName;
    private String characterID;
    private CharacterStatus status;
    protected PlayerTexture playerTexture;
    private String direction;
    protected Sprite sprite;
    private TextureRegion currentFrame;
    protected float stateTime;
    protected boolean isMoving;
    protected HealthBar healthBar;
    protected Vector2 respawndPoint;

    public Character(String characterID, PlayerTexture playerTexture, CharacterStatus playerStatus, Vector2 position) {
        this.characterID = characterID;
        this.sprite = new Sprite(playerTexture.getTexture());
        this.playerTexture = playerTexture;
        this.sprite.setPosition(position.x, position.y);
        this.status = playerStatus;
        this.direction = "left";
        this.currentFrame = playerTexture.getStandFrontAnimation().getKeyFrame(0, true);
        this.isMoving = false;
        this.healthBar = new HealthBar(position.x, position.y, 5, 70);
        this.respawndPoint = position;
    }

    public String getId() {
        return characterID;
    }

    public void render(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer, Color color) {
        spriteBatch.draw(currentFrame, sprite.getX(), sprite.getY());
        renderOnMiniMap(spriteBatch, shapeRenderer, color);
        healthBar.render(spriteBatch);
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

    JSONObject toJson() {
        return new PlayerJsonBuilder(this).build();
    }

    public Character translate(float x, float y) {
        float x1 = Math.min(Math.max(sprite.getX() + x, 0), 3120);
        float y2 = Math.min(Math.max(sprite.getY() + y, 30), 3110);

        sprite.setPosition(x1, y2);
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
        healthBar.update(getStatus().getHp(), getXPosition(), getYPosition() + 70);
    }

    public Rectangle getBounds() {
        return new Rectangle(getXPosition() + 22, getYPosition(), 30, 65);
    }

    public void isAttacked(Power power,GameContent gameContent) {
        getStatus().subtractHp(power.getDamage());
        if(this.isDead()){
            gameContent.registerKillOfTo(power.getCharacterID(),this);
            this.goToRespawnPoint();
            this.respawn();
        }
    }

    private boolean isDead(){
        return this.getStatus().getHp() <= 0;
    }

    protected void goToRespawnPoint(){
        this.sprite.setPosition(this.respawndPoint.x, this.respawndPoint.y);
    }

    private void respawn(){
        this.getStatus().respawnd();
    }

}
