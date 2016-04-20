package capslock.tg.model;

import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * Created by capslock.
 */
@Data
public class ProtocolData {
    private final ByteBuf data;
}
