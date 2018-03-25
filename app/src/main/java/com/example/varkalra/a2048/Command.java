package com.example.varkalra.a2048;

/**
 * Created by varkalra on 3/19/2018.
 */

public interface Command {
    State execute();
    State undo();
}
