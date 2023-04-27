package jpabook.jpashop.log;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceStatus {
    private TraceId traceId;
    private String message;
    private Long startTimeMs;

    private TraceStatus(TraceId traceId, String message, Long startTimeMs){
        this.traceId=traceId;
        this.message=message;
        this.startTimeMs=startTimeMs;
    }
    public static TraceStatus createTraceStatus(TraceId traceId, String message, Long startTimeMs){
        return new TraceStatus(traceId,message,startTimeMs);
    }
    public static TraceStatus nextStatus(TraceId traceId, String message, Long startTimeMs){
        traceId.nextLevel();
        return new TraceStatus(traceId, message, startTimeMs);
    }
}
