package info.jackpines.core.interfaces;

import info.jackpines.core.models.Entity;

import java.util.List;
import java.util.UUID;

public interface Persistence {

    boolean storeEntity(Entity entity);

    List<Entity> retrieveEntities(Class entityClass);

    List<Entity> retrieveEntities(String tableName);

    Entity retrieveEntityById(Class entityClass, UUID id);

    Entity retrieveEntityById(String tableName, UUID id);
}
