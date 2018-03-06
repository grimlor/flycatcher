package info.jackpines.core.models;

import java.util.UUID;

public abstract class Entity {

    private UUID id;

    Entity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public abstract String getTableName();
}
