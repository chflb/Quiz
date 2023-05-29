package com.example.quizgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "questions_table";
    private static final String ID = "id";
    public static final String QUESTION = "question";
    public static final String COLUMN_OPTION1 = "option1";
    public static final String COLUMN_OPTION2 = "option2";
    public static final String COLUMN_OPTION3 = "option3";
    public static final String COLUMN_ANSWER = "reponse";

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
               TABLE_NAME + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QUESTION + " TEXT, " +
               COLUMN_OPTION1 + " TEXT, " +
              COLUMN_OPTION2 + " TEXT, " +
                COLUMN_OPTION3 + " TEXT, " +
              COLUMN_ANSWER + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
          addQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void addQuestions() {
        Question q1 = new Question("Quel pays traversent les deux fleuves le Tigre et l’Euphrate?", "Egypt", "Le Kenya", "L'Irak", "L'Irak");
        this.addQuestion(q1);
        Question q2 = new Question("Quel pays est surnommé le Toit du monde ?", "LA Chine", "Le Japon", "Le Tibet", "Le Tibet");
        this.addQuestion(q2);
        Question q3 = new Question("Tchernobyl est situé en :", "Ukraine", "Russie", "Biélorussie", "Ukraine");
        this.addQuestion(q3);
        Question q5 = new Question("Où se trouvent les chutes d’eau les plus hautes du monde ?", "Canada", "Les Etats-Unies", "Vénézuela", "Vénézuela");
        this.addQuestion(q5);
        Question q6 = new Question("Quelle est la capitale de l’Arabie saoudite ?", "DubaÏ", "Riyad", "Makka", "Riyad");
        this.addQuestion(q6);
        Question q7 = new Question("Quel est le plus grand pays producteur de café au monde ?", "Brazil", "L'inde", "Myanmar", "Brazil");
        this.addQuestion(q7);
        Question q8 = new Question("Quel est le plus long fleuve du monde ?", "Ganga", "Amazon", "Nile", "Nile");
        this.addQuestion(q8);
        Question q9 = new Question("Quel pays est connu comme le pays du cuivre ?", "Somalie", "Cameroun", "Zambia", "Zambia");
        this.addQuestion(q9);
        Question q10 = new Question("Quelle est la plus ancienne ville connue du monde?", "La Rome", "Damas", "Jerusalem", "Damas");
        this.addQuestion(q10);

        Question q12 = new Question("Quelle ville est connue comme la ville des canaux ? ?", "Paris", "Venice", "London", "Venice");
        this.addQuestion(q12);
        Question q13 = new Question("L'Australie a été découverte par ?", "James Cook", "Columbus", "Magallan", "James Cook");
        this.addQuestion(q13);
        Question q14 = new Question("The national flower of Britain is ?", "Lily", "Rose", "Lotus", "Rose");
        this.addQuestion(q14);
        Question q15 = new Question("The red cross was founded by ?", "Gullivar Crossby", "Alexandra Maria Lara", "Jean Henri Durant", "Jean Henri Durant");
        this.addQuestion(q15);
        Question q16 = new Question("Which place is known as the roof of the world ?", "Alphs", "Tibet", "Nepal", "Tibet");
        this.addQuestion(q16);
        Question q17 = new Question("Who invented washing machine ?", "James King", "Alfred Vincor", "Christopher Marcossi", "James King");
        this.addQuestion(q17);
        Question q18 = new Question("Who won the Football world Cup in 2014 ?", "Italy", "Argentina", "Germany", "Germany");
        this.addQuestion(q18);
        Question q19 = new Question("Who won the Cricket World cup in 2011 ?", "Australia", "India", "England", "India");
        this.addQuestion(q19);
        Question q20 = new Question("The number regarded as the lucky number in Italy is ?", "13", "7", "9", "13");
        this.addQuestion(q20);

    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QUESTION, question.getQuestion());
        cv.put(COLUMN_OPTION1, question.getOption1());
        cv.put(COLUMN_OPTION2, question.getOption2());
        cv.put(COLUMN_OPTION3, question.getOption3());
        cv.put(COLUMN_ANSWER, question.getReponse());
        db.insert(TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(1));
                question.setOption1(c.getString(2));
                question.setOption2(c.getString(3));
                question.setOption3(c.getString(4));
                question.setReponse(c.getString(5));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}