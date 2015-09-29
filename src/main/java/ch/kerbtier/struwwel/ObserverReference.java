package ch.kerbtier.struwwel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ObserverReference {
  private Runnable runnable;
  private Observable observable;

  private static Map<Object, List<Runnable>> forInstance = new WeakHashMap<>();

  public ObserverReference(Observable listeners, Runnable runnable) {
    this.runnable = runnable;
    this.observable = listeners;
  }

  public ObserverReference weak() {
    observable.makeWeak(runnable);
    return this;
  }

  public ObserverReference keepFor(Object instance) {

    if (!forInstance.containsKey(instance)) {
      synchronized (forInstance) {
        if (!forInstance.containsKey(instance)) {
          forInstance.put(instance, new ArrayList<Runnable>());
        }
      }
    }
    forInstance.get(instance).add(runnable);

    observable.makeWeak(runnable);
    return this;
  }

  /**
   * weakHashMap looses references to values only when it is mutated, not when
   * actually key is garbage collected. the following mutation triggers that and
   * can be used for tests. System.gc(); clean(); System.gc(); will release
   * those values probably... depending on vm.
   */
  public static void clean() {
    forInstance.put(new Object(), new ArrayList<Runnable>());
  }
}
