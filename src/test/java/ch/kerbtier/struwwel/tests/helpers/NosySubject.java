package ch.kerbtier.struwwel.tests.helpers;

public class NosySubject implements Runnable {

  private NosyTarget target = new NosyTarget();

  @Override
  public void run() {
    target.inc();
  }

  public void assertRuns(int runs) {
    target.assertRuns(runs);
  }

  public NosyTarget getTarget() {
    return target;
  }
}