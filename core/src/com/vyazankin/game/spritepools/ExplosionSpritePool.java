package com.vyazankin.game.spritepools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.base.BaseSpritePool;
import com.vyazankin.game.sprite.Explosion;
import com.vyazankin.game.utils.TextureUtils;

public class ExplosionSpritePool extends BaseSpritePool<Explosion> {

    private TextureAtlas mainAtlas;
    private Sound explosionSound;

    public ExplosionSpritePool(TextureAtlas mainAtlas, Sound explosionSound) {
        this.mainAtlas = mainAtlas;
        this.explosionSound = explosionSound;
    }

    @Override
    protected Explosion obtainSprite() {
        return new Explosion(TextureUtils.split(mainAtlas.findRegion("explosion"),9,9,74), explosionSound);
    }
}
