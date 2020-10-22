package com.vyazankin.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.math.MatrixUtils;
import com.vyazankin.game.math.Rect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    //Размер экрана в пикселях
    private Rect screenBoundsPX;

    //Мировая(игровая) система координат (-1..1 / proportion)
    private Rect worldBounds;

    //OpenGL система координат 0..2f/0..2f
    private Rect openGLBounds;

    //Матрица перевода из игровых координат в OpenGL для отображения
    private Matrix4 world2openGLTransition;

    //Матрица перевода экранных координат в игровые
    private Matrix3 screen2worldTransition;

    //Вектор последней операции touch/drag в мировой системе координат
    private Vector2 worldTouchPosition;

    private List<TouchListener> touchListeners = new LinkedList<>();

    @Override
    public void show() {
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        screenBoundsPX = new Rect();
        worldBounds = new Rect();
        openGLBounds = new Rect(0f, 0f, 1f, 1f);
        world2openGLTransition = new Matrix4();
        screen2worldTransition = new Matrix3();
        worldTouchPosition = new Vector2();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.03f, 0.03f, 0.03f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        //Срабатывает после show, получаем актуальные размеры экрана
        screenBoundsPX.setSize(width, height);
        screenBoundsPX.setLeft(0);
        screenBoundsPX.setBottom(0);

        //Соотношение сторон - высота всегда 1f, ширина всегда пропорциональна ширине устройства
        worldBounds.setSize(width / (float) height, 1f);
        MatrixUtils.calculateTransitionMatrix(world2openGLTransition, worldBounds, openGLBounds);
        batch.setProjectionMatrix(world2openGLTransition);

        MatrixUtils.calculateTransitionMatrix(screen2worldTransition, screenBoundsPX, worldBounds);

        worldResize(worldBounds);
    }

    @Override
    public void pause() {
        //doNothing
    }

    @Override
    public void resume() {
        //doNothing
    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        worldTouchPosition.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screen2worldTransition);
        if (!touchListeners.isEmpty()){
            for (TouchListener l: touchListeners) {
                if (l.isTouchDownInBounds(worldTouchPosition)) l.touchDown(worldTouchPosition, pointer, button);
            }
        }
        return screenWorldTouchDown(worldTouchPosition, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        worldTouchPosition.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screen2worldTransition);
        if (!touchListeners.isEmpty()){
            for (TouchListener l: touchListeners) {
                if (l.isTouchUpInBounds(worldTouchPosition))l.touchUp(worldTouchPosition, pointer, button);
            }
        }
        return screenWorldTouchUp(worldTouchPosition, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        worldTouchPosition.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screen2worldTransition);
        if (!touchListeners.isEmpty()){
            for (TouchListener l: touchListeners) {
                if (l.isTouchDownInBounds(worldTouchPosition)) l.drag(worldTouchPosition, pointer);
            }
        }
        return screenWorldTouchDragged(worldTouchPosition, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /**
     *Обработка события нажатия кнопки/пада в мировой(игровой) системе координат
     */
    public boolean screenWorldTouchDown(Vector2 worldTouchPosition, int pointer, int button){
        return false;
    }

    /**
     *Обработка события отпускания кнопки/пада в мировой(игровой) системе координат
     */
    public boolean screenWorldTouchUp(Vector2 worldTouchPosition, int pointer, int button) {
        return false;
    }

    /**
     * Обработка события протягивания в мировой(игровой) системе координат
     */
    public boolean screenWorldTouchDragged(Vector2 worldTouchPosition, int pointer) {
        return false;
    }


    /**
     * Событие ресайз в мировой(игровой) системе координат
     */
    public void worldResize(Rect bounds){

    }

    public void addTouchListener(TouchListener listener){
        touchListeners.add(listener);
    }

    public void removeTouchListener(TouchListener listener) {touchListeners.remove(listener);}
}
