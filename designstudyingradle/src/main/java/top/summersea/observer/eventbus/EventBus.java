package top.summersea.observer.eventbus;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.Executor;

public class EventBus {
    private final Executor executor;
    private final ObserverRegistry observerRegistry = new ObserverRegistry();

    public EventBus() {
        this(MoreExecutors.directExecutor());
    }

    protected EventBus(Executor executor) {
        this.executor = executor;
    }

    public void register(Object object) {
        observerRegistry.register(object);
    }

    public void post(Object event) {
        List<ObserverAction> matchedObserverActions = observerRegistry.getMatchedObserverActions(event);
        for (ObserverAction observerAction : matchedObserverActions) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    observerAction.execute(event);
                }
            });
        }
    }
}
