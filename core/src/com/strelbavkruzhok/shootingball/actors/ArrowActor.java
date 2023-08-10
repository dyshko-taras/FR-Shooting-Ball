package com.strelbavkruzhok.shootingball.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ArrowActor extends Actor {
    private Image image;
    public float degrees = 0;

    public ArrowActor(Image image, float x, float y, float width, float height, float originX, float originY) {
        super();
        this.image = image;

        setPosition(x,y);
        setSize(width, height);
        setOrigin(originX, originY);
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


    @Override
    public void act(float delta) {
        super.act(delta);
        setRotation(degrees);
    }
}
