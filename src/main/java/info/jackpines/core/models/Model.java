package info.jackpines.core.models;

import java.util.UUID;

public class Model {
    private UUID id;

    public Model(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
