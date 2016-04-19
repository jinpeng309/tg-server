package capslock.tg.net;

import lombok.Data;

/**
 * Created by capslock.
 */
@Data
public class Packet {
    private final long authId;
    private final long messageId;
    private final byte[] data;
}
