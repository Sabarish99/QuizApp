package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.quizapp.QuizContract.*;
import java.util.*;

public class QuizDb extends SQLiteOpenHelper {

    private static final String Database_Name = "QuizApp.db";
    private static final int Database_Version = 1;
    private static QuizDb instance;
    private SQLiteDatabase db;

    private QuizDb(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    public static synchronized QuizDb getInstance(Context context) {
        if (instance == null)
            instance = new QuizDb(context.getApplicationContext());
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_Create_Categories_Table = " CREATE TABLE " +
                CategoriesTable.Table_name + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.Column_name + " TEXT  NOT NULL " + " )";

        final String SQL_Create_Questions_Table = " CREATE TABLE " +
                QuestionTable.Table_name + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.Coln_Question + " TEXT NOT NULL, " +
                QuestionTable.Coln_Option1 + " TEXT NOT NULL, " +
                QuestionTable.Coln_Option2 + " TEXT NOT NULL, " +
                QuestionTable.Coln_Option3 + " TEXT NOT NULL, " +
                QuestionTable.Coln_Option4 + " TEXT NOT NULL, " +
                QuestionTable.Coln_Answer_Nr + " INTEGER NOT NULL, " +
                QuestionTable.Coln_Difficulty + " TEXT NOT NULL, " +
                QuestionTable.Coln_Category_ID + " INTEGER, " +
                " FOREIGN KEY (" + QuestionTable.Coln_Category_ID + " ) REFERENCES " +
                CategoriesTable.Table_name + " (" + CategoriesTable._ID + " ) " + " ON DELETE CASCADE " + " )";

        db.execSQL(SQL_Create_Categories_Table);
        db.execSQL(SQL_Create_Questions_Table);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.Table_name);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.Table_name);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Data Structures and Algorithms");
        addCategory(c1);


        Category c2 = new Category("DataBase Management Systems ");
        addCategory(c2);


