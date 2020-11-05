package com.vyazankin.game.spritepools;

import com.vyazankin.game.base.BaseSpritePool;
import com.vyazankin.game.sprite.Bullet;

public class BulletSpritePool extends BaseSpritePool<Bullet> {
    @Override
    protected Bullet obtainSprite() {
        Bullet bullet = new Bullet();

        //System.out.printf("active/inactive %d/%d \n", getActiveSpritesList().size(), getInactiveSpritesList().size()  );

        return bullet;
    }
}
