package jpabook.jpashop.strategy.strategies;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrategyImpl1 implements Strategy<String>{
    public String call(){
        log.info("비즈니스 로직 1 실행");
        return "ok";
    }
}
