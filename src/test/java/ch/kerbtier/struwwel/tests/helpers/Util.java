package ch.kerbtier.struwwel.tests.helpers;

import java.util.ArrayList;
import java.util.List;

import ch.kerbtier.struwwel.ObserverReference;

public class Util {

  /**
   * this method should garbage collect all unreferenced objects. its actually
   * not possible as far as i know in java because it depends on the jvm.
   */
  public static void gc() {

    gcByOutOfMemory();

    ObserverReference.clean();
    
    gcByOutOfMemory();
  }
  
  public static void gcByOutOfMemory() {
    try {
      List<byte[]> data = new ArrayList<>();
      while (true) {
        data.add(new byte[1024]);
      }
    } catch (OutOfMemoryError e) {
      // totally expected
    }
  }
}
