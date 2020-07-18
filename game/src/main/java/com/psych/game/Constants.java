package com.psych.game;

import com.psych.game.model.GameMode;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final int MAX_ROUNDS =100;
    public static final int MAX_QUESTION_LENGTH=1000;
    public static final int MAX_ANSWER_LENGTH=1000;
    public static final int MAX_QUESTIONS_TO_READ=100;
    public static Map<String , GameMode>qa_files=new HashMap<>();
    static {
//        qa_files.put("qa_facts.txt",GameMode.IS_THIS_A_FACT);
//        qa_files.put("qa_unscramble.txt",GameMode.UNSCRAMBLE);
//        qa_files.put("qa_words.txt",GameMode.WORD_UP);
    }
}
