package com.vyazankin.game.math;

import com.badlogic.gdx.math.Vector2;

public class Rect {

    //Точка по центру прямоугольника
    protected final Vector2 centerPosition = new Vector2();

    //Половина ширины и высоты прямоугольника
    protected float halfWidth;
    protected float halfHeight;


    //Конструкторы
    public Rect() {
    }

    public Rect(Rect from){
        this.centerPosition.set(from.getCenterPosition());
        this.halfHeight = from.halfHeight;
        this.halfWidth = from.halfWidth;
    }

    public Rect(Vector2 centerPosition, float halfWidth, float halfHeight){
        this.centerPosition.set(centerPosition);
        this.halfHeight = halfHeight;
        this.halfWidth = halfWidth;
    }

    public Rect(float centerX, float centerY, float halfWidth, float halfHeight){
        this.centerPosition.set(centerX, centerY);
        this.halfHeight = halfHeight;
        this.halfWidth = halfWidth;
    }

    public Rect clone(Rect sourceRect){
        this.centerPosition.set(sourceRect.getCenterPosition());
        this.halfHeight = sourceRect.halfHeight;
        this.halfWidth = sourceRect.halfWidth;
        return this;
    }

    public Rect scale(float scale){
        halfHeight *= scale;
        halfWidth *= scale;
        return this;
    }

    //Геттеры/Сеттеры
    public Vector2 getCenterPosition() {
        return centerPosition;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getLeft(){
        return centerPosition.x - halfWidth;
    }

    public float getTop(){
        return centerPosition.y + halfHeight;
    }

    public float getRight(){
        return centerPosition.x + halfWidth;
    }

    public float getBottom(){
        return centerPosition.y - halfHeight;
    }

    /**
     * @return Возвращаем полную высоту прямоугольника
     */
    public float getFullWidth(){
        return 2f * halfWidth;
    }

    /**
     * @return Возвращаем полную высоту прямоугольника
     */
    public float getFullHeight(){
        return 2f * halfHeight;
    }


    /**
     * Задаем длины сторон прямоугольника через их полный, а не половинный размер
     * @param fullWith полная ширина
     * @param fullHeight полная высота
     */
    public void setSize(float fullWith, float fullHeight){
        this.halfWidth = fullWith / 2f;
        this.halfHeight = fullHeight / 2f;
    }

    /**
     * Задаем половинный размер ширины через полный
     * @param fullWidth полная ширина
     */
    public void setWidth(float fullWidth){
        this.halfWidth = fullWidth / 2f;
    }

    /**
     * Задаем половинный размер высоты через полный
     * @param fullHeight полная высота
     */
    public void setHeight(float fullHeight){
        this.halfHeight = fullHeight / 2f;
    }


    /**
     * Задаем координаты центра прямоугольника
     */
    public void setCenterPosition(float x, float y){
        this.centerPosition.set(x,y);
    }

    /**
     * Задаем координаты центра прямоугольника
     */
    public void setCenterPosition(Vector2 centerPosition){
        this.centerPosition.set(centerPosition);
    }

    /**
     * Задает левую точку прямоугольника. Координаты центра смещены относительно неё на +половину ширины
     * @param left левая точка
     */
    public void setLeft(float left){
        this.centerPosition.x = left + halfWidth;
    }

    /**
     * Задает верхнюю точку прямоугольника. Координаты центра смещены относительно неё на -половину высоты
     * @param top верхняя точка
     */
    public void setTop(float top){
        this.centerPosition.y = top - halfHeight;
    }

    /**
     * Задает правую точку прямоугольника. Координаты центра смещены относительно неё на -половину ширины
     * @param right правая точка
     */
    public void setRight(float right){
        centerPosition.x = right - halfWidth;
    }

    /**
     * Задает нижнюю точку прямоугольника. Координаты центра смещены относительно неё на +половину высоты
     * @param bottom нижняя точка
     */
    public void setBottom(float bottom){
        this.centerPosition.y = bottom + halfHeight;
    }

    /**
     * Находится ли точка position внутри прямоугольника?
     * @param position позиция для проверки
     * @return
     */
    public boolean isVectorInside(Vector2 position){
        return (position.x >= getLeft() && position.x <= getRight()
           && position.y >= getBottom() && position.y <= getTop());
    }


    @Override
    public String toString() {
        return "Rect{" +
                "centerPosition=" + centerPosition +
                ", halfWidth=" + halfWidth +
                ", halfHeight=" + halfHeight +
                '}';
    }



}
