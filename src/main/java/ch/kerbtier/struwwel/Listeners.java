package ch.kerbtier.struwwel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Listeners {
  private final List<WeakReference<Runnable>> listeners = new ArrayList<>();
  private final Set<Runnable> hard = new HashSet<>();

  public ListenerReference onEvent(Runnable run) {
    listeners.add(new WeakReference<>(run));
    hard.add(run);
    return new ListenerReference(this, run);
  }

  public void trigger() {
    List<WeakReference<Runnable>> tor = new ArrayList<>();
    for (WeakReference<Runnable> wr : listeners) {
      Runnable r = wr.get();
      if (r != null) {
        r.run();
      } else {
        tor.add(wr);
      }
    }
    tor.removeAll(tor);
  }

  public void makeWeak(Runnable runnable) {
    hard.remove(runnable);
  }
}
