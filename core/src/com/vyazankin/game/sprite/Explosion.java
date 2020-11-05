package com.vyazankin.game.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;

public class Explosion extends BaseSprite {

    Sound explosionSound;

    public Explosion(TextureRegion[] regions, Sound explosionSound) {
        super(regions);
        this.explosionSound = explosionSound;
    }

    public void set(
            Vector2 centerPosition,
            float heightProportion){
        setCenterPosition(centerPosition);
        currentFrame = 0;
        setHeightProportion(heightProportion);
        explosionSound.play(0.5f);
    }

    @Override
    protected void recalc(float deltaTime) {
        super.recalc(deltaTime);
        if (!isActive()) return;
        if ( ++currentFrame >= textureRegions.length ){
            setActive(false);
        }

    }
}
