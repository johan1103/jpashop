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
    public static TraceId create(){
        String transactionId = UUID.randomUUID().toString().substring(0,8);
        return new TraceId(transactionId, 0);
    }
    public void nextLevel(){
        this.level+=1;
    }
    public void previousLevel(){
        if(this.level!=0)this.level-=1;
    }
}
