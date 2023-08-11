package com.strelbavkruzhok.shootingball.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.strelbavkruzhok.shootingball.Main;
import com.strelbavkruzhok.shootingball.actors.TriangleActor;

public class B2PolygonStatic_Triangle {
    private float worldWidth;
    private float worldHeight;
    private float worldScale;
    private float screenScale;

    private BodyDef bodyDef;
    private PolygonShape shape;
    private Body body;

    private float oX, oY;

    public B2PolygonStatic_Triangle(World world, float x, float y, Vector2 p1, Vector2 p2, Vector2 p3, float worldScale, TriangleActor triangleActorUserData) {
        worldWidth = Main.SCREEN_WIDTH * worldScale;
        worldHeight = Main.SCREEN_HEIGHT * worldScale;
        this.worldScale = worldScale;
        screenScale = 1 / worldScale;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        shape = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2((x + p1.x) * worldScale, (y + p1.y) * worldScale);
        vertices[1] = new Vector2((x + p2.x) * worldScale, (y + p2.y) * worldScale);
        vertices[2] = new Vector2((x + p3.x) * worldScale, (y + p3.y) * worldScale);
        shape.set(vertices);

        body = world.createBody(bodyDef);
        body.createFixture(shape, 0);

        shape.dispose();
        body.setUserData(triangleActorUserData);

        oX = body.getPosition().x - x * worldScale;
        oY = body.getPosition().y - y * worldScale;
    }

    public void setPosition(float x, float y) {
        body.setTransform(x * worldScale + oX, y * worldScale + oY, 0);
    }

    public Body getBody() {
        return body;
    }
}
