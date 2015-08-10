package ch.kerbtier.struwwel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ListenerReference {
  private Runnable runnable;
  private Listeners listeners;
  
  private static Map<Object, List<Runnable>> forInstance = new WeakHashMap<>();
  
  public ListenerReference(Listeners listeners, Runnable runnable) {
    this.runnable = runnable;
    this.listeners = listeners;
  }

  public ListenerReference keepFor(Object instance) {
    if(!forInstance.containsKey(instance)) {
      synchronized(forInstance) {
        if(!forInstance.containsKey(instance)) {
          forInstance.put(instance, new ArrayList<Runnable>());
        }
      }
    }
    forInstance.get(instance).add(runnable);
    listeners.makeWeak(runnable);
    return this;
  }
}
