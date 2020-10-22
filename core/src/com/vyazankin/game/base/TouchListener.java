package com.vyazankin.game.base;

import com.badlogic.gdx.math.Vector2;
import com.vyazankin.game.math.Rect;

import java.util.function.Predicate;

/**
 * Интерфейс, позволяющий подписать объект на получение событий от BaseScreen
 */
public interface TouchListener {
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
}
