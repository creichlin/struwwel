package ch.kerbtier.struwwel;

import java.util.HashMap;
import java.util.Map;

public class Map2Listeners<T, U> {
  private final Map<T, Map<U, Listeners>> listeners = new HashMap<>();

  public ListenerReference on(T name1, U name2, Runnable run) {
    if (!listeners.containsKey(name1)) {
      listeners.put(name1, new HashMap<U, Listeners>());
    }

    Map<U, Listeners> listeners2 = listeners.get(name1);
    if (!listeners2.containsKey(name2)) {
      listeners2.put(name2, new Listeners());
    }

    listeners2.get(name2).onEvent(run);
    return new ListenerReference(listeners2.get(name2), run);
  }

  public void trigger(T name1, U name2) {
    if (listeners.containsKey(name1)) {
      Map<U, Listeners> listeners2 = listeners.get(name1);
      if (listeners2.containsKey(name2)) {
        listeners2.get(name2).trigger();
      }
    }
  }
}
