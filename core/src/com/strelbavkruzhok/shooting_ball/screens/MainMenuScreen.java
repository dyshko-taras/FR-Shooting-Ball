package com.strelbavkruzhok.shooting_ball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.strelbavkruzhok.shooting_ball.Main;
import com.strelbavkruzhok.shooting_ball.tools.Localization;

public class MainMenuScreen implements Screen {

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
    private Image iconBall;
    private Label labelGame;
    private ImageTextButton playButton;
    private Image settingButton;
    private Image achievementsButton;

    public MainMenuScreen(Main main) {
        this.main = main;
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

        iconBall = new Image(skin, "iconBall");
        iconBall.setScaling(Scaling.fit);
        table.add(iconBall).padTop(90.0f).expandX().align(Align.top).colspan(2);

        table.row();
        labelGame = new Label("SHOOTING\n"
                        + "BALL", skin, "f48_w_o");
        labelGame.setAlignment(Align.center);
        table.add(labelGame).padTop(20.0f).expandX().align(Align.top).colspan(2);

        table.row();
        playButton = new ImageTextButton("PLAY", skin);
        table.add(playButton).padTop(128.0f).expandX().align(Align.top).colspan(2);

        table.row();
        settingButton = new Image(skin, "settings");
        table.add(settingButton).padLeft(24.0f).padBottom(24.0f).expand().align(Align.bottomLeft);

        achievementsButton = new Image(skin, "achievements");
        table.add(achievementsButton).padRight(28.0f).padBottom(28.0f).expand().align(Align.bottomRight);

        container.setActor(table);
        mainTable.add(container);
        stage.addActor(mainTable);

        addBackground();
        setClickListeners();
        initLocalizedUI();
    }

    private void setClickListeners() {
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen(new GameScreen(main));
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
        playButton.setText(Localization.getLoc(Localization.PLAY));
    }
}
