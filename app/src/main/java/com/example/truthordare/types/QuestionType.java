package com.example.truthordare.types;

import com.example.truthordare.R;
import com.example.truthordare.utils.StaticEnum;

public class QuestionType extends StaticEnum {

    public static final QuestionType DARE = new QuestionType("DARE", "DARE", "Dare", R.string.title_add_dares);
    public static final QuestionType TRUTH = new QuestionType("TRUTH", "TRUTH", "Truth", R.string.title_add_truths);
    private static final QuestionType[] ALL_TYPES = new QuestionType[]{TRUTH, DARE};
    private int lbl;
    private boolean shouldShow = Boolean.TRUE.booleanValue();

    private QuestionType(String name, String value, String description, int lbl) {
        super(name, value, description);
        this.lbl = lbl;
    }

    public static QuestionType[] getAllTypes() {
        return ALL_TYPES;
    }

    public static QuestionType forName(String name) {
        return (QuestionType) StaticEnum.forName(getAllTypes(), name);
    }

    public int getLbl() {
        return this.lbl;
    }
}
