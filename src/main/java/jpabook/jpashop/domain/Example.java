package jpabook.jpashop.domain;

public class Example {
  int number;
  boolean isTrue;

  protected Example(int number,boolean isTrue){
    this.number=number;
    this.isTrue=isTrue;
  }

  public static Example ofInt(int number){
    return new Example(number,true);
  }
}
