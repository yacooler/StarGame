package com.vyazankin.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.vyazankin.game.math.Rect;


abstract public class BaseSprite extends Rect {
    protected float angle;
    protected float scale = 1f;

    protected TextureRegion[] textureRegions;
    protected int currentFrame = 0;

    private BaseSprite(){
        throw new UnsupportedOperationException("Нельзя создавать BaseSprite без параметров!");
    }

    public BaseSprite(TextureRegion region){
        textureRegions = new TextureRegion[1];
        textureRegions[0] = region;
    }

    public BaseSprite(TextureRegion[] regions){
        textureRegions = regions;
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
     * Должен быть вызван для отрисовки спрайта baseScreen
     * @param batch
     */
    public void draw(Batch batch){
        batch.draw(
                textureRegions[currentFrame],
                getLeft(),      getBottom(),
                getHalfWidth(), getHalfHeight(),
                getFullWidth(), getFullHeight(),
                scale,          scale,
                angle);
    };

    public void worldResize(Rect bounds){};

    /**
     * Освобождаем текстуры
     */
    public void dispose(){
        for (TextureRegion region: textureRegions) {
            region.getTexture().dispose();
        }
        textureRegions = null;
    };

    /**
     * Должен быть вызван из метода render baseScreen
     * @param deltaTime - дельта времени, к которой могут быть привязаны расчеты
     */
    public void recalc(float deltaTime){};

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }


}
