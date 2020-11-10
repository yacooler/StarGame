package com.vyazankin.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


abstract public class BaseSprite extends Rect {

    protected boolean isActive;

    protected float angle;
    protected float scale = 1f;

    protected TextureRegion[] textureRegions;
    protected int currentFrame = 0;
    protected Rect actualSpriteWorldBound;
    protected boolean initialized;

    public BaseSprite(){
        //Конструктор для спрайта, используемого в пуле
        //initialized = false
    }

    public BaseSprite(TextureRegion region){
        textureRegions = new TextureRegion[1];
        textureRegions[0] = region;

        initialized = true;
    }

    public BaseSprite(TextureRegion[] regions){
        textureRegions = regions;

        initialized = true;
    }

    public void setSpriteParams(TextureRegion region,
                                int currentFrame,
                                Vector2 centerPosition,
                                float highProportion){

        if (textureRegions == null) textureRegions = new TextureRegion[1];
        textureRegions[0] = region;
        this.currentFrame = currentFrame;
        setCenterPosition(centerPosition);
        setHeightProportion(highProportion);

        initialized = true;
    }


    /**
     * Установка размеров в процентном отношении относительно размера экрана
     * @param height принимает значения от 0 до 1, где 1f - картинка занимает полный экран
     */
    public void setHeightProportion(float height){
        setHeight(height);
        setWidth(height * textureRegions[currentFrame].getRegionWidth() / (float) textureRegions[currentFrame].getRegionHeight());
    }


    /**
     * Признак активности спрайта
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Признак активности спрайта
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Должен быть вызван для отрисовки спрайта baseScreen
     * @param batch
     */
    public void draw(Batch batch){

        if (!initialized) throw new IllegalArgumentException("Перед использованием спрайта он должен быть инициализирован! " + this);

        if (!isActive) return;
        batch.draw(
                textureRegions[currentFrame],
                getLeft(),      getBottom(),
                getHalfWidth(), getHalfHeight(),
                getFullWidth(), getFullHeight(),
                scale,          scale,
                angle);
    };

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Rect getActualSpriteWorldBound() {
        return actualSpriteWorldBound;
    }

    /**
     * Освобождаем текстуры
     */
    public void dispose(){
        System.out.println("Base sprite dispose");
        if (textureRegions!=null) {
            for (TextureRegion region : textureRegions) {
                if (region!=null && region.getTexture() != null) region.getTexture().dispose();
            }
        }
        textureRegions = null;
    };



    protected void worldResize(Rect bounds){
        actualSpriteWorldBound = bounds;
    };


    /**
     * Должен быть вызван из метода render baseScreen
     * @param deltaTime - дельта времени, к которой могут быть привязаны расчеты
     */
    protected void recalc(float deltaTime){
        if (!initialized) throw new IllegalArgumentException("Перед использованием спрайта он должен быть инициализирован! " + this);
    };

}
