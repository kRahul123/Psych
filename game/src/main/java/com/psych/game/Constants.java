package com.psych.game;

import com.psych.game.model.GameMode;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final int MAX_ROUNDS =100;
    public static final int MAX_QUESTION_LENGTH=1000;
    public static final int MAX_ANSWER_LENGTH=1000;
    public static final int MAX_QUESTIONS_TO_READ=100;
    public static Map<String , String>qa_files=new HashMap<>();
    static {
        qa_files.put("qa_facts.txt","Is This A Fact?");
        qa_files.put("qa_unscramble.txt","Un-Scramble");
        qa_files.put("qa_word_up.txt","Word Up");
    }
}
