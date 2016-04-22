package capslock.tg.api.object;

import capslock.tg.api.ApiConstructors;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by alvin.
 */
@Data
@AllArgsConstructor
public final class ResPqObject extends TLObject {
    private final static int CONSTRUCTOR_ID = ApiConstructors.RES_PQ;

    @Override
    public int getConstructId() {
        return CONSTRUCTOR_ID;
    }
}
