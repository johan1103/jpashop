package jpabook.jpashop.log.template;

import jpabook.jpashop.log.TraceId;
import jpabook.jpashop.log.TraceStatus;
import jpabook.jpashop.log.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
public abstract class AbstractTemplate<T> {
    private final Tracer tracer;

    public T execute(String message){
        TraceStatus status = tracer.begin(message);
        T result = null;
        try{
            result = call();
            tracer.complete(status,null);
        }catch (Exception e){
            tracer.complete(status, e);
            throw e;
        }
        return result;
    }

    protected abstract T call();
}
