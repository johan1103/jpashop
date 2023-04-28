package jpabook.jpashop.strategy;

import jpabook.jpashop.strategy.strategies.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StrategyTest {

    @Test
    public void strategyMethodV0(){
        Strategy<String> strategy = new StrategyImpl1();
        Context<String> context = new Context<>(strategy);
        context.execute();

        Strategy<String> strategy2 = new StrategyImpl2();
        Context<String> context2 = new Context<>(strategy2);
        context2.execute();
    }

    @Test
    public void strategyMethodV1(){
        Context<String> context = new Context<>(()->{
            log.info("비즈니스 로직 1 실행");
            return "ok";
        });
        context.execute();

        Context<String> context2 = new Context<>(()->{
            log.info("비즈니스 로직 2 실행");
            return "ok";
        });
        context2.execute();
    }

    @Test
    public void strategyMethodV2(){
        ContextV2<String> context = new ContextV2<>();

        context.execute(()->{log.info("비즈니스 로직 1 실행"); return "ok";});
        context.execute(()->{log.info("비즈니스 로직 2 실행"); return "ok";});
    }
}
