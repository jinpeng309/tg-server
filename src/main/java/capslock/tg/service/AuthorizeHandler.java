package capslock.tg.service;

import capslock.tg.api.ApiConstructors;
import capslock.tg.api.ApiObjectDeserializer;
import capslock.tg.api.object.ReqPqObject;
import capslock.tg.api.object.TLObject;
import capslock.tg.component.connection.Connection;
import capslock.tg.model.ProtocolData;
import io.netty.buffer.ByteBuf;

import java.nio.ByteOrder;

/**
 * Created by capslock.
 */
public final class AuthorizeHandler {
    private static enum STATE {
        AWAIT_REQ_PQ, AWAIT_REQ_DH_PARAMS, AWAIT_SET_CLIENT_DH_PARAMS
    }

    private final Connection connection;
    private STATE state = STATE.AWAIT_REQ_PQ;

    public AuthorizeHandler(final Connection connection) {
        this.connection = connection;
    }

    public void process(final ProtocolData protocolData) {
        final ByteBuf data = protocolData.getData().order(ByteOrder.LITTLE_ENDIAN);
        final long messageId = data.readLong();
        final int messageLength = data.readInt();

        final TLObject tlObject = ApiObjectDeserializer.deserialize(data);
        final int constructId = tlObject.getConstructId();

        switch (state) {
            case AWAIT_REQ_PQ:
                if (constructId != ApiConstructors.REQ_PQ) {
                    connection.close();
                } else {
                    final ReqPqObject reqPqObject = (ReqPqObject) tlObject;

                }
                break;
            case AWAIT_REQ_DH_PARAMS:
                break;
            case AWAIT_SET_CLIENT_DH_PARAMS:
                break;
        }
    }
}
