package com.vyazankin.game.base;

import com.badlogic.gdx.Game;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * Класс, описывающий базовый экран игры
 */
public abstract class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    //Размер экрана в пикселях
    protected Rect screenBoundsPX;

    //Мировая(игровая) система координат (-1..1 / proportion)
    protected Rect worldBounds;

    //OpenGL система координат 0..2f/0..2f
    protected Rect openGLBounds;

    //Матрица перевода из игровых координат в OpenGL для отображения
    protected Matrix4 world2openGLTransition;

    //Матрица перевода экранных координат в игровые
    protected Matrix3 screen2worldTransition;

    //Вектор последней операции touch/drag в мировой системе координат
    protected Vector2 worldTouchPosition;

    protected Set<InputListener> inputListeners = new LinkedHashSet<>();
    //protected Set<BaseSprite> sprites = new LinkedHashSet<>();

    //В спрайт-пул по умолчанию добавляются спрайты фона и другие одиночные спрайты
    private BaseSpritePool<BaseSprite> defaultSpritePool;

    //Спрайт-пулы конкретных объектов
    private List<BaseSpritePool<? extends BaseSprite>> baseSpritePoolList;

    //Ссылка на класс игры
    protected Game game;

    private BaseScreen(){
        throw new UnsupportedOperationException("Запрещен вызов конструктора экрана без параметров!");
    }

    public BaseScreen(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    /*------------------------------------------Секция работы со спрайтами-----------------------------*/

    /**
     * Добавление спрайта в пул по умолчанию
     * @param sprite
     */
    public void addSpriteToDefaultPool(BaseSprite sprite) {
        defaultSpritePool.addSpriteIntoActive(sprite);
    }

    public void addSpriteToDefaultPool(BaseSprite sprite, boolean active) {
        defaultSpritePool.addSpriteIntoActive(sprite);
        sprite.setActive(active);
    }

    /**
     * Исключение спрайта из пула по умолчанию (включая исключение из листнеров!)
     */
    public void removeSprite(BaseSprite sprite) {
        defaultSpritePool.removeSprite(sprite);
        if (sprite instanceof InputListener){
            removeInputListener((InputListener) sprite);
        }
    }

    /*------------------------------------------Секция работы с пулами -----------------------------*/
    public void addSpritePool(BaseSpritePool pool){
        baseSpritePoolList.add(pool);
        if (worldBounds != null) {
            pool.worldResize(worldBounds);
            System.out.println("WB -> pool");
        }
    }


    public void removeSpritePool(BaseSpritePool pool){
        baseSpritePoolList.remove(pool);
    }


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

        //Для хранения спрайтов, не требующих повторного использования
        defaultSpritePool = new BaseSpritePool<BaseSprite>() {
            @Override
            protected BaseSprite obtainSprite() {
                throw new UnsupportedOperationException("Запрещено динамическое добавление спрайтов в пул по умолчанию!");
            }
        };

        //Для хранения пулов спрайтов
        baseSpritePoolList = new ArrayList<>();
        addSpritePool(defaultSpritePool);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.03f, 0.03f, 0.03f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Если вызвали раньше ресайза - выход
        if (worldBounds == null) return;

        //Запускаем событие рекалка для экранов - наследников
        recalc(delta);

        //Пересчитываем пулы
        for (BaseSpritePool pool: baseSpritePoolList) {
            pool.recalc(delta);
        }

        //Отрисовываем пулы
        batch.begin();
        for (BaseSpritePool pool: baseSpritePoolList) {
            pool.draw(batch);
        }
        batch.end();

    }

    /**
     * Может быть перегружено у наследников для создания дополнительной логики
     * @param deltaTime
     */
    protected void recalc(float deltaTime){}

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

        //Команда спрайтам в пулах
        for (BaseSpritePool pool: baseSpritePoolList) {
            pool.worldResize(worldBounds);
        }
        //defaultSpritePool.worldResize(worldBounds);
    }

    /**
     * Получение текущих границ экрана
     */
    public Rect getActualWorldBound(){
        return worldBounds;
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
        //Диспозим спрайты
        for (BaseSpritePool pool : baseSpritePoolList){
            pool.dispose();
        }
        batch.dispose();
    }


    /*-------------------------------Секция перехвата input - событий-------------------------------------------*/

    //Добавление слушателя в пул подписчиков
    public void addInputListener(InputListener listener){
        inputListeners.add(listener);
    }

    //Исключение слушателя из пула подписчиков
    public void removeInputListener(InputListener listener) {
        inputListeners.remove(listener);}


    @Override
    public boolean keyDown(int keycode) {
        if (!inputListeners.isEmpty()){
            for (InputListener l: inputListeners) {
                l.keyDown(keycode);
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode){
        if (!inputListeners.isEmpty()){
            for (InputListener l: inputListeners) {
                l.keyUp(keycode);
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (!inputListeners.isEmpty()){
            for (InputListener l: inputListeners) {
                l.keyTyped(character);
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        worldTouchPosition.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screen2worldTransition);
        if (!inputListeners.isEmpty()){
            for (InputListener l: inputListeners) {
                //Проверка на тачдаун
                if (l.isTouchDownInBounds(worldTouchPosition)) l.touchDown(worldTouchPosition, pointer, button);
            }
        }
        return screenWorldTouchDown(worldTouchPosition, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button){
        worldTouchPosition.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screen2worldTransition);
        if (!inputListeners.isEmpty()){
            for (InputListener l: inputListeners) {
                //Проверка на тачап
                if (l.isTouchUpInBounds(worldTouchPosition))l.touchUp(worldTouchPosition, pointer, button);
            }
        }
        return screenWorldTouchUp(worldTouchPosition, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        worldTouchPosition.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screen2worldTransition);
        if (!inputListeners.isEmpty()){
            for (InputListener l: inputListeners) {
                //Проверка на тачдаун
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
    protected void worldResize(Rect bounds){}



}
