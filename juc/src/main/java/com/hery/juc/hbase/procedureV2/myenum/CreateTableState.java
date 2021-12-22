package com.hery.juc.hbase.procedureV2.myenum;

/**
 * @ClassName CreateTableState
 * @Description TODO
 * @Date 2021/12/17 17:47
 * @Author yongheng
 * @Version V1.0
 **/
public enum CreateTableState {

    CREATE_TABLE_PRE_OPERATION(1),
    CREATE_TABLE_WRITE_FS_LAYOUT(2),
    CREATE_TABLE_ADD_TO_META(3),
    CREATE_TABLE_ASSIGN_REGIONS(4),
    CREATE_TABLE_UPDATE_DESC_CACHE(5),
    CREATE_TABLE_POST_OPERATION(6),
    ;
    private int value;
    CreateTableState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public final int getNumber() {
        return value;
    }
    public static CreateTableState forNumber(int value) {
        switch (value) {
            case 1: return CREATE_TABLE_PRE_OPERATION;
            case 2: return CREATE_TABLE_WRITE_FS_LAYOUT;
            case 3: return CREATE_TABLE_ADD_TO_META;
            case 4: return CREATE_TABLE_ASSIGN_REGIONS;
            case 5: return CREATE_TABLE_UPDATE_DESC_CACHE;
            case 6: return CREATE_TABLE_POST_OPERATION;
            default: return null;
        }
    }
}
