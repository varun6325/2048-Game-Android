package com.example.varkalra.a2048;

import android.util.Pair;

import java.util.Random;
import java.util.Vector;

/**
 * Created by varkalra on 3/19/2018.
 */

public class State {
    private String[][] mGrid;// = new String[CONSTANTS.GRID_SIZE][CONSTANTS.GRID_SIZE];
    private Integer mScore;
    private Boolean mLost;
    private Boolean mWon;
    /* isUpdated indicates if the state has been updated as compared to the previous state. this helps us to maintain that we update the UI and make an
    entry into command list only when any change is made in the grid*/
    private Boolean mIsUpdated;
    State(){
        mScore = 0;
        mGrid =  new String[CONSTANTS.GRID_SIZE][CONSTANTS.GRID_SIZE];
        Random rand = new Random();
        int ri = rand.nextInt(4);
        int rj = rand.nextInt(4);
        mGrid[ri][rj] = "2";
        for(int i = 0; i < CONSTANTS.GRID_SIZE; i++){
            for(int j = 0; j < CONSTANTS.GRID_SIZE; j++){
                if(i==ri && j == rj )
                    continue;
                else
                    mGrid[i][j] = "";
            }
        }
        mLost = false;
        mWon = false;
        mIsUpdated = true;
    }
    State(String[][] grid, int score, Boolean Lost, Boolean Won, Boolean isUpdated){
        mGrid = grid;
        mScore = score;
        mLost = Lost;
        mWon = Won;
        mIsUpdated = isUpdated;
    }

    void updateScore(int score){
        mScore = score;
    }
    void updateGrid(String[][] grid){
        mGrid = grid;
    }
    String[][] getGrid(){
        return mGrid;
    }
    int getScore(){
        return mScore;
    }
    Boolean isupdatedFromPreviousState(){
        return mIsUpdated;
    }

