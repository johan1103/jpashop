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
    public static TraceStatus createTraceStatus(String message, Long startTimeMs){
        return new TraceStatus(TraceId.create(0),message,startTimeMs);
    }
    public static TraceStatus nextStatus(TraceId traceId, String message, Long startTimeMs){
        return new TraceStatus(TraceId.nextLevel(traceId), message, startTimeMs);
    }
}
