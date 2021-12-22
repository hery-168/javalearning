package com.hery.juc.hbase.snapshot;

/**
 * @ClassName SnapshotHandler
 * @Description
 * @Date 2021/12/15 14:49
 * @Author yongheng
 * @Version V1.0
 **/
public class SnapshotHandler extends EventHandler {
    private String tableName;

    public SnapshotHandler(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void prepare() {
        System.out.println(tableName + "prepare快照预处理 ");
    }

    @Override
    public void process() {
        System.out.println("核心方法，我在处理" + tableName + "snapshot 的逻辑");
    }
}
