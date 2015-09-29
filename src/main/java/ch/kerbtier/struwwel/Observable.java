package ch.kerbtier.struwwel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Observable {
  private final List<WeakReference<Runnable>> observers = new ArrayList<>();
  private final Set<Runnable> hard = new HashSet<>();

  public ObserverReference registerWeak(Runnable run) {
    return register(run, true);
  }
  
  public ObserverReference register(Runnable run) {
    return register(run, false);
  }
  
  public ObserverReference register(Runnable run, boolean weak) {
    observers.add(new WeakReference<>(run));
    if(!weak) {
      hard.add(run);
    }
    return new ObserverReference(this, run);
  }

  public void inform() {
    List<WeakReference<Runnable>> toRemove = new ArrayList<>();
    for (WeakReference<Runnable> wr : observers) {
      Runnable r = wr.get();
      if (r != null) {
        r.run();
      } else {
        toRemove.add(wr);
      }
    }
    observers.removeAll(toRemove);
  }

  void makeWeak(Runnable runnable) {
    hard.remove(runnable);
  }
  
  /**
   * returns total number of listeners
   * for testing/debugging purposes
   * @return
   */
  public int count() {
    int count = 0;
    for (WeakReference<Runnable> wr : observers) {
      if (wr.get() != null) {
        count++;
      }
    }
    return count;
  }
}
