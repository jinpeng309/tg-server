package capslock.tg.service;

import capslock.tg.component.connection.Connection;
import capslock.tg.model.ProtocolData;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by capslock.
 */
@Service
public final class AuthorizeService {
    private final ConcurrentHashMap<String, AuthorizeHandler> handlerMap = new ConcurrentHashMap<>(10000);

    public void process(final Connection connection, final ProtocolData protocolData) {
        AuthorizeHandler handler = handlerMap.get(connection.getConnId());
        if (handler == null) {
            handler = new AuthorizeHandler(connection);
        }
        handlerMap.put(connection.getConnId(), handler);
        try{
            handler.process(protocolData);
        } catch (RuntimeException ex){
            connection.close();
        }
    }
}
