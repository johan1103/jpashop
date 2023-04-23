package jpabook.jpashop.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Time;

@Slf4j
@Component
public class CustomTracer {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    public TraceStatus begin(Integer level, String message){
        TraceStatus status = TraceStatus.createTraceStatus(level,message, System.currentTimeMillis());
        log.info("[{}] {}{}",status.getTransactionId(),getPrefix(START_PREFIX, level),message);
        return status;
    }
    public TraceStatus beginSync(TraceStatus status, String message){
        TraceStatus syncStatus = TraceStatus.createNextStatus(status, message, System.currentTimeMillis());
        log.info("[{}] {}{}",syncStatus.getTransactionId(),getPrefix(START_PREFIX, syncStatus.getLevel()),message);
        return syncStatus;
    }
    public TraceStatus complete(TraceStatus status, String message, Exception ex){
        if(ex==null){
            return end(status,message);
        }else{
            return exceptionEnd(status,message,ex);
        }
    }
    private TraceStatus end(TraceStatus status, String message){
        log.info("[{}] {}{} ({}ms)",status.getTransactionId(),getPrefix(COMPLETE_PREFIX, status.getLevel()),message
        ,System.currentTimeMillis()-status.getStartTimeMs());
        return status;
    }
    private TraceStatus exceptionEnd(TraceStatus status, String message, Exception ex){
        log.info("[{}] {}{}(Ex:{}) ({}ms)",status.getTransactionId(),getPrefix(EX_PREFIX, status.getLevel()),message,
                ex.getMessage(),System.currentTimeMillis()-status.getStartTimeMs());
        return status;
    }
    private String getPrefix(String prefix, Integer level){
        StringBuilder result= new StringBuilder();
        for(int i=0;i<level;i++){
            result.append(i==level-1 ? prefix : "  |");
        }
        return result.toString();
    }
}
