package capslock.tg.filter;

import capslock.tg.ProtocolData;
import capslock.tg.component.connection.Connection;

/**
 * Created by alvin.
 */
public interface PacketFilter {
    boolean filter(final Connection connection, final ProtocolData data);
}
