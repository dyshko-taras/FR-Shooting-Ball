package com.strelbavkruzhok.shootingball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.strelbavkruzhok.shootingball.Main;
import com.strelbavkruzhok.shootingball.actors.BallActor;
import com.strelbavkruzhok.shootingball.actors.BlockActor;
import com.strelbavkruzhok.shootingball.actors.ArrowActor;
import com.strelbavkruzhok.shootingball.actors.BlockActorFactory;
import com.strelbavkruzhok.shootingball.actors.GroupActors;
import com.strelbavkruzhok.shootingball.actors.PlusCircleActor;
import com.strelbavkruzhok.shootingball.actors.PlusCircleActorFactory;
import com.strelbavkruzhok.shootingball.actors.TriangleActor;
import com.strelbavkruzhok.shootingball.actors.TriangleActorFactory;
import com.strelbavkruzhok.shootingball.box2d.B2Screen;
import com.strelbavkruzhok.shootingball.box2d.ListenerClass;
import com.strelbavkruzhok.shootingball.tools.GameSettings;
import com.strelbavkruzhok.shootingball.tools.GameState;
import com.strelbavkruzhok.shootingball.utils.LabelNum;
import com.strelbavkruzhok.shootingball.utils.LineAngleCalculator;

import java.util.Iterator;

public class GameScreen implements Screen, BlockActorFactory, PlusCircleActorFactory, TriangleActorFactory {

    public static final float SCREEN_WIDTH = Main.SCREEN_WIDTH;
    public static final float SCREEN_HEIGHT = Main.SCREEN_HEIGHT;

    //Viewports
    private final Main main;
    private Viewport viewport;
    private Viewport viewportBackground;
    private Skin skin;
    private Stage stage;
    private Stage stageBackground;
    private Table mainTable;
    private Image background;
    private Container container;
    private Table table;

    //Table
    private Image returnButton;
    private Label labelScore;
    private Label labelNumBalls;
    private Image settingButton;
    private Image achievementsButton;

    //Actors
    private BallActor ballActor;
    private Image imageBallActor;
    private ArrowActor arrowActor;
    private Image imageArrowActor;
    private float degrees = 0;
    private Image imageBlockActor;
    private Image imagePlusCircleActor;
    private Image imageTriangleActor;
    private GroupActors groupActors;


    //Box2D
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Viewport viewportBox2D;
    private float worldScale = 0.01f;
    private ListenerClass listenerClass;
    private Array<BlockActor> blocksActorToRemove = new Array<BlockActor>();
    private Array<PlusCircleActor> plusCircleActorToRemove = new Array<PlusCircleActor>();
    private Array<TriangleActor> triangleActorToRemove = new Array<TriangleActor>();
    private B2Screen b2Screen;

    //Game
    private int score = 0;
    private int numBalls = 1;

    public GameScreen(Main main) {
        this.main = main;
        GameSettings.setPlayGameTimes(GameSettings.getPlayGameTimes() + 1);
        GameState.setState(GameState.START_GAME);
    }


    public void show() {
        showCameraAndStage();
        showBox2D();

        skin = new Skin(Gdx.files.internal("skin.json"));

        mainTable = new Table();
        mainTable.setFillParent(true);

        container = new Container();
        container.minWidth(360.0f);
        container.minHeight(800.0f);
        container.maxWidth(360.0f);
        container.maxHeight(800.0f);

        table = new Table();

        returnButton = new Image(skin, "return");
        returnButton.setScaling(Scaling.fit);
        table.add(returnButton).padLeft(24.0f).padTop(30.0f).expandX().align(Align.topLeft).colspan(2);

        table.row();
        labelScore = new Label("0", skin, "f48_w_o");
        labelScore.setAlignment(Align.center);
        table.add(labelScore).padTop(-57.0f).expandX().align(Align.top).colspan(2);

        table.row();
        labelNumBalls = new Label("1x", skin, "f24_w");
        table.add(labelNumBalls).padLeft(124.0f).padBottom(7.0f).expand().align(Align.bottomLeft).colspan(2);

        table.row();
        Image simpleLine = new Image(skin, "line");
        table.add(simpleLine).padBottom(21.0f).expandX().align(Align.bottom).colspan(2);

        table.row();
        settingButton = new Image(skin, "settings");
        table.add(settingButton).padLeft(24.0f).padBottom(24.0f).expandX().align(Align.bottomLeft);

        achievementsButton = new Image(skin, "achievements");
        table.add(achievementsButton).padRight(28.0f).padBottom(28.0f).expandX().align(Align.bottomRight);

        container.setActor(table);
        mainTable.add(container);
        stage.addActor(mainTable);

        addBackground();
        setClickListeners();
        initAssets();
        addMyActors();
        initInputProcessor();
    }

