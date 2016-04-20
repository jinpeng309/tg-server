package capslock.tg.api;

import capslock.tg.api.object.ReqPqObject;
import capslock.tg.api.object.TLObject;
import capslock.tg.api.serializer.ReqPqObjectDeserializer;
import capslock.tg.api.serializer.TLObjectDeserializer;
import com.google.common.collect.ImmutableMap;
import io.netty.buffer.ByteBuf;

/**
 * Created by capslock.
 */
public final class ApiObjectDeserializer {
    private static final ImmutableMap<Integer, TLObjectDeserializer> DESERIALIZER_MAP;

    static {
        final ImmutableMap.Builder<Integer, TLObjectDeserializer> mapBuilder = ImmutableMap.builder();
        mapBuilder.put(ApiConstructors.REQ_PQ, new ReqPqObjectDeserializer());
        DESERIALIZER_MAP = mapBuilder.build();
    }

    public static TLObject deserialize(final ByteBuf data) {
        final int constructor = data.readInt();
        final TLObjectDeserializer desrializer = DESERIALIZER_MAP.get(constructor);
        return new ReqPqObject();
    }
}
