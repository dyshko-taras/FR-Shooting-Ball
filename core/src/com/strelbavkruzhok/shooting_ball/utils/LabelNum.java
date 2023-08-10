package com.strelbavkruzhok.shooting_ball.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LabelNum {
    private static int num = 0;

    public static void setNum(Label label, int start, int end) {
        num = MathUtils.random(start, end);
        label.setText(num);
    }

    public static void removeOne(Label label) {
        num = Integer.parseInt(String.valueOf(label.getText())) - 1;
        label.setText(num);
    }

    public static int getNum(Label label) {
        num = Integer.parseInt(String.valueOf(label.getText()));
        return num;
    }


}
