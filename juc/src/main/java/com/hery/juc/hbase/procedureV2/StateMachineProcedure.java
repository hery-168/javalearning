package com.hery.juc.hbase.procedureV2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName StateMachineProcedure
 * @Description 状态机
 * @Date 2021/12/20 15:52
 * @Author yongheng
 * @Version V1.0
 **/
public abstract class StateMachineProcedure<TEnvironment, TState> extends Procedure<TEnvironment> {
    private static final Logger logger = LoggerFactory.getLogger(StateMachineProcedure.class);

    private static final int EOF_STATE = Integer.MIN_VALUE;
    protected int stateCount = 0;
    private int[] states = null;
    private List<Procedure<TEnvironment>> subProcList = null;
    private int cycles = 0;
    private int previousState;

    private Flow stateFlow = Flow.HAS_MORE_STATE;

    public enum Flow {
        HAS_MORE_STATE, NO_MORE_STATE,
    }

    protected abstract Flow executeFromState(TEnvironment env, TState state);

    @Override
    protected Procedure<TEnvironment>[] execute(TEnvironment env) {
        // TODO_YH : 获取当前状态
        TState state = getCurrentState();
        if (stateCount == 0) {
            setNextState(getStateId(state));
        }
        // Keep running count of cycles
        if (getStateId(state) != this.previousState) {
            this.previousState = getStateId(state);
            this.cycles = 0;
        } else {
            this.cycles++;
        }

        // TODO_YH :执行
        stateFlow = executeFromState(env, state);

        if (!hasMoreState()) {
            setNextState(EOF_STATE);
        }

        if(subProcList != null && !subProcList.isEmpty()) {
            Procedure[] subProcedures = subProcList.toArray(new Procedure[subProcList.size()]);
            subProcList = null;
            return subProcedures;
        }
        return (!hasMoreState()) ? null : new Procedure[]{this};
    }

    private boolean hasMoreState() {
        return stateFlow != Flow.NO_MORE_STATE;
    }

    protected TState getCurrentState() {
        return stateCount > 0 ? getState(states[stateCount - 1]) : getInitialState();
    }

    protected void setNextState(final TState state) {
        setNextState(getStateId(state));
    }
    private void setNextState(final int stateId) {
        if (states == null || states.length == stateCount) {
            int newCapacity = stateCount + 8;
            if (states != null) {
                states = Arrays.copyOf(states, newCapacity);
            } else {
                states = new int[newCapacity];
            }
        }
        states[stateCount++] = stateId;
    }

    protected abstract TState getState(int stateId);

    protected abstract int getStateId(TState state);

    protected abstract TState getInitialState();
}
