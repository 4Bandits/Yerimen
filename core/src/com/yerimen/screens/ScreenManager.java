package com.yerimen.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Stack;

public class ScreenManager {
    private Stack<YerimenScreen> states;

    public ScreenManager(){
        states = new Stack<>();
    }

    public void push(YerimenScreen state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(YerimenScreen state){
        states.pop();
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb, ShapeRenderer sr){
        states.peek().render(sb, sr);
    }
}