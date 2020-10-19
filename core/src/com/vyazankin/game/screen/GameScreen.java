package com.vyazankin.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.base.BaseScreen;
import com.vyazankin.game.base.BaseSprite;

public class GameScreen extends BaseScreen {

    SriteMoon moon;

    @Override
    public void show() {
        super.show();
        moon = new SriteMoon(new Vector2(100,100), new Vector2(100,100));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        moon.recalc();
        batch.begin();
        moon.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        moon.dispose();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        moon.setDestination(new Vector2(screenX, Gdx.graphics.getHeight() - screenY));
        return super.touchDown(screenX, screenY, pointer, button);
    }


    class SriteMoon extends BaseSprite {
        private Texture img;
        Vector2 position;
        Vector2 velocity;
        Vector2 destination;
        Vector2 distance;
        Vector2 size;

        public SriteMoon(Vector2 position, Vector2 size) {
            this.position = position;
            this.size = size;
            velocity = new Vector2(0,0);
            distance = new Vector2(0,0);
            destination = null;
            img = new Texture("moon.jpg");
        }


        public void draw(Batch batch){
            batch.draw(img,position.x - size.x / 2, position.y - size.y / 2, size.x, size.y);
        }

        public void dispose(){
            img.dispose();
        }

        public void recalc(){
            if (destination == null) return;

            distance.set(position);
            distance.sub(destination);

            //Если достигли заданной точки - убираем скорость, убираем пункт назначения
            if (distance.len() < 1.0f){
                velocity.set(0f, 0f);
                destination = null;
                return;
            }

            position.add(velocity);
        }

        //Задаем новый пункт назначения и пересчитываем скорость
        public void setDestination(Vector2 destination) {
            this.destination = destination;
            velocity.set(destination);
            velocity.sub(position);
            velocity.nor();
        }
    }



}
