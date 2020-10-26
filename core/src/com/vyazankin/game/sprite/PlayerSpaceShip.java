package com.vyazankin.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseSprite;
import com.vyazankin.game.base.TouchListener;
import com.vyazankin.game.math.Rect;

public class PlayerSpaceShip extends BaseSprite implements TouchListener {

    private Rect worldBounds;
    private final float SHIP_SIZE = 0.1f;
    private Vector2 velocity;
    private Vector2 destinationPosition;
    private Vector2 temporary;
    private float velocityMul = 0.01f;

    public PlayerSpaceShip(TextureRegion region) {
        super(region);
        temporary = new Vector2(0,0);
        velocity = new Vector2(0,0);
    }



    @Override
    public void worldResize(Rect bounds) {
        super.worldResize(bounds);
        worldBounds = bounds;
        setHeightProportion(SHIP_SIZE);
        setCenterPosition(bounds.getCenterPosition().x, bounds.getBottom() + SHIP_SIZE / 2f);
    }

    @Override
    public void touchDown(Vector2 worldTouchPosition, int pointer, int button) {

    }

    @Override
    public void touchUp(Vector2 worldTouchPosition, int pointer, int button) {
        worldTouchPosition.y = getCenterPosition().y;
        temporary.set(worldTouchPosition);
        temporary.sub(getCenterPosition()).nor();
        destinationPosition = worldTouchPosition;
        velocity.set(temporary.scl(velocityMul));
    }

    @Override
    public void recalc(float deltaTime) {
        super.recalc(deltaTime);

        if (null != destinationPosition){

            setCenterPosition(getCenterPosition().add(velocity));

            if (temporary.set(destinationPosition).sub(getCenterPosition()).len() < velocityMul){
                setCenterPosition(destinationPosition);
                destinationPosition = null;
            }

        }
    }
}
