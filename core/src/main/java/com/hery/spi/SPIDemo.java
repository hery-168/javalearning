package com.hery.spi;

import java.util.ServiceLoader;

/**
 * @ClassName SPIDemo
 * @Description spi 实例入口
 * @Date 2021/4/21 17:30
 * @Author yongheng
 * @Version V1.0
 **/
public class SPIDemo {
    public static void main(String[] args) {
        //  SPI
        ServiceLoader<PipelineExecutor> executors = ServiceLoader.load(PipelineExecutor.class);
        for (PipelineExecutor executor : executors) {
            executor.executor();
        }

    }
}
