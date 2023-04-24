package jpabook.jpashop.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldTracer implements Tracer{
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";
    private TraceId traceId;

    @Override
    public TraceStatus begin(String message){
        TraceStatus status = (this.traceId==null ? TraceStatus.createTraceStatus(message, System.currentTimeMillis())
                : TraceStatus.nextStatus(traceId, message, System.currentTimeMillis()));
        this.traceId = status.getTraceId();
        log.info("[{}] {}{}",status.getTraceId().getTransactionId(),getPrefix(START_PREFIX, traceId.getLevel()),message);
        return status;
    }
    @Override
    public TraceStatus complete(TraceStatus status, Exception ex){
        if(ex==null){
            return end(status);
        }else{
            return exceptionEnd(status, ex);
        }
    }
    private TraceStatus end(TraceStatus status){
        log.info("[{}] {}{} ({}ms)",traceId.getTransactionId(),
                getPrefix(COMPLETE_PREFIX, traceId.getLevel()),status.getMessage()
                ,System.currentTimeMillis()-status.getStartTimeMs());
        return status;
    }
    private TraceStatus exceptionEnd(TraceStatus status, Exception ex){
        log.info("[{}] {}{}(Ex:{}) ({}ms)",traceId.getTransactionId(),
                getPrefix(EX_PREFIX, traceId.getLevel()),status.getMessage(),
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
