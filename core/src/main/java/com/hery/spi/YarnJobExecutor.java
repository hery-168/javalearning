package com.hery.spi;

/**
 * @ClassName YarnJobExecutor
 * @Description yarn 执行器
 * @Date 2021/4/21 17:27
 * @Author yongheng
 * @Version V1.0
 **/
public class YarnJobExecutor implements PipelineExecutor{
    @Override
    public void executor() {
        System.out.println("YarnJobExecutor  executor....");
    }
}
