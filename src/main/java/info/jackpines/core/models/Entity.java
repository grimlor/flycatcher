package info.jackpines.core.models;

import java.util.UUID;

public abstract class Entity {

    private final UUID id;

    Entity(final UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public abstract String getTableName();
}