    private void setClickListeners() {
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState.setState(GameState.MENU);
                main.setScreen(new MainMenuScreen(main));
            }
        });

        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState.setState(GameState.MENU);
                main.setScreen(new SettingScreen(main));
            }
        });

        achievementsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameState.setState(GameState.MENU);
                main.setScreen(new AchievScreen(main));
            }
        });
    }

    public void render(float delta) {
        renderCamera();
        renderCameraForBox2D();

        update(delta);
    }

    public void resize(int width, int height) {
        resizeCamera(width, height);
        resizeCameraForBox2D(width, height);
    }

    private void update(float delta) {
        setData();
        removeBlocksActorAndAddPoint();
        removeTriangleActorAndAddPoint();
        removePlusCircleActorAndAddNumBalls();
        addBlockActorIfStop();
        checkGameOver();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        main.batch.dispose();
    }

    /////Camera
    private void showCameraAndStage() {
        viewportBackground = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT); // ExtendViewport or FitViewport
        stageBackground = new Stage(viewportBackground);

        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);//only FitViewport
        stage = new Stage(viewport);
//        Gdx.input.setInputProcessor(stage);
    }

    private void renderCamera() {
        ScreenUtils.clear(Color.BLACK);

        viewportBackground.apply(true);
        stageBackground.draw();

        viewport.apply();
        stage.act();
        stage.draw();
    }

    private void resizeCamera(int width, int height) {
        viewportBackground.update(width, height);
        viewport.update(width, height, true);
    }
    ////////

    private void addBackground() {
        background = new Image(skin, "back");
        background.setFillParent(true);
        background.setScaling(Scaling.fillY);
        stageBackground.addActor(background);
    }

    private void setData() {
        labelNumBalls.setText(numBalls + "x");
        labelScore.setText(score);
    }

    public void initAssets() {
        imageBallActor = new Image(skin, "ball");
        imageArrowActor = new Image(skin, "arrow2");
        imageBlockActor = new Image(skin, "block");
        imagePlusCircleActor = new Image(skin, "plus");
        imageTriangleActor = new Image(skin, "triangle");
    }

    //
    public void addMyActors() {
        ballActor = new BallActor(imageBallActor, 160, 100, 20, world, worldScale);
        stage.addActor(ballActor);

        arrowActor = new ArrowActor(
                imageArrowActor,
                ballActor.getCircle().x,
                ballActor.getCircle().y - imageArrowActor.getHeight() / 2,
                imageArrowActor.getWidth(),
                imageArrowActor.getHeight(),
                0,
                imageArrowActor.getHeight() / 2
        );

        groupActors = new GroupActors(10, 632, 10, 10, 0.5f, 70, this, this,this);
        groupActors.addRandomRow(5, 60, 60);
        stage.addActor(groupActors);
    }

    private void initInputProcessor() {
        //init InputProcessor. The call order of the processors depends on the order you provide them!!!!
        InputMultiplexer multiplexer = new InputMultiplexer(stage, new InputProcessor() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (GameState.getState() == GameState.START_GAME || GameState.getState() == GameState.READY_TO_SHOOT) {
                    System.out.println("TouchDown");
                    Vector2 touch = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                    float x = touch.x;
                    float y = touch.y;

                    if (ballActor.getCircle().contains(x, y)) {
                        stage.addActor(arrowActor);

                        degrees = LineAngleCalculator.getDegrees(
                                arrowActor.getX() + arrowActor.getOriginX(),
                                arrowActor.getY() + arrowActor.getOriginY(),
                                x, y);
                        arrowActor.setPosition(ballActor.getCircle().x, ballActor.getCircle().y - imageArrowActor.getHeight() / 2);
                        arrowActor.degrees = degrees;
//                        System.out.println("degrees = " + degrees);
                    }
                }
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (GameState.getState() == GameState.START_GAME || GameState.getState() == GameState.READY_TO_SHOOT) {
                    Vector2 stageCoords = stage.screenToStageCoordinates(new Vector2(screenX, screenY));
                    float x = stageCoords.x;
                    float y = stageCoords.y;

                    degrees = LineAngleCalculator.getDegrees(
                            arrowActor.getX() + arrowActor.getOriginX(),
                            arrowActor.getY() + arrowActor.getOriginY(),
                            x, y);

                    arrowActor.setPosition(ballActor.getCircle().x, ballActor.getCircle().y - imageArrowActor.getHeight() / 2);
                    arrowActor.degrees = degrees;
                    System.out.println("degrees = " + degrees);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (GameState.getState() == GameState.START_GAME || GameState.getState() == GameState.READY_TO_SHOOT) {
                    System.out.println("TouchUp");
                    arrowActor.degrees = 0;
                    arrowActor.remove();
                    ballActor.degrees = degrees;
                    GameState.setState(GameState.SHOOTING);
                }
                return true;
            }


            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);
    }

    ///// Box2D
    private void showBox2D() {
        //init Box2D
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        viewportBox2D = new FitViewport(SCREEN_WIDTH * worldScale, SCREEN_HEIGHT * worldScale); //FitViewport or ExtendViewport
        World.setVelocityThreshold(0.1f);
        listenerClass = new ListenerClass(blocksActorToRemove,plusCircleActorToRemove,triangleActorToRemove);
        world.setContactListener(listenerClass);
        addBodyBox2D();
    }

    private void renderCameraForBox2D() {
        viewportBox2D.apply();
        world.step(1 / 60f, 6, 2);
        debugRenderer.render(world, viewportBox2D.getCamera().combined);
    }

    private void resizeCameraForBox2D(int width, int height) {
        viewportBox2D.update(width, height, true);
    }


    private void addBodyBox2D() {
        b2Screen = new B2Screen(world, worldScale);
    }
    ////////

    private void removeBlocksActorAndAddPoint() {
        if (blocksActorToRemove != null && blocksActorToRemove.size > 0) {
            for (Iterator<BlockActor> it = blocksActorToRemove.iterator(); it.hasNext(); ) {
                BlockActor actor = it.next();
                if (LabelNum.getNum(actor.label) > 1) {
                    LabelNum.removeOne(actor.label);
                } else {
                    score += actor.point;
                    world.destroyBody(actor.b2Block.getBody());
                    actor.remove();
                }
                it.remove();
            }
        }
    }


    private void removeTriangleActorAndAddPoint() {
        if (triangleActorToRemove != null && triangleActorToRemove.size > 0) {
            for (Iterator<TriangleActor> it = triangleActorToRemove.iterator(); it.hasNext(); ) {
                TriangleActor actor = it.next();
                if (LabelNum.getNum(actor.label) > 1) {
                    LabelNum.removeOne(actor.label);
                } else {
                    score += actor.point;
                    world.destroyBody(actor.b2Triangle.getBody());
                    actor.remove();
                }
                it.remove();
            }
        }
    }

    private void removePlusCircleActorAndAddNumBalls() {
        if (plusCircleActorToRemove != null && plusCircleActorToRemove.size > 0) {
            for (Iterator<PlusCircleActor> it = plusCircleActorToRemove.iterator(); it.hasNext(); ) {
                PlusCircleActor actor = it.next();
                numBalls++;
                world.destroyBody(actor.b2PlusCircle.getBody());
                actor.remove();
                it.remove();
            }
        }
    }


    private void addBlockActorIfStop() {
        if (GameState.getState() == GameState.STOP_BLOCKS) {
            System.out.println("Get stop blocks");
            groupActors.addRandomRow(5, 60, 60);
            GameState.setState(GameState.READY_TO_SHOOT);
            System.out.println("Set ready to shoot");
        }
    }

    private void checkGameOver() {
        for (Actor actor : groupActors.getChildren()) {
//            System.out.println(actor.getY() + groupBlocks.getY());
            if (actor.getY() + groupActors.getY() < 140) {
                GameState.setState(GameState.GAME_OVER);
                main.setScreen(new GameOverScreen(main, score, numBalls,ballActor.getX(),ballActor.getY()));
            }
        }
    }


    @Override
    public BlockActor createBlockActor() {
        return new BlockActor(imageBlockActor,
                new Label("", skin, "f24_o"),
                0,
                0,
                60,
                60,
                world,
                worldScale);
    }

    @Override
    public PlusCircleActor createPlusCircleActor() {
        return new PlusCircleActor(imagePlusCircleActor, 0, 0, 17, world, worldScale);
    }

    @Override
    public TriangleActor createTriangleActor() {
        return new TriangleActor(imageTriangleActor,
                new Label("", skin, "f24_o"),
                0,
                0,
                60,
                60,
                new Vector2(60,0),
                new Vector2(60,60),
                new Vector2(0,60),
                world,
                worldScale);
    }
}

