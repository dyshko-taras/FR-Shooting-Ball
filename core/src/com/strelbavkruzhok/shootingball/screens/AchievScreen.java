package com.strelbavkruzhok.shootingball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.strelbavkruzhok.shootingball.Main;
import com.strelbavkruzhok.shootingball.tools.GameSettings;
import com.strelbavkruzhok.shootingball.tools.Localization;

public class AchievScreen implements Screen {

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
    private Label labelAchievements;
    private ProgressBar progressBar1;
    private Label labelProgressBar1Num;
    private Label labelProgressBar1Text;
    private ProgressBar progressBar2;
    private Label labelProgressBar2Num;
    private Label labelProgressBar2Text;
    private ProgressBar progressBar3;
    private Label labelProgressBar3Num;
    private Label labelProgressBar3Text;
    private Image settingButton;
    private Image achievementsButton;


    public AchievScreen(Main main) {
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

        returnButton = new Image(skin, "return");
        returnButton.setScaling(Scaling.fit);
        table.add(returnButton).padLeft(24.0f).padTop(30.0f).expandX().align(Align.topLeft).colspan(2);

        table.row();
        labelAchievements = new Label("ACHIEVEMENTS", skin, "f32_w_o");
        table.add(labelAchievements).padTop(27.0f).expandX().align(Align.top).colspan(2);

        table.row();
        Stack stack = new Stack();

        progressBar1 = new ProgressBar(0.0f, 10.0f, 1.0f, false, skin);
        stack.addActor(progressBar1);

        Table table2 = new Table();

        labelProgressBar1Num = new Label("10/10", skin, "f40_w");
        labelProgressBar1Num.setAlignment(Align.center);
        table2.add(labelProgressBar1Num).padLeft(21.0f).expandY().align(Align.left);

        labelProgressBar1Text = new Label("PLAY 10 TIMES", skin, "f20_w");
        labelProgressBar1Text.setAlignment(Align.center);
        table2.add(labelProgressBar1Text).expand();
        stack.addActor(table2);
        table.add(stack).padTop(82.0f).expandX().align(Align.top).minWidth(305.0f).minHeight(80.0f).maxWidth(305.0f).maxHeight(80.0f).colspan(2);

        table.row();
        stack = new Stack();

        progressBar2 = new ProgressBar(0.0f, 20.0f, 1.0f, false, skin);
        stack.addActor(progressBar2);

        table2 = new Table();

        labelProgressBar2Num = new Label("22/20", skin, "f40_w");
        labelProgressBar2Num.setAlignment(Align.center);
        table2.add(labelProgressBar2Num).padLeft(21.0f).expandY().align(Align.left);

        labelProgressBar2Text = new Label("BEST SCORE 20", skin, "f20_w");
        labelProgressBar2Text.setAlignment(Align.center);
        table2.add(labelProgressBar2Text).expand();
        stack.addActor(table2);
        table.add(stack).padTop(50.0f).expandX().align(Align.top).minWidth(305.0f).minHeight(80.0f).maxWidth(305.0f).maxHeight(80.0f).colspan(2);

        table.row();
        stack = new Stack();

        progressBar3 = new ProgressBar(0.0f, 100.0f, 1.0f, false, skin);
        stack.addActor(progressBar3);

        table2 = new Table();

        labelProgressBar3Num = new Label("22/100", skin, "f40_w");
        labelProgressBar3Num.setAlignment(Align.center);
        table2.add(labelProgressBar3Num).padLeft(11.0f).expandY();

        labelProgressBar3Text = new Label("BEST SCORE 100", skin, "f20_w");
        labelProgressBar3Text.setAlignment(Align.center);
        table2.add(labelProgressBar3Text).expand();
        stack.addActor(table2);
        table.add(stack).padTop(50.0f).expandX().align(Align.top).minWidth(305.0f).minHeight(80.0f).maxWidth(305.0f).maxHeight(80.0f).colspan(2);

        table.row();
        settingButton = new Image(skin, "settings");
        table.add(settingButton).padLeft(24.0f).padBottom(24.0f).expand().align(Align.bottomLeft);

        achievementsButton = new Image(skin, "achievements");
        table.add(achievementsButton).padRight(28.0f).padBottom(28.0f).expand().align(Align.bottomRight);

        container.setActor(table);
        mainTable.add(container);
        stage.addActor(mainTable);

        addBackground();
        initLocalizedUI();
        setClickListeners();
        setData();
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
        labelAchievements.setText(Localization.getLoc(Localization.ACHIEVEMENTS));
        labelProgressBar1Text.setText(Localization.getLoc(Localization.PLAY_10_TIMES));
        labelProgressBar2Text.setText(Localization.getLoc(Localization.BEST_SCORE_20));
        labelProgressBar3Text.setText(Localization.getLoc(Localization.BEST_SCORE_100));
    }

    private void setData() {
        progressBar1.setValue(GameSettings.getPlayGameTimes());
        labelProgressBar1Num.setText(GameSettings.getPlayGameTimes() + "/10");

        progressBar2.setValue(GameSettings.getBestScore());
        labelProgressBar2Num.setText(GameSettings.getBestScore() + "/20");

        progressBar3.setValue(GameSettings.getBestScore());
        labelProgressBar3Num.setText(GameSettings.getBestScore() + "/100");
    }


}
