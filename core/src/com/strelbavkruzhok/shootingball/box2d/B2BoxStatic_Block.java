package com.strelbavkruzhok.shootingball.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.strelbavkruzhok.shootingball.Main;
import com.strelbavkruzhok.shootingball.actors.BlockActor;

public class B2BoxStatic_Block {
    private float worldWidth;
    private float worldHeight;
    private float worldScale;
    private float screenScale;

    private BodyDef bodyDef;
    private PolygonShape boxShape;
    private Body body;
    private float halfWidthBox;
    private float halfHeightBox;

    public B2BoxStatic_Block(World world, float x, float y, float width, float height, float worldScale, BlockActor blockActorUserData) {
        worldWidth = Main.SCREEN_WIDTH * worldScale;
        worldHeight = Main.SCREEN_HEIGHT * worldScale;
        this.worldScale = worldScale;
        screenScale = 1 / worldScale;

        halfWidthBox = width * worldScale / 2;
        halfHeightBox = height * worldScale / 2;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) * worldScale, (y + height / 2) * worldScale);

        boxShape = new PolygonShape();
        boxShape.setAsBox(halfWidthBox, halfHeightBox);// boxShape.setAsBox(width * worldScale / 2, height * worldScale / 2);

        body = world.createBody(bodyDef);
        body.createFixture(boxShape, 0);

        boxShape.dispose();
        body.setUserData(blockActorUserData);
    }

    public float getX() {
        return (body.getPosition().x - halfWidthBox) * screenScale;
    }

    public float getY() {
        return (body.getPosition().y - halfHeightBox) * screenScale;
    }

    public void setPosition(float x, float y) {
        body.setTransform(x * worldScale + halfWidthBox, y * worldScale + halfHeightBox,  0);
    }

    public Body getBody() {
        return body;
    }

}
