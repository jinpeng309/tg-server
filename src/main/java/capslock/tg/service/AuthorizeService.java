package capslock.tg.service;

import capslock.tg.component.connection.Connection;
import capslock.tg.model.ProtocolData;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.WorkQueueProcessor;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Created by capslock.
 */
@Service
public final class AuthorizeService {
    private final HashMap<String, AuthorizeHandler> handlerMap = new HashMap<>(10000);
    private final EmitterProcessor<HandlerItem> handlerProcessQueue = EmitterProcessor.create();
    private final WorkQueueProcessor<ProcessItem> authProcessQueue = WorkQueueProcessor.create();

    @PostConstruct
    private void init() {
        handlerProcessQueue.consume(processItem -> processItem.getHandler().process(processItem.getData()));
        authProcessQueue.consume(processItem -> {
            final String connId = processItem.getConnection().getConnId();
            AuthorizeHandler handler = handlerMap.get(connId);
            if (handler == null) {
                handler = new AuthorizeHandler(processItem.getConnection());
                handlerMap.put(connId, handler);
            }
            handlerProcessQueue.onNext(new HandlerItem(handler, processItem.getData()));
        });
    }

    public void process(final Connection connection, final ProtocolData protocolData) {
        authProcessQueue.onNext(new ProcessItem(connection, protocolData));
    }

    @Data
    private static class ProcessItem {
        private final Connection connection;
        private final ProtocolData data;
    }

    @Data
    private static class HandlerItem {
        private final AuthorizeHandler handler;
        private final ProtocolData data;
    }
}
