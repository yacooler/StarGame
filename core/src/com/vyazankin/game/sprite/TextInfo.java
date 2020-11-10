package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Align;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.libgdxadapters.FontAdapter;


/**
 * Класс, отвечающий за отображение здоровья, счета и уровня сложности
 */
public class TextInfo extends BaseSprite {
    private static final float FONT_SIZE = 0.03F;
    private static final float MARGIN = 0.01F;

    private static final String HP = "HP:%d";
    private static final String SCORE = "SCORE:%d";
    private static final String LEVEL = "LEVEL:%d";

    FontAdapter font;
    private int hitpoints;
    private int score;
    private int level;


    public TextInfo(){
        font = new FontAdapter("resources/font/font.fnt", "resources/font/font.png");
        font.setSize(FONT_SIZE);
        initialized = true;
    }

    public void set(int hitpoints, int score, int level){
        this.hitpoints = hitpoints;
        this.score = score;
        this.level = level;
    }

    @Override
    public void draw(Batch batch) {
        font.draw(batch, String.format(HP, hitpoints), actualSpriteWorldBound.getLeft() + MARGIN, actualSpriteWorldBound.getTop() - MARGIN);
        font.draw(batch, String.format(SCORE, score),  actualSpriteWorldBound.getCenterPosition().x, actualSpriteWorldBound.getTop() - MARGIN, Align.center);
        font.draw(batch, String.format(LEVEL, level), actualSpriteWorldBound.getRight() - MARGIN, actualSpriteWorldBound.getTop() - MARGIN, Align.right);
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }
}
