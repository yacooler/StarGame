package com.vyazankin.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.vyazankin.game.math.Rect;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSpritePool<T extends BaseSprite> {

    protected List<T> activeSprites;
    protected List<T> inactiveSprites;


    public BaseSpritePool() {
        activeSprites = new ArrayList<>();
        inactiveSprites = new ArrayList<>();
    }

    public List<T> getActiveSpritesList(){
        return activeSprites;
    }

    public List<T> getInactiveSpritesList(){
        return inactiveSprites;
    }

    private Rect actualWorldBound;

    /**
     * Функция создания нового спрайта, если его нет в пуле
     */
    protected abstract T obtainSprite();

    /**
     * Функция добавления спрайта в пул
     */
    public void addSpriteIntoActive(T sprite){
        //sprite.worldResize(actualWorldBound);
        activeSprites.add(sprite);
    }

    /**
     * Функция исключения спрайта из пула
     */
    public void removeSprite(T sprite){
        if (!activeSprites.remove(sprite)) inactiveSprites.remove(sprite);
    }

    /**
     * Должен быть вызван из метода render baseScreen
     */
    public void recalc(float deltaTime){
        if (actualWorldBound == null) return;

        for (T t : activeSprites){
            t.actualSpriteWorldBound = actualWorldBound;
            if (t.isActive) t.recalc(deltaTime);
        }
        checkInactiveSprites();
        afterPoolRecalc(deltaTime);
    };

    /**
     * Вызывается после пересчета пула
     */
    protected void afterPoolRecalc(float deltaTime){}

    /**
     * Должен быть вызван из метода render baseScreen
     */
    public void draw(Batch batch){
        for (T t : activeSprites){
            if (t.isActive) t.draw(batch);
        }
        afterPoolDraw(batch);
    }

    /**
     * Вызывается после перерисовки пула
     */
    protected void afterPoolDraw(Batch batch){}

    /**
     * Должен быть вызван из метода resize baseScreen
     */
    public void worldResize(Rect bounds){
        for (T t: activeSprites) {
            t.worldResize(bounds);
        }
        for (T t: inactiveSprites) {
            t.worldResize(bounds);
        }

        actualWorldBound = bounds;
    }

    /**
     * Получение спрайта из пула
     */
    public T poolNewOrInactiveSprite(){

        T sprite;
        if (inactiveSprites.isEmpty()){
            sprite = obtainSprite();
            sprite.actualSpriteWorldBound = actualWorldBound;
        } else {
            sprite = inactiveSprites.remove(inactiveSprites.size() - 1);
        }
        return sprite;
    }

    /**
     * Перевод спрайтов из пула активных в пул неактивных по свойству isActive
     */
    public void checkInactiveSprites(){
        if (activeSprites.isEmpty()) return;
        for(int i = 0; i < activeSprites.size(); i++){
            if (!activeSprites.get(i).isActive){
                inactiveSprites.add(activeSprites.get(i));
                activeSprites.remove(i);
                i--;
            }
        }
    }

    public void dispose(){
        //Вызывает диспоз на всех спрайтах пулов
        for (T sprite: activeSprites) {
            sprite.dispose();
        }

        for (T sprite: inactiveSprites) {
            sprite.dispose();
        }

        //чистим списки
        activeSprites.clear();
        inactiveSprites.clear();
        activeSprites = null;
        inactiveSprites = null;
    }



}
