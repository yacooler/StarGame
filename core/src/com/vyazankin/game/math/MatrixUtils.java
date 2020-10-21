package com.vyazankin.game.math;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class MatrixUtils {

    /**
     * Расчет матрицы проекции
     * @param matrix4 матрица, содержащая результат
     * @param source область источника
     * @param destination область получателя
     */
    public static Matrix4 calculateTransitionMatrix(Matrix4 matrix4, Rect source, Rect destination){
        //Масштабирование
        float scaleX = destination.getHalfWidth() / source.getHalfWidth();
        float scaleY = destination.getHalfHeight() / source.getHalfHeight();

        matrix4 .idt()
                .translate(destination.getCenterPosition().x, destination.getCenterPosition().y, 0f)
                .scale(scaleX, scaleY, 1f)
                .translate(-source.getCenterPosition().x, -source.getCenterPosition().y, 0f);
        return matrix4;
    }

    /**
     * Расчет матрицы проекции
     * @param matrix3 матрица, содержащая результат
     * @param source область источника
     * @param destination область получателя
     */
    public static Matrix3 calculateTransitionMatrix(Matrix3 matrix3, Rect source, Rect destination){
        float scaleX = destination.getHalfWidth() / source.getHalfWidth();
        float scaleY = destination.getHalfHeight() / source.getHalfHeight();
        matrix3 .idt()
                .translate(destination.getCenterPosition().x, destination.getCenterPosition().y)
                .scale(scaleX, scaleY)
                .translate(-source.getCenterPosition().x, -source.getCenterPosition().y);
        return matrix3;
    }


}
