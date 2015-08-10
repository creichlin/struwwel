package ch.kerbtier.struwwel;

import java.util.HashMap;
import java.util.Map;

public class MappedListeners<T> {
  private final Map<T, Listeners> listeners = new HashMap<>();

  public ListenerReference on(T name, Runnable run) {
    if (!listeners.containsKey(name)) {
      listeners.put(name, new Listeners());
    }
    listeners.get(name).onEvent(run);
    return new ListenerReference(listeners.get(name), run);
  }

  public void trigger(T name) {
    if (listeners.containsKey(name)) {
      listeners.get(name).trigger();
    }
  }
}
