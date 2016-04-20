package capslock.tg.processor;

import capslock.tg.model.ProtocolData;
import capslock.tg.component.connection.Connection;

/**
 * Created by alvin.
 */
public interface PacketProcessor {
    void process(final Connection connection, final ProtocolData data);
}
