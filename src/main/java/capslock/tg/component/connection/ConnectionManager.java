package capslock.tg.component.connection;

import capslock.tg.filter.PacketFilter;
import capslock.tg.model.ProtocolData;
import capslock.tg.processor.PacketProcessor;
import capslock.tg.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private AuthorizeService authorizeService;

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
        final long authId = protocolData.getData().readLong();
        if (authId == 0) {
            authorizeService.process(connection, protocolData);
        }
    }
}
