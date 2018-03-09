package vivek.wo.interfacebus;

/**
 * Created by VIVEK-WO on 2018/3/2.
 */

public class Subscribtion {
    private int priority;
    private String event;
    private BaseSubscribtionInterface subscribtionInterface;

    Subscribtion(String event, BaseSubscribtionInterface subscribtionInterface) {
        this(0, event, subscribtionInterface);
    }

    Subscribtion(int priority, String event, BaseSubscribtionInterface subscribtionInterface) {
        this.priority = priority;
        this.event = event;
        this.subscribtionInterface = subscribtionInterface;
    }

    int getPriority() {
        return priority;
    }

    String getEvent() {
        return event;
    }

    BaseSubscribtionInterface get() {
        return subscribtionInterface;
    }
}
