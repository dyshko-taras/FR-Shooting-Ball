package com.strelbavkruzhok.shooting_ball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.strelbavkruzhok.shooting_ball.Main;
import com.strelbavkruzhok.shooting_ball.tools.GameSettings;
import com.strelbavkruzhok.shooting_ball.tools.GameState;

public class GameScreen implements Screen {

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
    private Label numBalls;
    private Image settingButton;
    private Image achievementsButton;

    public GameScreen(Main main) {
        this.main = main;
        GameSettings.setPlayGameTimes(GameSettings.getPlayGameTimes() + 1);
        GameState.setState(GameState.START_GAME);
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
        labelScore = new Label("23", skin, "f48_w_o");
        labelScore.setAlignment(Align.center);
        table.add(labelScore).padTop(-57.0f).expandX().align(Align.top).colspan(2);

        table.row();
        numBalls = new Label("1x", skin, "f24_w");
        table.add(numBalls).padLeft(124.0f).padBottom(7.0f).expand().align(Align.bottomLeft).colspan(2);

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
        ////////////////////////////////
        numBalls.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new GameOverScreen(main,11));
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
}
