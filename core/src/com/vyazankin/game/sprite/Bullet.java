package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.math.Rect;

public class Bullet extends BaseSprite {


    private Vector2 velocity = new Vector2();

    private int damage;
    private boolean playerIsOwner;


    public void set(TextureRegion region,
                    int currentFrame,
                    Vector2 centerPosition,
                    float highProportion,

                    Vector2 velocity,
                    int damage,

                    boolean playerIsOwner) {

        this.setSpriteParams(region, currentFrame, centerPosition, highProportion);

        //В сетерах надо проверять, существует ли устанавливаемый объект
        this.velocity.set(velocity);
        this.damage = damage;
        this.playerIsOwner = playerIsOwner;
    }


    @Override
    protected void recalc(float deltaTime) {
        getCenterPosition().mulAdd(velocity, deltaTime);
        //Если вылетели за границы экрана центром пули - выводим её в пул неактивных спрайтов
        if (!getActualSpriteWorldBound().isVectorInside(getCenterPosition())) {
            setActive(false);
        }
    }

    public boolean isPlayerIsOwner(){
        return playerIsOwner;
    }

    public int getDamage(){
        return damage;
    }

    public Vector2 getVelocity(){
        return velocity;
    }
}
