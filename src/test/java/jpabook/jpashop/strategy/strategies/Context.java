package jpabook.jpashop.strategy.strategies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class Context<T> {
    private final Strategy<T> strategy;

    public Context(Strategy<T> strategy){
        this.strategy = strategy;
    }

    public T execute(){
        long startTime = System.currentTimeMillis();
        T result = strategy.call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("resultTime={}", resultTime);

        return result;
    }
}
