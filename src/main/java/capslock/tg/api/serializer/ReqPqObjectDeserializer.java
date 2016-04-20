package capslock.tg.api.serializer;

import capslock.tg.api.object.ReqPqObject;
import capslock.tg.api.object.TLObject;
import io.netty.buffer.ByteBuf;

/**
 * Created by alvin.
 */
public class ReqPqObjectDeserializer implements TLObjectDeserializer {
    @Override
    public TLObject deserialize(final ByteBuf data) {
        return new ReqPqObject();
    }
}
