package capslock.tg.model;

import capslock.tg.type.PacketSourceType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by alvin.
 */
@AllArgsConstructor
@Data
public final class Packet {
    private final ProtocolData data;
    private final PacketSourceType sourceType;
}
