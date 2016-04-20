package capslock.tg.component.connection;

import capslock.tg.ProtocolData;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by capslock.
 */
@Component
public class ConnectionManager {
    private final ConcurrentHashMap<String, Connection> connectionsMap = new ConcurrentHashMap<>(10000);

    public void registerConnection(final String connId, final Connection connection) {
        final Connection preConnection = connectionsMap.putIfAbsent(connId, connection);
        if (preConnection != null) {
            connection.close();
        }
    }

    public void messageReceivedFromClient(final String connId, final ProtocolData protocolData) {
        final Connection connection = connectionsMap.get(connId);
    }
}
