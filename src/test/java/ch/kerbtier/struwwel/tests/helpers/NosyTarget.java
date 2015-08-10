package ch.kerbtier.struwwel.tests.helpers;

public class NosyTarget {
  private int runs = 0;
  
  void inc() {
    runs++;
  }
  
  public void assertRuns(int runs_) {
    if (runs_ != this.runs) {
      throw new RuntimeException("expected to be notifyied " + runs_ + " times but was notifyied " + this.runs
          + " times.");
    }
  }
}
