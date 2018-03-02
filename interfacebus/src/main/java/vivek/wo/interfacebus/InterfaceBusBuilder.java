package vivek.wo.interfacebus;

/**
 * Created by VIVEK-WO on 2018/3/2.
 */

public class InterfaceBusBuilder {

    InterfaceBus build() {
        return new InterfaceBus(this);
    }
}
