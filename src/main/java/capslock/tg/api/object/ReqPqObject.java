package capslock.tg.api.object;

import capslock.tg.api.ApiConstructors;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by capslock.
 */
@Data
@AllArgsConstructor
public final class ReqPqObject extends TLObject {
    private final static int CONSTRUCTOR_ID = ApiConstructors.REQ_PQ;

    private final byte[] nonce;

    @Override
    public int getConstructId() {
        return CONSTRUCTOR_ID;
    }
}
