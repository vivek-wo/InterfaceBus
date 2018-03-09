package vivek.wo.interfacebus;

import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by VIVEK-WO on 2018/3/2.
 */

public class InterfaceBus {
    static volatile InterfaceBus interfaceBus;
    private static final InterfaceBusBuilder DEFAULT_BUILDER = new InterfaceBusBuilder();

    private final Map<String, CopyOnWriteArrayList<Subscribtion>> mSubscribtionInterfaceTypes;
    private final Map<BaseSubscribtionInterface, List<String>> mEventTypes;

    private final ThreadLocal<PostThreadState> mCurrentPostThreadState
            = new ThreadLocal<PostThreadState>() {
        @Override
        protected PostThreadState initialValue() {
            return new PostThreadState();
        }
    };

    public static InterfaceBus getDefault() {
        if (interfaceBus == null) {
            synchronized (InterfaceBus.class) {
                if (interfaceBus == null) {
                    interfaceBus = new InterfaceBus();
                }
            }
        }
        return interfaceBus;
    }

    public InterfaceBus() {
        this(DEFAULT_BUILDER);
    }

    InterfaceBus(InterfaceBusBuilder builder) {
        mSubscribtionInterfaceTypes = new HashMap<>();
        mEventTypes = new HashMap<>();
    }

    public void register(BaseSubscribtionInterface subscribtionInterface, String event) {
        register(subscribtionInterface, event, 0);
    }

    public synchronized void register(BaseSubscribtionInterface subscribtionInterface, String event,
                                      int priority) {
        subscribe(subscribtionInterface, event, priority);
    }

    public synchronized void register(BaseSubscribtionInterface subscribtionInterface,
                                      SubscribtionFilter filter) {
        Iterator<String> iterator = filter.iterator();
        while (iterator.hasNext()) {
            subscribe(subscribtionInterface, iterator.next(), filter.getPriority());
        }
    }

    private void subscribe(BaseSubscribtionInterface subscribtionInterface, String event,
                           int priority) {
        CopyOnWriteArrayList<Subscribtion> subscribtionList = mSubscribtionInterfaceTypes.get
                (event);
        Subscribtion subscribtion = new Subscribtion(priority, event, subscribtionInterface);
        if (subscribtionList == null) {
            subscribtionList = new CopyOnWriteArrayList<>();
            mSubscribtionInterfaceTypes.put(event, subscribtionList);
        }
        int size = subscribtionList.size();
        for (int i = 0; i <= size; i++) {
            if (i == size || priority > subscribtionList.get(i).getPriority()) {
                subscribtionList.add(i, subscribtion);
                break;
            }
        }

        List<String> eventTypeList = mEventTypes.get(subscribtionInterface);
        if (eventTypeList == null) {
            eventTypeList = new ArrayList<>();
            mEventTypes.put(subscribtionInterface, eventTypeList);
        }
        eventTypeList.add(event);
    }

    public synchronized void unregister(BaseSubscribtionInterface subscribtionInterface) {
        List<String> eventList = mEventTypes.get(subscribtionInterface);
        if (eventList != null) {
            for (String event : eventList) {
                unsubscribe(subscribtionInterface, event);
            }
        }
    }

    private void unsubscribe(BaseSubscribtionInterface subscribtionInterface, String event) {
        CopyOnWriteArrayList<Subscribtion> subscribtionList = mSubscribtionInterfaceTypes.get
                (event);
        if (subscribtionList != null) {
            int size = subscribtionList.size();
            for (int i = 0; i < size; i++) {
                Subscribtion subscribtion = subscribtionList.get(i);
                if (subscribtionInterface == subscribtion.get()) {
                    subscribtionList.remove(subscribtion);
                    i--;
                    size--;
                }
            }
        }
    }

    public void cancelEventDelivery(String event) {
        PostThreadState postThreadState = mCurrentPostThreadState.get();
        if (!postThreadState.isPosting) {
            throw new InterfaceBusException("This method may only be called from inside event " +
                    "handling methods on the posting thread");
        } else if (event == null) {
            throw new InterfaceBusException("subscriber may not be null.");
        } else if (!postThreadState.publish.getEvent().equals(event)) {
            throw new InterfaceBusException("Only the currently handled event may be aborted");
        }

        postThreadState.canceled = true;
    }

    public void post(String event, Object object) {
        post(new Publish(event, object));
    }

    public void post(Publish publish) {
        PostThreadState postThreadState = mCurrentPostThreadState.get();
        List<Publish> publishList = postThreadState.publishQueue;
        publishList.add(publish);

        if (!postThreadState.isPosting) {
            postThreadState.isMainThread = Looper.getMainLooper() == Looper.myLooper();
            postThreadState.isPosting = true;
            if (postThreadState.canceled) {
                throw new InterfaceBusException("Internal error.Abort state was not reset");
            }
            try {
                while (!publishList.isEmpty()) {
                    postPublish(publishList.remove(0), postThreadState);
                }
            } finally {
                postThreadState.isPosting = false;
                postThreadState.isMainThread = false;
            }
        }
    }

    private void postPublish(Publish publish, PostThreadState postThreadState) {
        CopyOnWriteArrayList<Subscribtion> subscribtionList = null;
        synchronized (this) {
            subscribtionList = mSubscribtionInterfaceTypes.get(publish.getEvent());
        }
        if (subscribtionList != null && !subscribtionList.isEmpty()) {
            for (Subscribtion subscribtion : subscribtionList) {
                postThreadState.publish = publish;
                boolean aborted;
                try {
                    BaseSubscribtionInterface subscribtionInterface = subscribtion.get();
                    subscribtionInterface.onSubscribed(publish);
                    aborted = postThreadState.canceled;
                } finally {
                    postThreadState.publish = null;
                    postThreadState.canceled = false;
                }
                if (aborted) {
                    break;
                }
            }
        }
    }

    final static class PostThreadState {
        final List<Publish> publishQueue = new ArrayList<>();
        Publish publish;
        boolean isPosting;
        boolean isMainThread;
        boolean canceled;
    }

}
