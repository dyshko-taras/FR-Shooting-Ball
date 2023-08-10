package com.strelbavkruzhok.shootingball.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.strelbavkruzhok.shootingball.box2d.B2CircleSensor_PlusCircle;

public class PlusCircleActor extends Actor {

    private Image image;
    private Circle circle;
    public float radius;
    public B2CircleSensor_PlusCircle b2PlusCircle;

    public PlusCircleActor(Image image, float x, float y, float radius, World world, float worldScale) {
        super();

        this.image = image;
        circle = new Circle();
        this.radius = radius;

        setBounds(x, y, radius * 2, radius * 2);
        b2PlusCircle = new B2CircleSensor_PlusCircle(world, x, y, radius, worldScale, this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        image.setBounds(getX(), getY(), getWidth(), getHeight());
        image.setOrigin(getOriginX(), getOriginY());
        image.setRotation(getRotation());
        image.setScale(getScaleX(), getScaleY());
        image.draw(batch, parentAlpha);
    }

    public void setPositionB2(float x, float y) {
        super.setPosition(x, y);
        b2PlusCircle.setPosition(x, y);
    }

    public void setPositionB2(float x, float y, Group group) {
        super.setPosition(x, y);
        b2PlusCircle.setPosition(x + group.getX(), y + group.getY());
    }

    public void updateB2Position(Group group) {
        b2PlusCircle.setPosition(getX() + group.getX(), getY() + group.getY());
    }

    public Circle getCircle() {
        circle.x = getX() + radius;
        circle.y = getY() + radius;
        circle.radius = radius;
        return circle;
    }
}
