package com.strelbavkruzhok.shootingball.tools;

public class Localization {

    public static final int PLAY = 0;
    public static final int SETTING = 1;
    public static final int ACHIEVEMENTS = 2;
    public static final int PLAY_10_TIMES = 3;
    public static final int BEST_SCORE_20 = 4;
    public static final int BEST_SCORE_100 = 5;
    public static final int GAME_OVER = 6;
    public static final int SCORE = 7;
    public static final int PLAY_AGAIN = 8;


    public static final String[] EN = {
            "PLAY",
            "SETTING",
            "ACHIEVEMENTS",
            "PLAY 10 TIMES",
            "BEST SCORE 20",
            "BEST SCORE 100",
            "GAME\nOVER",
            "SCORE: ",
            "PLAY AGAIN",
    };

    public static final String[] BR = {
            "JOGUE",
            "CONFIGURAÇÃO",
            "ACHIEVEMENTOS",
            "JOGUE 10 VEZES",
            "MELHOR\nESCORDO 20",
            "MELHOR\nESCORDO 100",
            "GANHO DE\nJOGO",
            "PONTUAÇÃO: ",
            "JOGUE CONTINUA",
    };

    public static String[] currentLanguage = EN;// def

    public static void setLanguage(String languageCode) {
        if (languageCode.equals("br")) {
            currentLanguage = BR;
        } else if (languageCode.equals("en")) {
            currentLanguage = EN;
        }
    }

    public static String getLoc(int index) {
        if (index >= 0 && index < currentLanguage.length) {
            return currentLanguage[index];
        } else {
            return "Localized text not found";
        }
    }

}

