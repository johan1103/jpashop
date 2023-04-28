package jpabook.jpashop.template.templates;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateImpl1 extends AbstractTemplate{
    @Override
    protected String call() {
        log.info("비즈니스 로직 1 실행");
        return "ok";
    }
}
