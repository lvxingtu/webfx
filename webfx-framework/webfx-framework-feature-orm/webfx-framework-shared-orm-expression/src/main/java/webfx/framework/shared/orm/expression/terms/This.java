package webfx.framework.shared.orm.expression.terms;

import webfx.framework.shared.orm.expression.lci.DomainReader;
import webfx.extras.type.Type;

/**
 * @author Bruno Salmon
 */
public final class This<T> extends AbstractExpression<T> {

    public static final This SINGLETON = new This();

    private This() {
        super(9);
    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public Object evaluate(T domainObject, DomainReader<T> domainReader) {
        return domainObject;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append("this");
    }
}
