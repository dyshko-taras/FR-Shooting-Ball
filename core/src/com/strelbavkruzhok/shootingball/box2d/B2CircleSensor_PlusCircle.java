package com.strelbavkruzhok.shootingball.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.strelbavkruzhok.shootingball.Main;
import com.strelbavkruzhok.shootingball.actors.PlusCircleActor;

public class B2CircleSensor_PlusCircle {

    private float worldWidth;
    private float worldHeight;
    private float worldScale;
    private float screenScale;

    private BodyDef bodyDef;
    private CircleShape circleShape;
    private Body body;
    private FixtureDef fixtureDef;
    private float radiusCircle;
    private int check = 0;


    public B2CircleSensor_PlusCircle(World world, float x, float y, float radius, float worldScale, PlusCircleActor plusCircleUserData) {
        worldWidth = Main.SCREEN_WIDTH * worldScale;
        worldHeight = Main.SCREEN_HEIGHT * worldScale;
        this.worldScale = worldScale;
        screenScale = 1 / worldScale;

        radiusCircle = radius * worldScale;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x * worldScale + radiusCircle, y * worldScale + radiusCircle);
        circleShape = new CircleShape();
        circleShape.setRadius(radiusCircle);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = true;
        fixtureDef.density = 0;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        circleShape.dispose();

        body.setUserData(plusCircleUserData);
    }

    public float getX() {
        return (body.getPosition().x - radiusCircle) * screenScale;
    }

    public float getY() {
        return (body.getPosition().y - radiusCircle) * screenScale;
    }

    public void setPosition(float x, float y) {
        body.setTransform(x * worldScale + radiusCircle, y * worldScale + radiusCircle, 0);
    }

    public Body getBody() {
        return body;
    }
}

