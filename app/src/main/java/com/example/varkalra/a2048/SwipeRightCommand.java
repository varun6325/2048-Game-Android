package com.example.varkalra.a2048;

/**
 * Created by varkalra on 3/19/2018.
 */

public class SwipeRightCommand implements Command {
    private State mState;
    SwipeRightCommand(State state){
        mState = state;
    }
    @Override
    public State execute() {
        return mState.swipeRight();
    }

    @Override
    public State undo() {
        return mState;
    }
}
