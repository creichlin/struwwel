package ch.kerbtier.struwwel;

import java.util.HashMap;
import java.util.Map;

public class MappedObservable<T> {
  private final Map<T, Observable> observables = new HashMap<>();

  public ObserverReference register(T name, Runnable observer) {
    if (!observables.containsKey(name)) {
      observables.put(name, new Observable());
    }
    observables.get(name).register(observer);
    return new ObserverReference(observables.get(name), observer);
  }

  public void inform(T name) {
    if (observables.containsKey(name)) {
      observables.get(name).inform();
    }
  }
}
