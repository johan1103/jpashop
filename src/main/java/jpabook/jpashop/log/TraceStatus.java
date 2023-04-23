package jpabook.jpashop.log;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceStatus {
    private String transactionId;
    private Integer level;
    private String message;
    private Long startTimeMs;

    private TraceStatus(String transactionId, Integer level, String message, Long startTimeMs){
        this.transactionId=transactionId;
        this.level=level;
        this.message=message;
        this.startTimeMs=startTimeMs;
    }
    public static TraceStatus createTraceStatus(Integer level, String message, Long startTimeMs){
        String transactionId = UUID.randomUUID().toString().substring(0,8);
        TraceStatus status = new TraceStatus(transactionId,level,message,startTimeMs);
        return status;
    }
    public static TraceStatus createNextStatus(TraceStatus traceStatus, String message, Long startTimeMs){
        TraceStatus status = new TraceStatus(traceStatus.getTransactionId(), traceStatus.getLevel()+1,
                message, startTimeMs);
        return status;
    }
}
