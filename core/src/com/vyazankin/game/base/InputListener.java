package com.vyazankin.game.base;

import com.badlogic.gdx.math.Vector2;

/**
 * Интерфейс, позволяющий подписать объект на получение событий от BaseScreen
 */
public interface InputListener {
        /**
     *Обработка события отпускания кнопки/пада в мировой(игровой) системе координат
     */
    public void touchDown(Vector2 worldTouchPosition, int pointer, int button);

    /**
     *Обработка события нажатия кнопки/пада в мировой(игровой) системе координат
     */
    public void touchUp(Vector2 worldTouchPosition, int pointer, int button);

    /**
     * Обработка события протягивания в мировой(игровой) системе координат
     */
    public default void drag(Vector2 worldTouchPosition, int pointer){};

    /**
     * Проверка на попадание в необходимые координаты - должна быть переопределена для
     * корректной работы BaseScreen.
     * При выполнении условия попадания(например, вектор попал в прямоугольную область) - возвращаем true
     * в этом случае BaseScreen отправит текущему Listenerу соответствуюшее событие
     */
    public default boolean isTouchDownInBounds(Vector2 checkedPosition){
        return true;
    };

    public default boolean isTouchUpInBounds(Vector2 checkedPosition){
        return true;
    };


    //Кнопки клавиатуры
    public default boolean keyDown(int keycode) {
        return false;
    }

    public default boolean keyUp(int keycode) {
        return false;
    }

    public default boolean keyTyped(char character) {
        return false;
    }



}
