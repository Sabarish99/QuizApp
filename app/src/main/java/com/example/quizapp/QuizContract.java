package com.example.quizapp;

import android.provider.BaseColumns;

public final class QuizContract {
    private  QuizContract(){ }

    public static class CategoriesTable implements BaseColumns
    {
        public static final String Table_name = "quiz_categories";
        public static final String Column_name = "name";
    }

    public static class QuestionTable implements BaseColumns
    {
        public static final String Table_name = "quiz_questions";
        public static final String Coln_Question = "question";
        public static final String Coln_Option1= "option1";
        public static final String Coln_Option2= "option2";
        public static final String Coln_Option3= "option3";
        public static final String Coln_Option4= "option4";
        public static final String Coln_Answer_Nr = "answer_nr";
        public static final String Coln_Difficulty = "difficulty";
        public static final String Coln_Category_ID = "category_id";
    }

}
