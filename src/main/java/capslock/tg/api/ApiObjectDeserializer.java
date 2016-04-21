package capslock.tg.api;

import capslock.tg.annotations.ApiConstructorId;
import capslock.tg.api.deserializer.TLObjectDeserializer;
import capslock.tg.api.object.TLObject;
import com.google.common.collect.ImmutableMap;
import io.netty.buffer.ByteBuf;
import org.reflections.Reflections;

import java.util.Optional;
import java.util.Set;

/**
 * Created by capslock.
 */
public final class ApiObjectDeserializer {
    private static final String DESERIALIZER_PACKAGE_PREFIX = "capslock.tg.api.deserializer";
    private static final ImmutableMap<Integer, TLObjectDeserializer> DESERIALIZER_MAP;

    static {
        final ImmutableMap.Builder<Integer, TLObjectDeserializer> deserializerMapBuilder = ImmutableMap.builder();
        final Reflections reflections = new Reflections(DESERIALIZER_PACKAGE_PREFIX);
        final Set<Class<?>> deserializerClass = reflections.getTypesAnnotatedWith(ApiConstructorId.class);
        deserializerClass.forEach(clazz -> {
            try {
                final int constructId = clazz.getAnnotation(ApiConstructorId.class).value();
                final TLObjectDeserializer deserializer = (TLObjectDeserializer) clazz.newInstance();
                deserializerMapBuilder.put(constructId, deserializer);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        DESERIALIZER_MAP = deserializerMapBuilder.build();

    }

    public static TLObject deserialize(final ByteBuf data) {
        final int constructId = data.readInt();
        final Optional<TLObjectDeserializer> deserializer = Optional.ofNullable(DESERIALIZER_MAP.get(constructId));
        return deserializer
                .orElseThrow(() -> new RuntimeException("unknown constructId " + constructId))
                .deserialize(data);
    }
}
