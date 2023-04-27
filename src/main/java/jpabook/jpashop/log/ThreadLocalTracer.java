package jpabook.jpashop.log;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalTracer implements Tracer{
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";
    private ThreadLocal<TraceId> traceId = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message){
        if(traceId.get()==null) traceId.set(TraceId.create());
        else traceId.get().nextLevel();
        TraceStatus status = TraceStatus.createTraceStatus(traceId.get(), message, System.currentTimeMillis());
        log.info("[{}] {}{}",status.getTraceId().getTransactionId(),getPrefix(START_PREFIX, traceId.get().getLevel()),message);
        return status;
    }
    @Override
    public void complete(TraceStatus status, Exception ex){
        if(ex==null){
            end(status);
        }else{
            exceptionEnd(status, ex);
        }
        if(this.traceId.get().getLevel()==0)
            traceId.remove();
        else
            traceId.get().previousLevel();
    }
    private void end(TraceStatus status){
        log.info("[{}] {}{} ({}ms)",traceId.get().getTransactionId(),
                getPrefix(COMPLETE_PREFIX, traceId.get().getLevel()),status.getMessage()
                ,System.currentTimeMillis()-status.getStartTimeMs());
    }
    private void exceptionEnd(TraceStatus status, Exception ex){
        log.info("[{}] {}{}(Ex:{}) ({}ms)",traceId.get().getTransactionId(),
                getPrefix(EX_PREFIX, traceId.get().getLevel()),status.getMessage(),
                ex.getMessage(),System.currentTimeMillis()-status.getStartTimeMs());
    }
    private String getPrefix(String prefix, Integer level){
        StringBuilder result= new StringBuilder();
        for(int i=0;i<level;i++){
            result.append(i==level-1 ? prefix : "  |");
        }
        return result.toString();
    }
}
