package ch.kerbtier.struwwel;

import java.util.HashMap;
import java.util.Map;

public class DoubleMappedObservable<T, U> {
  private final Map<T, Map<U, Observable>> listeners = new HashMap<>();

  public ObserverReference register(T name1, U name2, Runnable observer) {
    if (!listeners.containsKey(name1)) {
      listeners.put(name1, new HashMap<U, Observable>());
    }

    Map<U, Observable> listeners2 = listeners.get(name1);
    if (!listeners2.containsKey(name2)) {
      listeners2.put(name2, new Observable());
    }

    listeners2.get(name2).register(observer);
    return new ObserverReference(listeners2.get(name2), observer);
  }

  public void inform(T name1, U name2) {
    if (listeners.containsKey(name1)) {
      Map<U, Observable> listeners2 = listeners.get(name1);
      if (listeners2.containsKey(name2)) {
        listeners2.get(name2).inform();
      }
    }
  }
}
