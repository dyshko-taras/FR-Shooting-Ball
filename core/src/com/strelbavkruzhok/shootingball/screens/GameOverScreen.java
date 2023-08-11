package com.strelbavkruzhok.shootingball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.strelbavkruzhok.shootingball.Main;
import com.strelbavkruzhok.shootingball.actors.ArrowActor;
import com.strelbavkruzhok.shootingball.actors.BallActor;
import com.strelbavkruzhok.shootingball.actors.GroupActors;
import com.strelbavkruzhok.shootingball.tools.GameSettings;
import com.strelbavkruzhok.shootingball.tools.Localization;

public class GameOverScreen implements Screen {

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
    private Label labelGameOver;
    private ImageTextButton playAgainButton;
    private Label labelNumBalls;
    private Image settingButton;
    private Image achievementsButton;

    //Game
    private int score;
    private int numBalls;
    private Image imageBallActor;
    private float XImageBallActor;
    private float YImageBallActor;

    public GameOverScreen(Main main, int score, int numBalls, float XImageBallActor, float YImageBallActor) {
        this.main = main;
        this.score = score;
        this.numBalls = numBalls;
        this.XImageBallActor = XImageBallActor;
        this.YImageBallActor = YImageBallActor;
        setMaximumScore();
    }

    public void show() {
        showCameraAndStage();

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
        labelGameOver = new Label("GAME\n"
                        + "OVER", skin);
        labelGameOver.setAlignment(Align.center);
        table.add(labelGameOver).padTop(122.0f).expandX().align(Align.top).colspan(2);

        table.row();
        labelScore = new Label("SCORE: 0", skin, "f24_w_o");
        labelScore.setAlignment(Align.center);
        table.add(labelScore).padTop(50.0f).expandX().align(Align.top).colspan(2);

        table.row();
        playAgainButton = new ImageTextButton("PLAY AGAIN", skin);
        table.add(playAgainButton).padTop(50.0f).expandX().align(Align.top).colspan(2);

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
        initLocalizedUI();
        setData();
        addMyActors();
    }

    private void setClickListeners() {
        returnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new MainMenuScreen(main));
            }
        });

        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new SettingScreen(main));
            }
        });

        achievementsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new AchievScreen(main));
            }
        });

        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new GameScreen(main));
            }
        });
    }

    public void render(float delta) {
        renderCamera();
    }

    public void resize(int width, int height) {
        resizeCamera(width, height);
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
        viewportBackground = new ExtendViewport(SCREEN_WIDTH, SCREEN_HEIGHT); // ExtendViewport or FitViewport
        stageBackground = new Stage(viewportBackground);

        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);//only FitViewport
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
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

    private void initLocalizedUI() {
        labelGameOver.setText(Localization.getLoc(Localization.GAME_OVER));
        labelScore.setText(Localization.getLoc(Localization.SCORE)  + score);
        playAgainButton.setText(Localization.getLoc(Localization.PLAY_AGAIN));
    }

    private void setData() {
        labelNumBalls.setText(numBalls + "x");
    }

    private void setMaximumScore() {
        if (GameSettings.getBestScore() < score) {
            GameSettings.setBestScore(score);
        }
    }

    public void addMyActors() {
        imageBallActor = new Image(skin, "ball");
        imageBallActor.setBounds(XImageBallActor, YImageBallActor, 40, 40);
        stage.addActor(imageBallActor);
    }
}
