package vivek.wo.interfacebus;

/**
 * Created by VIVEK-WO on 2018/3/2.
 */

public class InterfaceBusException extends RuntimeException {
    public InterfaceBusException(String message) {
        super(message);
    }

    public InterfaceBusException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterfaceBusException(Throwable cause) {
        super(cause);
    }
}
