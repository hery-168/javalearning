package com.hery.spi;

/**
 * @ClassName K8sExecutor
 * @Description TODO
 * @Date 2021/4/21 17:28
 * @Author yongheng
 * @Version V1.0
 **/
public class K8sExecutor implements PipelineExecutor{
    @Override
    public void executor() {
        System.out.println("K8sExecutor  executor");
    }
}
