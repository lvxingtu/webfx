package mongoose.entities.impl;

import mongoose.entities.Teacher;
import webfx.framework.orm.entity.EntityId;
import webfx.framework.orm.entity.EntityStore;
import webfx.framework.orm.entity.impl.DynamicEntity;

/**
 * @author Bruno Salmon
 */
public final class TeacherImpl extends DynamicEntity implements Teacher {

    public TeacherImpl(EntityId id, EntityStore store) {
        super(id, store);
    }
}