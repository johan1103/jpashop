package jpabook.jpashop.template;

import jpabook.jpashop.template.templates.AbstractTemplate;
import jpabook.jpashop.template.templates.TemplateImpl1;
import jpabook.jpashop.template.templates.TemplateImpl2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0(){
        logic1();
        logic2();
    }

    private void logic1(){
        long startTime = System.currentTimeMillis();

        log.info("비즈니스 로직 1 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("resultTime={}", resultTime);
    }

    private void logic2(){
        long startTime = System.currentTimeMillis();

        log.info("비즈니스 로직 2 실행");

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("resultTime={}", resultTime);
    }

    @Test
    void templateMethodV1(){
        AbstractTemplate template = null;

        template = new TemplateImpl1();
        template.execute();

        template = new TemplateImpl2();
        template.execute();

    }

    @Test
    void templateMethodV2(){
        AbstractTemplate template = null;

        template = new AbstractTemplate<String>() {
            @Override
            protected String call() {
                log.info("비즈니스 로직 1 실행");
                return "ok";
            }
        };
        log.info("result : {}",template.execute());

        template = new AbstractTemplate<String>() {
            @Override
            protected String call() {
                log.info("비즈니스 로직 2 실행");
                return "ok";
            }
        };
        log.info("result : {}",template.execute());
    }

}
