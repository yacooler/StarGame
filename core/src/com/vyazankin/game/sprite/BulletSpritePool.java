package com.vyazankin.game.sprite;

import com.vyazankin.game.base.BaseSpritePool;

public class BulletSpritePool extends BaseSpritePool<Bullet> {
    @Override
    protected Bullet obtainSprite() {
        Bullet bullet = new Bullet();

        //System.out.printf("active/inactive %d/%d \n", getActiveSpritesList().size(), getInactiveSpritesList().size()  );

        return bullet;
    }
}
