package com.example.varkalra.a2048;

import java.util.LinkedList;

/**
 * Created by varkalra on 3/19/2018.
 */

public class Intermediator {
    private LinkedList<Command> CommandList;
    Intermediator(){
        CommandList = new LinkedList<Command>();
    }
    private void CheckSizeAndRemove(){
        if (CommandList.size() > CONSTANTS.MAX_ALLOWED_COMMANDS) {
            int num = CommandList.size() - CONSTANTS.MAX_ALLOWED_COMMANDS;
            for (int i = 0; i < num && CommandList.size() > 1; i++)
                CommandList.removeFirst();
        }
    }
    State execute(Command cmd){
        State newState = cmd.execute();
        if(newState.hasLost())
            return newState;
        if(newState.isupdatedFromPreviousState()) {
            CommandList.add(cmd);
            CheckSizeAndRemove();
            return newState;
        }
        return null;
    }
    State undo(){
        if(CommandList.size() > 0) {
            Command cmd = CommandList.removeLast();
            return cmd.undo();
        }
        else
            return null;

    }
}
