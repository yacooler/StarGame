package com.vyazankin.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.vyazankin.game.math.Rect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class BaseSpritePool<T extends BaseSprite> {

    //HashSet гарантирует, что мы не добавим спрайт в одно множество несколько раз
    protected HashSet<T> activeSprites;
    protected HashSet<T> inactiveSprites;


    public BaseSpritePool() {
        //Linked т.к. важна очередность спрайтов
        activeSprites = new LinkedHashSet<>();
        inactiveSprites = new LinkedHashSet<>();
    }

    public HashSet<T> getActiveSpritesList(){
        return activeSprites;
    }

    public HashSet<T> getInactiveSpritesList(){
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
        //Удалим из неактивных, если он там был
        removeSprite(sprite);
        activeSprites.add(sprite);
    }

    /**
     * Функция исключения спрайта из пула
     */
    public void removeSprite(T sprite){
        if (!inactiveSprites.remove(sprite)) activeSprites.remove(sprite);
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

        T sprite = null;

        if (inactiveSprites.isEmpty()){
            sprite = obtainSprite();
            sprite.actualSpriteWorldBound = actualWorldBound;
        } else {
            Iterator<T> iterator = inactiveSprites.iterator();
            if (iterator.hasNext()) {
                sprite = iterator.next();
                iterator.remove();
            }

        }
        return sprite;
    }

    /**
     * Перевод спрайтов из пула активных в пул неактивных по свойству isActive
     */
    public void checkInactiveSprites(){
        if (activeSprites.isEmpty()) return;
        T sprite;
        Iterator<T> iterator = activeSprites.iterator();
        while (iterator.hasNext()){
            sprite = iterator.next();
            if (!sprite.isActive()){
              inactiveSprites.add(sprite);
              iterator.remove();
            };
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

    //Принудительно переводит все активные спрайты в инактив
    public void forceInactive(){
        for (BaseSprite sprite : getActiveSpritesList()){
            sprite.setActive(false);
        }
        checkInactiveSprites();
    }


}
