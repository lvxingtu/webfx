package mongoose.entities.markers;

import mongoose.entities.DocumentLine;
import webfx.framework.orm.entity.Entity;
import webfx.framework.orm.entity.EntityId;

/**
 * @author Bruno Salmon
 */
public interface EntityHasDocumentLine extends Entity, HasDocumentLine {

    @Override
    default void setDocumentLine(Object documentLine) {
        setForeignField("documentLine", documentLine);
    }

    @Override
    default EntityId getDocumentLineId() {
        return getForeignEntityId("documentLine");
    }

    @Override
    default DocumentLine getDocumentLine() {
        return getForeignEntity("documentLine");
    }

}