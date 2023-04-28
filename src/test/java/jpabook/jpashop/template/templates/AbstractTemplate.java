package jpabook.jpashop.template.templates;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate<T> {

    public T execute(){
        long startTime = System.currentTimeMillis();

        T result = call();

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("resultTime={}", resultTime);
        return result;
    }

    protected abstract T call();
}
