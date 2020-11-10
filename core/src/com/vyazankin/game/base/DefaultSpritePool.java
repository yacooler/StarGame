package com.vyazankin.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.HashMap;
import java.util.Map;

public class DefaultSpritePool<T extends BaseSprite> extends BaseSpritePool<T> {

    private Map<BaseSprite, DefaultPoolSpriteLayout> defaultSpritePoolSpriteLayouts;
    DefaultPoolSpriteLayout drawLayout = DefaultPoolSpriteLayout.BACKGROUND;

    public DefaultSpritePool() {
        super();
        defaultSpritePoolSpriteLayouts = new HashMap<>();
    }

    public void setDrawLayout(DefaultPoolSpriteLayout drawLayout) {
        this.drawLayout = drawLayout;
    }

    @Override
    protected T obtainSprite() {
        throw new UnsupportedOperationException("Запрещено динамическое добавление спрайтов в пул по умолчанию!");
    }

    public void addSpriteIntoActive(T sprite, DefaultPoolSpriteLayout layout) {
        super.addSpriteIntoActive(sprite);
        defaultSpritePoolSpriteLayouts.put(sprite, layout);
    }

    public void removeSprite(T sprite) {
        super.removeSprite(sprite);
        defaultSpritePoolSpriteLayouts.remove(sprite);
    }


    /**
     * В пуле по умолчанию рисуем бэкграунд и форграунд отдельно, в разные части отрисовки
     * @param batch
     */
    @Override
    public void draw(Batch batch) {
        for (T t : activeSprites) {
            if (!t.isActive()) continue;

            //Если layout не указан - рисуем на заднем фоне
            if (defaultSpritePoolSpriteLayouts.get(t) == null && drawLayout == DefaultPoolSpriteLayout.BACKGROUND) {
                System.out.println("Не указан layout!");
                if (t.isActive) t.draw(batch);
            } else {
                //Иначе рисуем на указанном в функции setDrawLayout
                if (t.isActive && defaultSpritePoolSpriteLayouts.get(t).equals(drawLayout))
                    t.draw(batch);
            }
            afterPoolDraw(batch);
        }
    }


}
