package ch.kerbtier.struwwel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Observable {
  private final List<WeakReference<Runnable>> observers = new ArrayList<>();
  private final Set<Runnable> hard = new HashSet<>();

  public ObserverReference registerWeak(Runnable observer) {
    return register(observer, true);
  }
  
  public ObserverReference register(Runnable observer) {
    return register(observer, false);
  }
  
  public ObserverReference register(Runnable observer, boolean weak) {
    observers.add(new WeakReference<>(observer));
    if(!weak) {
      hard.add(observer);
    }
    return new ObserverReference(this, observer);
  }

  public void inform() {
    List<WeakReference<Runnable>> toRemove = new ArrayList<>();
    for (WeakReference<Runnable> observerReference : observers) {
      Runnable observer = observerReference.get();
      if (observer != null) {
        observer.run();
      } else {
        toRemove.add(observerReference);
      }
    }
    observers.removeAll(toRemove);
  }

  void makeWeak(Runnable observer) {
    hard.remove(observer);
  }
  
  /**
   * returns total number of listeners
   * for testing/debugging purposes
   * @return
   */
  public int count() {
    int count = 0;
    for (WeakReference<Runnable> observerReference : observers) {
      if (observerReference.get() != null) {
        count++;
      }
    }
    return count;
  }
}
