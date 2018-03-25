package com.example.varkalra.a2048;

/**
 * Created by varkalra on 3/19/2018.
 */

public class SwipeDownCommand implements Command {
    private State mState;
    SwipeDownCommand(State state){
        mState = state;
    }
    @Override
    public State execute() {
        return mState.swipeDown();
    }

    @Override
    public State undo() {
        return mState;
    }
}
