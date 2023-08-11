package com.strelbavkruzhok.shootingball.box2d;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.strelbavkruzhok.shootingball.actors.BallActor;
import com.strelbavkruzhok.shootingball.actors.BlockActor;
import com.strelbavkruzhok.shootingball.actors.PlusCircleActor;
import com.strelbavkruzhok.shootingball.actors.TriangleActor;

public class ListenerClass implements ContactListener {
    private Fixture fixtureA;
    private Fixture fixtureB;
    private BlockActor blockActor;
    private PlusCircleActor plusCircleActor;
    private TriangleActor triangleActor;
    private BallActor ballActor;

    private Array<BlockActor> blocksActorToRemove;
    private Array<PlusCircleActor> plusCircleActorToRemove;
    private Array<TriangleActor> triangleActorToRemove;

    public ListenerClass(Array<BlockActor> blocksToRemove, Array<PlusCircleActor> plusCircleToRemove, Array<TriangleActor> triangleToRemove) {
        this.blocksActorToRemove = blocksToRemove;
        this.plusCircleActorToRemove = plusCircleToRemove;
        this.triangleActorToRemove = triangleToRemove;
    }

    @Override
    public void beginContact(Contact contact) {
        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        if (fixtureA.getBody().getUserData() instanceof BlockActor && fixtureB.getBody().getUserData() instanceof BallActor) {
            blockActor = (BlockActor) fixtureA.getBody().getUserData();
            ballActor = (BallActor) fixtureB.getBody().getUserData();
            blocksActorToRemove.add(blockActor);
        } else if (fixtureB.getBody().getUserData() instanceof BlockActor && fixtureA.getBody().getUserData() instanceof BallActor) {
            blockActor = (BlockActor) fixtureB.getBody().getUserData();
            ballActor = (BallActor) fixtureA.getBody().getUserData();
            blocksActorToRemove.add(blockActor);
        }


        if (fixtureA.getBody().getUserData() instanceof PlusCircleActor && fixtureB.getBody().getUserData() instanceof BallActor) {
            plusCircleActor = (PlusCircleActor) fixtureA.getBody().getUserData();
            ballActor = (BallActor) fixtureB.getBody().getUserData();
            plusCircleActorToRemove.add(plusCircleActor);
        } else if (fixtureB.getBody().getUserData() instanceof PlusCircleActor && fixtureA.getBody().getUserData() instanceof BallActor) {
            plusCircleActor = (PlusCircleActor) fixtureB.getBody().getUserData();
            ballActor = (BallActor) fixtureA.getBody().getUserData();
            plusCircleActorToRemove.add(plusCircleActor);
        }


        if (fixtureA.getBody().getUserData() instanceof TriangleActor && fixtureB.getBody().getUserData() instanceof BallActor) {
            triangleActor = (TriangleActor) fixtureA.getBody().getUserData();
            ballActor = (BallActor) fixtureB.getBody().getUserData();
            triangleActorToRemove.add(triangleActor);
        } else if (fixtureB.getBody().getUserData() instanceof TriangleActor && fixtureA.getBody().getUserData() instanceof BallActor) {
            triangleActor = (TriangleActor) fixtureB.getBody().getUserData();
            ballActor = (BallActor) fixtureA.getBody().getUserData();
            triangleActorToRemove.add(triangleActor);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
