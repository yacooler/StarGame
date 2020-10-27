package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.math.Rect;

public class Bullet extends BaseSprite {


    private Vector2 velocity = new Vector2();
    private int damage;


    public void setVelocity(Vector2 velocity) {
        //В сетерах надо проверять, существует ли устанавливаемый объект
        this.velocity.set(velocity);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


    @Override
    protected void worldResize(Rect bounds) {
        super.worldResize(bounds);

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    protected void recalc(float deltaTime) {
        getCenterPosition().mulAdd(velocity, deltaTime);
        //Если вылетели за границы экрана центром пули - выводим её в пул неактивных спрайтов
        if (!getActualWorldBound().isVectorInside(getCenterPosition())) {
            setActive(false);
        }
    }


}
