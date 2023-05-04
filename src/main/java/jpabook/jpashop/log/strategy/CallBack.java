package jpabook.jpashop.log.strategy;

public interface CallBack<T> {
    public T call();
}
