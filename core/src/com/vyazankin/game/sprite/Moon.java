package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.base.TouchListener;

import static java.lang.Math.*;

public class Moon extends BaseSprite implements TouchListener {

    private Vector2 velocity = new Vector2(0,0);
    private Vector2 temporary = new Vector2(0,0);
    private Vector2 destinationPosition = null;
    private final float velocityMul = 0.001f;

    public Moon(TextureRegion region) {
        super(region);
        setCenterPosition(0f, 0f);
    }

    @Override
    public void touchDown(Vector2 worldTouchPosition, int pointer, int button) {

    }

    @Override
    public void touchUp(Vector2 worldTouchPosition, int pointer, int button) {
        temporary.set(worldTouchPosition);
        temporary.sub(getCenterPosition()).nor();
        destinationPosition = worldTouchPosition;
        velocity.set(temporary.scl(velocityMul));
    }

    @Override
    public void recalc(float deltaTime) {

        angle += 0.5f;
        if (angle > 360f) angle = 0;

        if (null != destinationPosition){

            setCenterPosition(getCenterPosition().add(velocity));

            if (temporary.set(destinationPosition).sub(getCenterPosition()).len() < velocityMul){
                setCenterPosition(destinationPosition);
                destinationPosition = null;
            }

        }

    }
}
