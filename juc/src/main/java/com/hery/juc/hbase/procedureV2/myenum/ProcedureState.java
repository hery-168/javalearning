package com.hery.juc.hbase.procedureV2.myenum;

/**
 * @ClassName ProcedureState
 * @Description ProcedureState
 * @Date 2021/12/20 18:27
 * @Author yongheng
 * @Version V1.0
 **/
public enum ProcedureState {
    INITIALIZING(1),
    RUNNABLE(2),
    WAITING(3),
    WAITING_TIMEOUT(4),
    ROLLEDBACK(5),
    SUCCESS(6),
    FAILED(7),
    ;
    private int value;

    ProcedureState(int value) {
        this.value = value;
    }

    public static final int INITIALIZING_VALUE = 1;

    public static final int RUNNABLE_VALUE = 2;

    public static final int WAITING_VALUE = 3;

    public static final int WAITING_TIMEOUT_VALUE = 4;

    public static final int ROLLEDBACK_VALUE = 5;

    public static final int SUCCESS_VALUE = 6;
    public static final int FAILED_VALUE = 7;

    public final int getNumber() {
        return value;
    }

    public static ProcedureState valueOf(int value) {
        return forNumber(value);
    }

    public static ProcedureState forNumber(int value) {
        switch (value) {
            case 1:
                return INITIALIZING;
            case 2:
                return RUNNABLE;
            case 3:
                return WAITING;
            case 4:
                return WAITING_TIMEOUT;
            case 5:
                return ROLLEDBACK;
            case 6:
                return SUCCESS;
            case 7:
                return FAILED;
            default:
                return null;
        }
    }
}