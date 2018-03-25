package com.example.varkalra.a2048;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {
    private TextView[][] tvGrid;
    private TextView tvScore;
    private State currentState;
    private int negativeScore;
    private Intermediator intermediator;
    private GestureDetectorCompat mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        negativeScore = 0;
        tvGrid = new TextView[CONSTANTS.GRID_SIZE][CONSTANTS.GRID_SIZE];
        for(int i = 0; i < CONSTANTS.GRID_SIZE; i++){
            for(int j = 0; j < CONSTANTS.GRID_SIZE; j++){
                tvGrid[i][j] = findViewById(CONSTANTS.IDS[i][j]);
            }
        }
        tvScore = findViewById(R.id.tv_score);
        intermediator = new Intermediator();
        currentState = new State();
        mDetector = new GestureDetectorCompat(this, new SwipeGestureListener());
        updateView();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.mDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
    public void onUndo(View v)
    {
        if(CONSTANTS.DEBUG) {
            Toast.makeText(this, "Clicked on Undo", Toast.LENGTH_LONG).show();
        }
        State newState = intermediator.undo();
        if(newState == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Undo");
            alert.setMessage("No more undos can be done");
            alert.setPositiveButton("OK", null);
            alert.show();
        }else {
            int score = currentState.getScore();
            if(score < CONSTANTS.DEDUCTION_PER_UNDO){
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Undo");
                alert.setMessage("You don't have sufficient score to do an undo");
                alert.setPositiveButton("OK", null);
                alert.show();
            }
            int newScore = score - CONSTANTS.DEDUCTION_PER_UNDO;
            newState.updateScore(newScore);

            currentState = newState;
            updateView();
        }
    }
    void executeCommandAndUpdateView(Command cmd){
        State newState = intermediator.execute(cmd);
        if(newState == null)
            return;
        if(newState.hasLost()){
            Intent newIntent = new Intent(getApplicationContext(), LostActivity.class);
            startActivity(newIntent);
        }else if(newState.hasWon()){
            Intent newIntent = new Intent(getApplicationContext(), WonActivity.class);
            startActivity(newIntent);
        }
        currentState = newState;
        updateView();
    }
    public void onSwipeRight() {
        if(CONSTANTS.DEBUG) {
            Log.d(CONSTANTS.LOG_TAG, "swipe right");
            Toast.makeText(GameActivity.this, "right", Toast.LENGTH_SHORT).show();
        }
        SwipeRightCommand cmd = new SwipeRightCommand(currentState);
        executeCommandAndUpdateView(cmd);
    }
    public void onSwipeLeft() {
        if(CONSTANTS.DEBUG) {
            Log.d(CONSTANTS.LOG_TAG, "swipe left");
            Toast.makeText(GameActivity.this, "left", Toast.LENGTH_SHORT).show();
        }
        SwipeLeftCommand cmd = new SwipeLeftCommand(currentState);
        executeCommandAndUpdateView(cmd);
    }
    public void onSwipeUp() {
        if(CONSTANTS.DEBUG) {
            Log.d(CONSTANTS.LOG_TAG, "swipe top");
            Toast.makeText(GameActivity.this, "top", Toast.LENGTH_SHORT).show();
        }
        SwipeUpCommand cmd = new SwipeUpCommand(currentState);
        executeCommandAndUpdateView(cmd);
    }
    public void onSwipeDown() {
        if(CONSTANTS.DEBUG) {
            Log.d(CONSTANTS.LOG_TAG, "swipe bottom");
            Toast.makeText(GameActivity.this, "down", Toast.LENGTH_SHORT).show();
        }
        SwipeDownCommand cmd = new SwipeDownCommand(currentState);
        executeCommandAndUpdateView(cmd);
    }
    public void updateView(){
        String[][] grid = currentState.getGrid();
        for(int i = 0; i < CONSTANTS.GRID_SIZE; i++){
            for(int j = 0; j < CONSTANTS.GRID_SIZE; j++){
                tvGrid[i][j].setText(grid[i][j]);
                if(!grid[i][j].equals("")){
                    int val = Integer.parseInt(grid[i][j]);
                    int idx = (int)Math.floor(Math.log(val)/Math.log(2.0));
                    tvGrid[i][j].setBackgroundColor(Color.parseColor(CONSTANTS.COLORS[idx-1]));
                }else{
                    tvGrid[i][j].setBackgroundColor(Color.parseColor(CONSTANTS.DEFAULTCOLOR));
                }
            }
        }
        int score = currentState.getScore() - negativeScore;
        tvScore.setText(Integer.toString(score));
    }
    public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeDown();
                    } else {
                        onSwipeUp();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                result = false;
            }
            return result;
        }
    }


}
