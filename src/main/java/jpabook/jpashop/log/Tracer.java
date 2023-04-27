package jpabook.jpashop.log;

public interface Tracer{
    public TraceStatus begin(String message);
    public void complete(TraceStatus status, Exception ex);
}
