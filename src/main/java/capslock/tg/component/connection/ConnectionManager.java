package capslock.tg.component.connection;

import capslock.tg.ProtocolData;
import capslock.tg.filter.PacketFilter;
import capslock.tg.processor.PacketProcessor;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by capslock.
 */
@Component
public class ConnectionManager {
    private final ConcurrentHashMap<String, Connection> connectionsMap = new ConcurrentHashMap<>(10000);
    private final LinkedList<PacketProcessor> processors = new LinkedList<>();
    private final LinkedList<PacketFilter> filters = new LinkedList<>();

    public void registerConnection(final String connId, final Connection connection) {
        final Connection preConnection = connectionsMap.putIfAbsent(connId, connection);
        if (preConnection != null) {
            connection.close();
        }
    }

    public void clientClosed(final String connId) {
        connectionsMap.remove(connId);
    }

    public void messageReceivedFromClient(final String connId, final ProtocolData protocolData) {
        final Connection connection = connectionsMap.get(connId);
        processors.forEach(packetProcessor -> packetProcessor.process(connection, protocolData));
        for (final PacketFilter filter : filters) {
            final boolean stop = filter.filter(connection, protocolData);
            if (stop) {
                return;
            }
        }

    }
}
