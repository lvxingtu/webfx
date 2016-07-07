package naga.framework.orm.entity.resultset;

import naga.framework.orm.entity.EntityId;
import naga.framework.expression.mocks.ParserDomainModelReaderMock;
import naga.framework.expression.mocks.CompilerDomainModelReaderMock;
import naga.framework.expression.sqlcompiler.sql.DbmsSqlSyntaxOptions;
import org.junit.Test;

/**
 * @author Bruno Salmon
 */
public class EntityChangesToUpdateBatchGeneratorTest {

    @Test
    public void test() throws Exception {
        EntityChangesToUpdateBatchGenerator.generateUpdateBatch(EntityChangesBuilder.create()
                .addFieldChange(EntityId.create("Document", -5), "cancelled", true)
                .addDeletedEntityId(EntityId.create("Document", 12))
                .addDeletedEntityId(EntityId.create("Document", 13))
                .build(), 3, DbmsSqlSyntaxOptions.POSTGRES_SYNTAX, new ParserDomainModelReaderMock(), new CompilerDomainModelReaderMock());
    }
}
