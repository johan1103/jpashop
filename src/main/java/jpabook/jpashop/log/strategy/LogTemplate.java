package jpabook.jpashop.log.strategy;

import jpabook.jpashop.log.TraceStatus;
import jpabook.jpashop.log.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogTemplate<T> {
    private final Tracer tracer;
    public T execute(String message,CallBack<T> callBack){
        TraceStatus status = tracer.begin(message);
        T result = null;
        try{
            result = callBack.call();
            tracer.complete(status,null);
        }catch (Exception e){
            tracer.complete(status,e);
            throw e;
        }
        return result;
    }
}
