package com.example.varkalra.a2048;

/**
 * Created by varkalra on 3/19/2018.
 */

public class SwipeLeftCommand implements Command {
    private State mState;
    SwipeLeftCommand(State state){
        mState = state;
    }
    @Override
    public State execute() {
        return mState.swipeLeft();
    }

    @Override
    public State undo() {
        return mState;
    }
}
