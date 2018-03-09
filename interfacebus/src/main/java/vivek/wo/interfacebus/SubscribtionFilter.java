package vivek.wo.interfacebus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by VIVEK-WO on 2018/3/2.
 */
public class SubscribtionFilter {

    /**
     * 订阅者优先级 ， 默认为0 ， 数值越大优先级越高
     */
    private int priority;

    private List<String> eventList;

    /**
     * 订阅者事件过滤器
     *
     * @param event
     */
    public SubscribtionFilter(String event) {
        this(0, event);
    }

    /**
     * 订阅者事件过滤器
     *
     * @param priority 订阅者优先级 默认为0 ，数值越大优先级越高
     * @param event    订阅者事件
     */
    public SubscribtionFilter(int priority, String event) {
        eventList = new ArrayList<>();
        this.priority = priority;
        addFilter(event);
    }

    /**
     * 添加订阅者事件
     *
     * @param event 订阅事件
     */
    public void addFilter(String event) {
        if (!eventList.contains(event)) {
            eventList.add(event);
        }
    }

    int getPriority() {
        return priority;
    }

    Iterator<String> iterator() {
        return eventList.iterator();
    }

    List<String> getSubscribtionEventList() {
        return eventList;
    }
}
