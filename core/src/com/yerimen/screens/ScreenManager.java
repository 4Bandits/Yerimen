package com.yerimen.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Stack;

public class ScreenManager {

    private Stack<YerimenScreen> states;

    public ScreenManager(){
        this.states = new Stack<>();
    }

    public void push(YerimenScreen state){
        this.states.push(state);
    }

    public void pop(){
        this.states.pop();
    }

    public void set(YerimenScreen state){
        this.pop();
        this.push(state);
    }

    public void update(float deltaTime){
        this.states.peek().update(deltaTime);
    }

    public void render(SpriteBatch spriteBatch, SpriteBatch hudBatch, ShapeRenderer shapeRenderer){
        this.states.peek().render(spriteBatch, hudBatch, shapeRenderer);
    }

}