package capslock.tg.api.deserializer;

import capslock.tg.annotations.ApiConstructorId;
import capslock.tg.api.ApiConstructors;
import capslock.tg.api.object.ReqPqObject;
import capslock.tg.api.object.TLObject;
import io.netty.buffer.ByteBuf;

/**
 * Created by capslock.
 */
@ApiConstructorId(value = ApiConstructors.REQ_PQ)
public class ReqPqObjectDeserializer implements TLObjectDeserializer {
    @Override
    public TLObject deserialize(final ByteBuf data) {
        final byte[] nonce = new byte[16];
        data.readBytes(nonce);
        return new ReqPqObject(nonce);
    }
}
