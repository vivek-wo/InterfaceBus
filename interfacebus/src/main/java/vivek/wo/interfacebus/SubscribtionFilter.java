package vivek.wo.interfacebus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by VIVEK-WO on 2018/3/2.
 */
public class SubscribtionFilter {

    private int priority;

    private List<String> eventList;

    public SubscribtionFilter(String event) {
        this(0, event);
    }

    public SubscribtionFilter(int priority, String event) {
        eventList = new ArrayList<>();
        this.priority = priority;
        addFilter(event);
    }

    public void addFilter(String event) {
        if (!eventList.contains(event)) {
            eventList.add(event);
        }
    }

    public int getPriority() {
        return priority;
    }

    public Iterator<String> iterator() {
        return eventList.iterator();
    }

    public List<String> getSubscribtionEventList() {
        return eventList;
    }
}
