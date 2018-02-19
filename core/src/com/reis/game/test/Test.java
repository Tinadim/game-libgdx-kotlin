package com.reis.game.test;

import com.reis.game.entity.ai.actions.EntityAction;
import com.reis.game.entity.ai.actions.ActionQueue;
import com.reis.game.entity.ai.actions.Idle;

import java.util.Random;

/**
 * Created by bernardoreis on 1/30/18.
 */

public class Test {

    public static void main(String[] args) {
        Random random = new Random();
        ActionQueue queue = new ActionQueue();
        for (int i = 0; i < 3; i++) {
            int priority = random.nextInt(20);
            System.out.println("Adding action with priority " + priority);
            EntityAction action = new EntityAction(priority);
            queue.addAction(action);
        }
        EntityAction action = null;
        System.out.println("Final order:");
        while (!(action instanceof Idle)) {
            action = queue.getNextAction();
            System.out.println("EntityAction priority " + action.getPriority());
        }
    }
}
