package com.strelbavkruzhok.shootingball.actors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.strelbavkruzhok.shootingball.tools.GameState;
import com.strelbavkruzhok.shootingball.utils.MyMath;

public class GroupActors extends Group {

    private final float dx;
    private final float dy;
    private int numRows = 0;
    private BlockActorFactory blockActorFactory;
    private PlusCircleActorFactory plusCircleActorFactory;
    private TriangleActorFactory triangleActorFactory;

    //Act
    private float durationAct;
    private float distanceYAct;
    private float elapsedTime = 0;

    public GroupActors(float x,
                       float y,
                       float dx,
                       float dy,
                       float durationAct,
                       float distanceYAct,
                       BlockActorFactory blockActorFactory,
                       PlusCircleActorFactory plusCircleActorFactory,
                       TriangleActorFactory triangleActorFactory) {
        super();
        this.dx = dx;
        this.dy = dy;
        this.durationAct = durationAct;
        this.distanceYAct = distanceYAct;
        this.blockActorFactory = blockActorFactory;
        this.plusCircleActorFactory = plusCircleActorFactory;
        this.triangleActorFactory = triangleActorFactory;
        setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(GameState.getState() == GameState.BLOCKS_MOVING) {
            System.out.println("Get blocks moving");
            elapsedTime += delta;
            if(elapsedTime <= durationAct) {
                moveBy(0,-distanceYAct/durationAct * delta);
                updateB2Positions();
            } else {
                GameState.setState(GameState.STOP_BLOCKS);
                elapsedTime = 0;
                System.out.println("Set stop blocks");
            }
        }
    }

    public void addRandomRow(int numElements, float widthElement, float heightElement) {
        for (int numColumn = 0; numColumn < numElements; numColumn++) {
            if (MyMath.calculateProbability(80)) {
                int randomActor = MathUtils.random(0, 9);
                if (randomActor < 10) {
                    BlockActor blockActor = blockActorFactory.createBlockActor();
                    addActor(blockActor);
                    blockActor.setPositionB2(
                            numColumn * (widthElement + dx),
                            numRows * (heightElement + dy),
                            this);
                } else if (randomActor < 7) {
                    TriangleActor triangleActor = triangleActorFactory.createTriangleActor();
                    addActor(triangleActor);
                    triangleActor.setPositionB2(
                            numColumn * (widthElement + dx),
                            numRows * (heightElement + dy),
                            this);
                } else {
                    PlusCircleActor plusCircleActor = plusCircleActorFactory.createPlusCircleActor();
                    addActor(plusCircleActor);
                    plusCircleActor.setPositionB2(
                            numColumn * (widthElement + dx) + (widthElement - plusCircleActor.getWidth()) / 2,
                            numRows * (heightElement + dy) + (heightElement - plusCircleActor.getHeight()) / 2,
                            this);
                }
            }
        }
        numRows++;
    }

    private void updateB2Positions() {
        for (Actor actor : getChildren()) {
            if (actor instanceof BlockActor) {
                BlockActor blockActor = (BlockActor) actor;
                blockActor.updateB2Position(this);
            }

            if (actor instanceof PlusCircleActor) {
                PlusCircleActor plusCircleActor = (PlusCircleActor) actor;
                plusCircleActor.updateB2Position(this);
            }

            if (actor instanceof TriangleActor) {
                TriangleActor triangleActor = (TriangleActor) actor;
                triangleActor.updateB2Position(this);
            }
        }
    }
}
