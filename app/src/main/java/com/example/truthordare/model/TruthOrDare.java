package com.example.truthordare.model;

import com.example.truthordare.types.GameMode;
import com.example.truthordare.types.QuestionType;

public class TruthOrDare {

    public static final String KEY_ID = "_id";
    public static final String KEY_MODE = "_gameMode";
    public static final String KEY_QTYPE = "question_type";
    public static final String KEY_QUE = "question";
    public static final String KEY_USER = "add_by_user";

    public static final String[] COLS = new String[]{KEY_ID, KEY_QUE, KEY_QTYPE, KEY_USER, KEY_MODE};
    public GameMode gameMode;
    public int id;
    public Integer isAddedByUser;
    public String question;
    public QuestionType questionType;

    public TruthOrDare(String question, QuestionType questionType, Integer isAddedByUser, GameMode gameMode) {
        this.question = question;
        this.questionType = questionType;
        this.isAddedByUser = isAddedByUser;
        this.gameMode = gameMode;
    }
}
