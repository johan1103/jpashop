package jpabook.jpashop.log;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceId {
    private String transactionId;
    private Integer level;

    private TraceId(String transactionId, Integer level){
        this.transactionId=transactionId;
        this.level=level;
    }
    public static TraceId create(Integer level){
        String transactionId = UUID.randomUUID().toString().substring(0,8);
        return new TraceId(transactionId, level);
    }
    public static TraceId nextLevel(TraceId traceId){
        return new TraceId(traceId.getTransactionId(), traceId.getLevel()+1);
    }
    public static TraceId previousLevel(TraceId traceId) throws LowestLevelException{
        if(traceId.getLevel()==0) throw new LowestLevelException();
        return new TraceId(traceId.getTransactionId(), traceId.getLevel()-1);
    }
}
