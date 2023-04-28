package jpabook.jpashop.strategy.strategies;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextV2<T> {
    public T execute(Strategy<T> strategy){
        long startTime = System.currentTimeMillis();
        T result = strategy.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("resultTime={}", resultTime);
        return result;
    }
}
