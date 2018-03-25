package com.example.varkalra.a2048;

/**
 * Created by varkalra on 3/19/2018.
 */

public class SwipeUpCommand implements Command {
    private State mState;
    SwipeUpCommand(State state){
        mState = state;
    }
    @Override
    public State execute() {
        return mState.swipeUp();
    }

    @Override
    public State undo() {
        return mState;
    }
}
