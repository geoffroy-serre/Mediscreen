package com.mediscreen.diabeteEstimation.model;

public class Counter {

  private int counter;

  public Counter(int counter) {
    this.counter = counter;
  }

  public int getCounter() {
    return counter;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }

  public void increment(){
    this.counter++;
  }
}
