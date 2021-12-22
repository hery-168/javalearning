package com.hery.juc.hbase.snapshot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName SnapshotManager
 * @Description
 * @Date 2021/12/15 14:51
 * @Author yongheng
 * @Version V1.0
 **/
public class SnapshotManager {
    private ExecutorService executorService;

    public SnapshotManager() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public static void main(String[] args) {
        SnapshotManager manager = new SnapshotManager();
        manager.snapshot();
    }

    public boolean snapshot() {
        for (int i = 0; i < 5; i++) {
            SnapshotHandler handler = new SnapshotHandler("table" + i);
            executorService.submit(handler);
        }
        return true;
    }
}
