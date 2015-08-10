Struwwel
========

An implementation of the Observer pattern for different requirements.

Why?
----

Observer pattern can lead to memory leaks.

As long as observers and observables get garbage collected from time to time it's not necessary to unregister the observers.
This is the case in most GUI toolkits for example. But there are often situations where this holds not true. Many programmers
are even unaware that this can cause problems.

The common aproach to prevent this is to unregister the observer from the observable. This is often done at a different
location in the code and often in a place where it's difficult to find where the related piece of code is which added it.

This should help improve that problem by allowing to define when the observer should be unregistered while registering it.

Warning
-------

There is no guarantee though that this is helpful.
So far i have only used it in some very limited cases without proper experience.
It might also produce some other problems in the long term.


Also it's still kind of limited.

Usage patterns
--------------

Observables are instances which implement the Runnable interface.

### Release strategies

#### Observe forever

If you have an observer that should always be notified

    Observable observable = new Observable();
    observable.register(new Observer());
    
    observable.inform(); // will notify observer
     
#### Observe till observer is garbage

If you have an observer that should be notified as long as it's not garbage collected

    Observable observable = new Observable();
    Observer observer = new Observer();
    observable.register(observer).weak();
    
    observable.inform(); // will notify observer
    
    observer = null;
    
    // after garbage collection
    
    observer.inform();  // will not notify observer
    
#### Observe till other object is garbage    

If you have an observer that should be notified as long as some other object is not garbage collected

    Observable observable = new Observable();
    Object token = new Object();
    observable.register(new Observer()).keepFor(token);
    
    observable.inform(); // will notify observer
    
    token = null;
    
    // after garbage collection
    
    observable.inform();  // will not notify observer

### Mapped observable

A mapped observable can assign observer to attributes so they will only be notified about those specific attributes.

Can be combined with **release strategies**

    MappedObservable<String> observable = new MappedObservable<String>();
    observable.register("foo", new Observer());
    
    observable.inform("foo"); // will notify observer
    observable.inform("bar"); // will not notify observer
    

Tests
-----

For testing release strategies it's necessary to trigger garbage collection and force WeakHashMaps to tidy up values
with garbage collected keys. This is not guaranteed to work. If some tests fail it might be that the JVM used is not
implemented in a way that allows for triggering garbage collection.

    
    