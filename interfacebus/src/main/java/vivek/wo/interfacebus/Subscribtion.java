package vivek.wo.interfacebus;

/**
 * Created by VIVEK-WO on 2018/3/2.
 */

public class Subscribtion {
    private int priority;
    private String event;
    private BaseSubscribtionInterface subscribtionInterface;

    public Subscribtion(String event, BaseSubscribtionInterface subscribtionInterface) {
        this(0, event, subscribtionInterface);
    }

    public Subscribtion(int priority, String event, BaseSubscribtionInterface subscribtionInterface) {
        this.priority = priority;
        this.event = event;
        this.subscribtionInterface = subscribtionInterface;
    }

    public int getPriority() {
        return priority;
    }

    public String getEvent() {
        return event;
    }

    public BaseSubscribtionInterface get() {
        return subscribtionInterface;
    }
}
