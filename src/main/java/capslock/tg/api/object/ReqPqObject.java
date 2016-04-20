package capslock.tg.api.object;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by capslock.
 */
@Data
@AllArgsConstructor
public final class ReqPqObject extends TLObject {
    private final static int CONSTRUCTOR_ID = 0x60469778;

    private final byte[] nonce;

    @Override
    public int getConstructor() {
        return CONSTRUCTOR_ID;
    }
}
