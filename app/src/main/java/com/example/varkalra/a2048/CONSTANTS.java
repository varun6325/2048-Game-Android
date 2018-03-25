package com.example.varkalra.a2048;

/**
 * Created by varkalra on 3/22/2018.
 */

public class CONSTANTS {
    public final static int DEDUCTION_PER_UNDO = 2;
    public static final int MAX_ALLOWED_COMMANDS = 10;
    public static final int GRID_SIZE = 4;
    public static final int[][] IDS = {{R.id.cell00, R.id.cell01, R.id.cell02, R.id.cell03}, {R.id.cell10, R.id.cell11, R.id.cell12, R.id.cell13} ,
        {R.id.cell20, R.id.cell21, R.id.cell22, R.id.cell23}, {R.id.cell30, R.id.cell31, R.id.cell32, R.id.cell33} };
    public static String LOG_TAG = "DEBUG_2048";
    public static String WINNING_SCORE = "2048";
    public static boolean DEBUG = false;
    public static String[] COLORS = {"#f4b841", "#f4d641", "#f4424b", "#f49a41", "#f45b41", "#f4cd41", "#f44141", "#414ff4", "#7c41f4", "#41a3f4",
    "#41f443", "#f49141", "#f46741", "#f4e541", "#6441f4", "#f44194", "#f48241", "#f45b41", "#4141f4", "#f4417f", "#f45541"};
    public static String DEFAULTCOLOR="#D3D3D3";
}
