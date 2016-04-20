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
    private static final String DESERIALIZER_PACKAGE_PREFIX = "capslock.tg.api.serializer";
    private static final ImmutableMap<Integer, TLObjectDeserializer> DESERIALIZER_MAP;

    static {
        final ImmutableMap.Builder<Integer, TLObjectDeserializer> deserializerMapBuilder = ImmutableMap.builder();
        DESERIALIZER_MAP = deserializerMapBuilder.build();

        final Reflections reflections = new Reflections(DESERIALIZER_PACKAGE_PREFIX);
        final Set<Class<?>> deserializerClass = reflections.getTypesAnnotatedWith(ApiConstructorId.class);
        deserializerClass.forEach(clazz -> {
            try {
                final int constructorId = clazz.getAnnotation(ApiConstructorId.class).value();
                final TLObjectDeserializer deserializer = (TLObjectDeserializer) clazz.newInstance();
                deserializerMapBuilder.put(constructorId, deserializer);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public static Optional<TLObject> deserialize(final ByteBuf data) {
        final int constructor = data.readInt();
        final Optional<TLObjectDeserializer> deserializer = Optional.ofNullable(DESERIALIZER_MAP.get(constructor));
        return deserializer.flatMap(objectDeserializer -> Optional.of(objectDeserializer.deserialize(data)));
    }
}