    State swipeLeft(){
        String[][] grid = new String[CONSTANTS.GRID_SIZE][CONSTANTS.GRID_SIZE];
        for (int i = 0; i < mGrid.length; i++) {
            System.arraycopy(mGrid[i], 0, grid[i], 0, mGrid[0].length);
        }
        Integer score = mScore;
        Boolean isUpdated = false;
        int i, j, k;
        int row = 0;
        int o;
        while(row < CONSTANTS.GRID_SIZE){
            i = 0;
            j = -1;k = -1;
            o = 0;
            while(i < CONSTANTS.GRID_SIZE){
                if(grid[row][i].equals("")) {
                    i++;
                    continue;
                }
                if(j == -1)
                    j = i;
                else if(k == -1)
                    k = i;
                if(j != -1 && k != -1){
                    int val1 = Integer.parseInt(grid[row][j]);
                    int val2 = Integer.parseInt(grid[row][k]);
                    if(val1 == val2){
                        int out = val1+val2;
                        grid[row][o] = Integer.toString(out);
                        j = -1;k = -1;
                        score += out;
                        isUpdated = true;
                    }else{
                        grid[row][o] = Integer.toString(val1);
                        if(o != j)
                            isUpdated = true;
                        j = k;
                        k = -1;
                    }
                    o++;
                }
                i++;
            }
            if(j != -1){
                if(o != j)
                    isUpdated = true;
                grid[row][o] = grid[row][j];
                o++;
            }
            while(o < CONSTANTS.GRID_SIZE){
                grid[row][o] = "";
                o++;
            }

            row++;
        }
        Boolean[] lostWon = {false, false};
        findRandPosAndFill(grid, lostWon);
        return new State(grid, score, lostWon[0], lostWon[1], isUpdated);
    }
    State swipeRight(){
        String[][] grid = new String[CONSTANTS.GRID_SIZE][CONSTANTS.GRID_SIZE];
        for (int i = 0; i < mGrid.length; i++) {
            System.arraycopy(mGrid[i], 0, grid[i], 0, mGrid[0].length);
        }
        int score = mScore;
        int i, j, k;
        int row = 0;
        int o ;
        Boolean isUpdated = false;
        while(row < CONSTANTS.GRID_SIZE){
            i = CONSTANTS.GRID_SIZE-1;
            j = -1;k = -1;
            o = CONSTANTS.GRID_SIZE-1;
            while(i >= 0){
                if(grid[row][i].equals("")) {
                    i--;
                    continue;
                }
                if(j == -1)
                    j = i;
                else if(k == -1)
                    k = i;
                if(j != -1 && k != -1){
                    int val1 = Integer.parseInt(grid[row][j]);
                    int val2 = Integer.parseInt(grid[row][k]);
                    if(val1 == val2){
                        isUpdated = true;
                        int out = val1+val2;
                        grid[row][o] = Integer.toString(out);
                        j = -1;k = -1;
                        score += out;
                    }else{
                        if(o != j)
                            isUpdated = true;
                        grid[row][o] = Integer.toString(val1);
                        j = k;
                        k = -1;
                    }
                    o--;
                }
                i--;
            }
            if(j != -1){
                if(o != j)
                    isUpdated = true;
                grid[row][o] = grid[row][j];
                o--;
            }
            while(o >= 0){
                grid[row][o] = "";
                o--;
            }

            row++;
        }
        Boolean[] lostWon = {false, false};
        findRandPosAndFill(grid, lostWon);
        return new State(grid, score, lostWon[0], lostWon[1], isUpdated);
    }
    State swipeUp(){
        String[][] grid = new String[CONSTANTS.GRID_SIZE][CONSTANTS.GRID_SIZE];
        for (int i = 0; i < mGrid.length; i++) {
            System.arraycopy(mGrid[i], 0, grid[i], 0, mGrid[0].length);
        }
        int score = mScore;
        Boolean isUpdated = false;
        int i, j, k;
        int col = 0;
        int o ;
        while(col < CONSTANTS.GRID_SIZE){
            i = 0;
            j = -1;k = -1;
            o = 0;
            while(i < CONSTANTS.GRID_SIZE){
                if(grid[i][col].equals("")) {
                    i++;
                    continue;
                }
                if(j == -1)
                    j = i;
                else if(k == -1)
                    k = i;
                if(j != -1 && k != -1){
                    int val1 = Integer.parseInt(grid[j][col]);
                    int val2 = Integer.parseInt(grid[k][col]);
                    if(val1 == val2){
                        int out = val1+val2;
                        isUpdated = true;
                        grid[o][col] = Integer.toString(out);
                        j = -1;k = -1;
                        score += out;
                    }else{
                        if(o != j)
                            isUpdated = true;
                        grid[o][col] = Integer.toString(val1);
                        j = k;
                        k = -1;
                    }
                    o++;
                }
                i++;
            }
            if(j != -1){
                if(o != j)
                    isUpdated = true;
                grid[o][col] = grid[j][col];
                o++;
            }
            while(o < CONSTANTS.GRID_SIZE){
                grid[o][col] = "";
                o++;
            }

            col++;
        }
        Boolean[] lostWon = {false, false};
        findRandPosAndFill(grid, lostWon);
        return new State(grid, score, lostWon[0], lostWon[1], isUpdated);
    }
    State swipeDown(){
        String[][] grid = new String[CONSTANTS.GRID_SIZE][CONSTANTS.GRID_SIZE];
        for (int i = 0; i < mGrid.length; i++) {
            System.arraycopy(mGrid[i], 0, grid[i], 0, mGrid[0].length);
        }
        int score = mScore;
        Boolean isUpdated = false;
        int i, j, k;
        int col = 0;
        int o;
        while(col< CONSTANTS.GRID_SIZE){
            i = CONSTANTS.GRID_SIZE-1;
            j = -1;k = -1;
            o = CONSTANTS.GRID_SIZE-1;
            while(i >= 0){
                if(grid[i][col].equals("")) {
                    i--;
                    continue;
                }
                if(j == -1)
                    j = i;
                else if(k == -1)
                    k = i;
                if(j != -1 && k != -1){
                    int val1 = Integer.parseInt(grid[j][col]);
                    int val2 = Integer.parseInt(grid[k][col]);
                    if(val1 == val2){
                        int out = val1+val2;
                        isUpdated = true;
                        grid[o][col] = Integer.toString(out);
                        j = -1;k = -1;
                        score += out;
                    }else{
                        grid[o][col] = Integer.toString(val1);
                        if(o != j)
                            isUpdated = true;
                        j = k;
                        k = -1;
                    }
                    o--;
                }
                i--;
            }
            if(j != -1){
                if(o != j)
                    isUpdated = true;
                grid[o][col] = grid[j][col];
                o--;
            }
            while(o >= 0){
                grid[o][col] = "";
                o--;
            }
            col++;
        }
        Boolean[] lostWon = {false, false};
        findRandPosAndFill(grid, lostWon);
        return new State(grid, score, lostWon[0], lostWon[1], isUpdated);
    }
    private void findRandPosAndFill(String[][] grid, Boolean[] lostWon){
        Vector<Pair<Integer, Integer>> vector = new Vector<Pair<Integer, Integer>>();
        for(int i = 0; i < CONSTANTS.GRID_SIZE; i++){
            for(int j = 0; j < CONSTANTS.GRID_SIZE; j++){
                if(grid[i][j].equals("")){
                    vector.add(new Pair<Integer, Integer>(i, j));
                }else if(grid[i][j].equals(CONSTANTS.WINNING_SCORE)){
                    lostWon[1] = true;
                }
            }
        }
        if(vector.size() == 0) {
            lostWon[0] = true;
            return;
        }
        int rand = new Random().nextInt(vector.size());
        Pair<Integer, Integer> pair = vector.get(rand);
        grid[pair.first][pair.second] = "2";
    }

    //returns false if no empty position found else finds and fills the position
    Boolean findRandPosAndFill(){
        Vector<Pair<Integer, Integer>> vector = new Vector<Pair<Integer, Integer>>();
        for(int i = 0; i < CONSTANTS.GRID_SIZE; i++){
            for(int j = 0; j < CONSTANTS.GRID_SIZE; j++){
                if(mGrid[i][j].equals("")){
                    vector.add(new Pair<Integer, Integer>(i, j));
                }else if(mGrid[i][j].equals(CONSTANTS.WINNING_SCORE)){
                    mWon = true;
                }
            }
        }
        if(vector.size() == 0) {
            mLost = true;
            return false;
        }
        int rand = new Random().nextInt(vector.size());
        Pair<Integer, Integer> pair = vector.get(rand);
        mGrid[pair.first][pair.second] = "2";
        return true;
    }
    Boolean hasLost(){

        return mLost;
    }
    Boolean hasWon(){
        return mWon;
    }

}
