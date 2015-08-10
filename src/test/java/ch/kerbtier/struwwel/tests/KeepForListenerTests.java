package ch.kerbtier.struwwel.tests;

import org.testng.annotations.Test;

import ch.kerbtier.struwwel.Observable;
import ch.kerbtier.struwwel.tests.helpers.NosySubject;
import ch.kerbtier.struwwel.tests.helpers.NosyTarget;
import ch.kerbtier.struwwel.tests.helpers.Util;

public class KeepForListenerTests {
  @Test
  public void testSimpleListener() {
    Observable l = new Observable();
    
    Object keeperSubject = new Object();
    
    NosySubject r = new NosySubject();
    NosyTarget t = r.getTarget();
    
    l.register(r).keepFor(keeperSubject);
    r = null;

    Util.gc();
    l.inform();
    
    t.assertRuns(1);
  }
  
  @Test
  public void testKeepListener() {
    Observable l = new Observable();

    Object keeperSubject = new Object();
    
    NosySubject r = new NosySubject();
    NosyTarget t = r.getTarget();
    
    l.register(r).keepFor(keeperSubject);

    r = null;
    keeperSubject = null;
    Util.gc();
    
    
    l.inform();
    
    t.assertRuns(0);
  }
}
