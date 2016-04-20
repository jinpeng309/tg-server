package capslock.tg.api.deserializer;

import capslock.tg.api.object.TLObject;
import io.netty.buffer.ByteBuf;

/**
 * Created by capslock.
 */
public interface TLObjectDeserializer {
    TLObject deserialize(final ByteBuf data);
}
