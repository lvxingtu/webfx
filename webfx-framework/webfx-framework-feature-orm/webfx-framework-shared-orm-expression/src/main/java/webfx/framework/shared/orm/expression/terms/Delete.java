package webfx.framework.shared.orm.expression.terms;


import webfx.framework.shared.orm.expression.Expression;

/**
 * @author Bruno Salmon
 */
public final class Delete<T> extends DqlStatement<T> {

    public Delete(Object domainClass, String domainClassAlias, Expression<T> where) {
        this(null, domainClass, domainClassAlias, null, null, null, where);
    }

    public Delete(Object id, Object domainClass, String domainClassAlias, String definition, String sqlDefinition, Object[] sqlParameters, Expression<T> where) {
        super(id, domainClass, domainClassAlias, definition, sqlDefinition, sqlParameters, where, null, null);
    }
}
