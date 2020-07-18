package com.psych.game;

import com.psych.game.config.ApplicationContextProvider;
import com.psych.game.model.EllenAnswer;
import com.psych.game.model.GameMode;
import com.psych.game.model.Question;
import com.psych.game.repository.EllenAnswerRepository;
import com.psych.game.repository.QuestionRepository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Utils {
    private static List<String> wordsList;
    private static Map<String,Integer>wordIndices;
    private static EllenAnswerRepository ellenAnswerRepository;
    private static QuestionRepository questionRepository;

    static{
        ellenAnswerRepository=(EllenAnswerRepository) ApplicationContextProvider.getApplicationContext().getBean("ellenAnswerRepository");
        questionRepository=(QuestionRepository)ApplicationContextProvider.getApplicationContext().getBean("questionRepository");
        // read words into wordsList
        wordsList=new ArrayList<>();
        wordIndices=new HashMap<>();
        try {

            BufferedReader br=new BufferedReader(new FileReader("words.txt"));
            String word;
            do{
                word=br.readLine();
                wordsList.add(word);
                wordIndices.put(word,wordsList.size()-1);
            }while(word!=null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static List<Pair<String,String>> readQAFile(String filename) throws FileNotFoundException {
        BufferedReader br=new BufferedReader(new FileReader(filename));
        String question,answer;
        List<Pair<String,String>>questionAnswer=new ArrayList<>();
       try {
           do {
               question = br.readLine();
               answer = br.readLine();
               if(question!=null && answer!=null)
               if(question.length()>Constants.MAX_QUESTION_LENGTH-1 || answer.length()>Constants.MAX_ANSWER_LENGTH-1){
                   continue;
               }
               if(question!=null && answer!=null)
               questionAnswer.add(new Pair<>(question,answer));

           } while (question != null && answer != null);
       }catch (IOException ignored){

       }
       return questionAnswer;
    }

    public static String getSecretCodeFromId(Long id){
        int base=wordsList.size();
        String code="";
        while(id>0){
            code+=wordsList.get((int)(id%base))+" ";
            id/=base;

        }
        return code;

    }
    public static long getGameIdFromSecretCode(String code){
        List<String> words= Arrays.asList(code.split(" "));
        long ans=0;
        int base=wordsList.size();
        for(String word:words){
            int index=wordIndices.get(word);
            ans=ans*base+index;
        }
        return ans;
    }

    public static Question getRandomQuestion(GameMode gameMode) {


        return questionRepository.getRandomQuestion(gameMode.getId());
    }

    public static EllenAnswer getRandomEllenAnswer(Question question) {
        return ellenAnswerRepository.getRandomAnswer(question.getId());
    }
}