        Category c3 = new Category("Operating Systems");
        addCategory(c3);
    }

    private void addCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.Column_name, category.getName());
        db.insert(CategoriesTable.Table_name, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("The height of a binary tree is the maximum number of edges in any root to leaf path. The maximum number of nodes in a binary tree of height h is", "2^h -1", "2^(h-1) – 1", "2^(h+1) -1", "2*(h+1)", 3, Question.Difficult_Easy, Category.DataStructures_Algorithms);
        addQuestion(q1);

        Question q2 = new Question("The maximum number of binary trees that can be formed with three unlabeled nodes is:", "1", "5", "4", "3", 2, Question.Difficult_Easy, Category.DataStructures_Algorithms);
        addQuestion(q2);

        Question q3 = new Question("Which of the following sorting algorithms has the lowest worst-case complexity?", "Merge sort", "Bubble sort", "Quick sort", "Selection sort", 1, Question.Difficult_Easy, Category.DataStructures_Algorithms);
        addQuestion(q3);

        Question q4 = new Question("The inorder and preorder traversal of a binary tree are d b e a f c g and a b d e c f g, respectively. The postorder traversal of the binary tree is:", "d e b f g c a", "e d b g f c a", "e d b f g c a", "d e f g b c a", 1, Question.Difficult_Medium, Category.DataStructures_Algorithms);
        addQuestion(q4);

        Question q5 = new Question(" Consider a binary max-heap implemented using an array. Which one of the following array represents a binary max-heap?", "25,12,16,13,10,8,14", "25,14,13,16,10,8,12", "25,14,16,13,10,8,12", "25,14,12,13,10,8,16", 3, Question.Difficult_Medium, Category.DataStructures_Algorithms);
        addQuestion(q5);

        Question q6 = new Question("Suppose we have a O(n) time algorithm that finds median of an unsorted array. Now consider a QuickSort implementation where we first find median using the above algorithm, then use median as pivot. What will be the worst case time complexity of this modified QuickSort.", "O(n^2 Logn)", "O(n^2)", "O(n Logn Logn)", "O(nLogn)", 4, Question.Difficult_Hard, Category.DataStructures_Algorithms);
        addQuestion(q6);

        Question q7 = new Question("The recurrence relation capturing the optimal time of the Tower of Hanoi problem with n discs is", "T(n) = 2T(n – 2) + 2", "T(n) = 2T(n – 1) + n", "T(n) = 2T(n/2) + 1", "T(n) = 2T(n – 1) + 1", 4, Question.Difficult_Medium, Category.DataStructures_Algorithms);
        addQuestion(q7);

        Question q8 = new Question("Which of the following is not O(n^2)?", "(15^10) * n + 12099", "n^1.98", "n^3 / (sqrt(n))", "(2^20) * n", 3, Question.Difficult_Hard, 1);
        addQuestion(q8);

        Question q9 = new Question("Consider two strings A = \"qpqrr\" and B = \"pqprqrp\". Let x be the length of the longest common subsequence (not necessarily contiguous) between A and B and let y be the number of such longest common subsequences between A and B. Then x + 10y = ___.", "33", "23", "43", "34", 4, Question.Difficult_Hard, 1);
        addQuestion(q9);

        Question q10 = new Question("Consider a sequence F00 defined as : F00(0) = 1, F00(1) = 1 F00(n) = 10 ∗ F00(n – 1) + 100 F00(n – 2) for n ≥ 2 Then what shall be the set of values of the sequence F00 ?", "(1, 110, 1200)", "(1, 110, 600, 1200)", "(1, 2, 55, 110, 600, 1200)", "(1, 55, 110, 600, 1200)", 1, Question.Difficult_Medium, 1);
        addQuestion(q10);

        Question q11 = new Question("Relation R has eight attributes ABCDEFGH. Fields of R contain only atomic values. F = {CH -> G, A -> BC, B -> CFH, E -> A, F -> EG} is a set of functional dependencies (FDs) so that F+ is exactly the set of FDs that hold for R. How many candidate keys does the relation R have?\n" +
                "A\n", "3", "4", "5", "6", 2, Question.Difficult_Medium, Category.DBMS);
        addQuestion(q11);

        Question q12 = new Question("Which of following is true ?", "Every relation in 3NF is also in BCNF", "A relation R is in 3NF if every non-prime attribute of R is fully functionally dependent on every key of R", "Every relation in BCNF is also in 3NF", "No relation can be in both BCNF and 3NF", 3, Question.Difficult_Medium, Category.DBMS);
        addQuestion(q12);

        Question q13 = new Question("Consider the relation scheme R = {E, F, G, H, I, J, K, L, M, M} and the set of functional dependencies {{E, F} -> {G}, {F} -> {I, J}, {E, H} -> {K, L}, K -> {M}, L -> {N} on R. What is the key for R?", "{E,F}", "{E,F,H}", "{E,F,H,K,L}", "{E}", 2, Question.Difficult_Easy, Category.DBMS);
        addQuestion(q13);

        Question q14 = new Question("Given the basic ER and relational models, which of the following is INCORRECT?", "An attribute of an entity can have more than one value", "An attribute of an entity can be composite", "In a row of a relational table, an attribute can have more than one value", "In a row of a relational table, an attribute can have exactly one value or a NULL value", 3, Question.Difficult_Easy, Category.DBMS);
        addQuestion(q14);

        Question q15 = new Question("Which of the following concurrency control protocols ensure both conflict serialzability and freedom from deadlock? I. 2-phase locking II. Time-stamp ordering", "I only", "II only", "Both I and II", "Neither I or II", 2, Question.Difficult_Hard, Category.DBMS);
        addQuestion(q15);

        Question q16 = new Question("Consider a B+-tree in which the maximum number of keys in a node is 5. What is the minimum number of keys in any non-root node?", "1", "2", "3", "4", 2, Question.Difficult_Easy, 2);
        addQuestion(q16);

        Question q17 = new Question("A FAT (file allocation table) based file system is being used and the total overhead of each entry in the FAT is 4 bytes in size. Given a 100 x 106 bytes disk on which the file system is stored and data block size is 103 bytes, the maximum size of a file that can be stored on this disk in units of 106 bytes is ", "99.55 to 99.65", "100.5 to 101.4", "97.2 to 98.5", "89.1 to 91.2", 1, Question.Difficult_Hard, Category.DBMS);
        addQuestion(q17);

        Question q18 = new Question("Which of the following is correct?", "B-trees are for storing data on disk and B+ trees are for main memory.", "Range queries are faster on B+ trees.", "B-trees are for primary indexes and B+ trees are for secondary indexes.", "\tThe height of a B+ tree is independent of the number of records.", 2, Question.Difficult_Medium, Category.DBMS);
        addQuestion(q18);

        Question q19 = new Question("In a B+ tree, if the search-key value is 12 bytes long, the block size is 1024 bytes and the block pointer is 6 bytes, then the maximum number of keys that can be accommodated in each non-leaf node of the tree is ", "57", "54", "58", "56", 4, Question.Difficult_Hard, 2);
        addQuestion(q19);

        Question q20 = new Question("The relation scheme Student Performance (name, courseNo, rollNo, grade) has the following functional dependencies:\n" +
                "name, courseNo → grade\n" +
                "rollNo, courseNo → grade\n" +
                "name → rollNo\n" +
                "rollNo → name", "2 NF", "3 NF", "BCNF", "4 NF", 2, Question.Difficult_Hard, Category.DBMS);
        addQuestion(q20);

        Question q21 = new Question("A process executes the code\n" +
                "fork();\n" +
                "fork();\n" +
                "fork(); ", "3", "4", "7", "8", 3, Question.Difficult_Easy, Category.OS);
        addQuestion(q21);

        Question q22 = new Question("The time taken to switch between user and kernel modes of execution be t1 while the time taken to switch between two processes be t2. Which of the following is TRUE?", "t1 > t2", "t1 = t2", "t1 <t2", "none", 3, Question.Difficult_Easy, Category.OS);
        addQuestion(q22);

        Question q23 = new Question("A thread is usually defined as a \"light weight process\" because an operating system (OS) maintains smaller data structures for a thread than for a process. In relation to this, which of the following is TRUE?", "On per-thread basis, the OS maintains only CPU register state", "The OS does not maintain a separate stack for each thread", "On per-thread basis, the OS does not maintain virtual memory state", "On per-thread basis, the OS maintains only scheduling and accounting information", 3, Question.Difficult_Medium, Category.OS);
        addQuestion(q23);

        Question q24 = new Question("Which of the following page replacement algorithms suffers from Belady’s anomaly?", "FIFO", "LRU", "OPTIMAL PAGE REPLACEMENT", "BOTH LRU and FIFO", 3, Question.Difficult_Easy, Category.OS);
        addQuestion(q24);

        Question q25 = new Question("A CPU generates 32-bit virtual addresses. The page size is 4 KB. The processor has a translation look-aside buffer (TLB) which can hold a total of 128 page table entries and is 4-way set associative. The minimum size of the TLB tag in bits is:", "11", "13", "15", "20", 3, Question.Difficult_Medium, Category.OS);
        addQuestion(q25);

        Question q26 = new Question("Consider three CPU-intensive processes, which require 10, 20 and 30 time units and arrive at times 0, 2 and 6, respectively. How many context switches are needed if the operating system implements a shortest remaining time first scheduling algorithm? Do not count the context switches at time zero and at the end.", "1", "2", "3", "4", 2, Question.Difficult_Hard, Category.OS);
        addQuestion(q26);

        Question q27 = new Question("A scheduling algorithm assigns priority proportional to the waiting time of a process. Every process starts with priority zero (the lowest priority). The scheduler re-evaluates the process priorities every T time units and decides the next process to schedule. Which one of the following is TRUE if the processes have no I/O operations and all arrive at time zero?", "This algorithm is equivalent to the first-come-first-serve algorithm", "This algorithm is equivalent to the round-robin algorithm.", "This algorithm is equivalent to the shortest-job-first algorithm.", "This algorithm is equivalent to the shortest-remaining-time-first algorithm", 2, Question.Difficult_Hard, Category.OS);
        addQuestion(q27);

        Question q28 = new Question("Which of the following statements are true?\n" +
                "I. Shortest remaining time first scheduling may cause starvation\n" +
                "II. Preemptive scheduling may cause starvation\n" +
                "III. Round robin is better than FCFS in terms of response time", "I only", "I and III only", "II and III only", "I,II,III", 4, Question.Difficult_Hard, 3);
        addQuestion(q28);


    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.Coln_Question, question.getQuestion());
        cv.put(QuestionTable.Coln_Option1, question.getOption1());
        cv.put(QuestionTable.Coln_Option2, question.getOption2());
        cv.put(QuestionTable.Coln_Option3, question.getOption3());
        cv.put(QuestionTable.Coln_Option4, question.getOption4());
        cv.put(QuestionTable.Coln_Answer_Nr, question.getAnswerNr());
        cv.put(QuestionTable.Coln_Difficulty, question.getDiffulty());
        cv.put(QuestionTable.Coln_Category_ID, question.getCategoryID());
        db.insert(QuestionTable.Table_name, null, cv);
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery(" SELECT * FROM " + CategoriesTable.Table_name, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.Column_name)));
                categoryList.add(category);
            } while (c.moveToNext());
        }
        c.close();
        return categoryList;
    }



    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionArrayList = new ArrayList<>();
        db = getReadableDatabase();
        String selection = QuestionTable.Coln_Category_ID + " = ? " + " AND " +
                QuestionTable.Coln_Difficulty + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(QuestionTable.Table_name, null, selection, selectionArgs, null, null, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionTable.Coln_Question)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionTable.Coln_Option1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionTable.Coln_Option2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionTable.Coln_Option3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionTable.Coln_Option4)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionTable.Coln_Answer_Nr)));
                question.setDiffulty(c.getString(c.getColumnIndex(QuestionTable.Coln_Difficulty)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionTable.Coln_Category_ID)));
                questionArrayList.add(question);
            } while (c.moveToNext());

        }
        c.close();
        return questionArrayList;
    }
}
