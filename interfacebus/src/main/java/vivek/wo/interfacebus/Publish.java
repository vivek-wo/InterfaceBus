package vivek.wo.interfacebus;

/**
 * Created by VIVEK-WO on 2018/3/2.
 */

public class Publish {
    String event ;
    Object object;

    public Publish(String event, Object object) {
        this.event = event;
        this.object = object;
    }

    public String getEvent() {
        return event;
    }

    public Object getObject() {
        return object;
    }
}
