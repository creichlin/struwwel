package ch.kerbtier.struwwel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ObserverReference {
  private static Map<Object, List<Runnable>> keptObservers = new WeakHashMap<>();

  private Runnable observer;
  private Observable observable;

  public ObserverReference(Observable observable, Runnable observer) {
    this.observer = observer;
    this.observable = observable;
  }

  public ObserverReference weak() {
    observable.makeWeak(observer);
    return this;
  }

  public ObserverReference keepFor(Object instance) {

    if (!keptObservers.containsKey(instance)) {
      synchronized (keptObservers) {
        if (!keptObservers.containsKey(instance)) {
          keptObservers.put(instance, new ArrayList<Runnable>());
        }
      }
    }
    keptObservers.get(instance).add(observer);

    observable.makeWeak(observer);
    return this;
  }

  /**
   * WeakHashMap looses references to values only when it is mutated, not when
   * the key is garbage collected. the following mutation triggers that removal of references.
   * 
   * There should be no need to use this method in real code. It's for testing.
   */
  public static void clean() {
    keptObservers.put(new Object(), new ArrayList<Runnable>());
  }
}
