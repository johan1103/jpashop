package jpabook.jpashop.domain;

public class ExtendExample extends Example{
  int extendNumber;

  protected ExtendExample(int number,int extendNumber){
    super(number,true);
    this.number=number;
  }

  public static ExtendExample ofNumber(int number){
    return new ExtendExample(number,number);
  }

}
