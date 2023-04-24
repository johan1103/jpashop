package jpabook.jpashop.log;

public interface Tracer{
    public TraceStatus begin(String message);
    public TraceStatus complete(TraceStatus status, Exception ex);
}
