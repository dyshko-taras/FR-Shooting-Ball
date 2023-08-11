package com.strelbavkruzhok.shootingball.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.strelbavkruzhok.shootingball.box2d.B2PolygonStatic_Triangle;
import com.strelbavkruzhok.shootingball.utils.LabelNum;

public class TriangleActor extends Actor {
    private Image image;
    public Label label;
    public float width;
    public float height;
    public B2PolygonStatic_Triangle b2Triangle;
    private World world;
    private float worldScale;
    public int point = 1;

    public TriangleActor(Image image, Label label, float x, float y, float width, float height, Vector2 p1, Vector2 p2, Vector2 p3, World world, float worldScale) {
        super();
        this.image = image;
        this.label = label;
        LabelNum.setNum(label, 1, 9);
        this.width = width;
        this.height = height;
        b2Triangle = new B2PolygonStatic_Triangle(world, x, y, p1, p2, p3, worldScale, this);
        this.world = world;
        this.worldScale = worldScale;

        setBounds(x, y, width, height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        image.setBounds(getX(), getY(), getWidth(), getHeight());
        image.setOrigin(getOriginX(), getOriginY());
        image.setRotation(getRotation());
        image.setScale(getScaleX(), getScaleY());
        image.draw(batch, parentAlpha);

        label.setAlignment(Align.center);
        label.setX(image.getX() + (width - label.getWidth()) / 4 * 3);
        label.setY(image.getY() + (height - label.getHeight()) / 4 * 3);
        label.setOrigin(image.getOriginX(), image.getOriginY());
        label.setRotation(image.getRotation());
        label.draw(batch, parentAlpha);
    }

    public void setPositionB2(float x, float y) {
        super.setPosition(x, y);
        b2Triangle.setPosition(x, y);
    }

    public void setPositionB2(float x, float y, Group group) {
        super.setPosition(x, y);
        b2Triangle.setPosition(x + group.getX(), y + group.getY());
    }

    public void updateB2Position(Group group) {
        b2Triangle.setPosition(getX() + group.getX(), getY() + group.getY());
    }






}
