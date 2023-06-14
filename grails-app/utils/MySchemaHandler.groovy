
import groovy.util.logging.Slf4j
import org.grails.datastore.gorm.jdbc.schema.SchemaHandler

import javax.sql.DataSource
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

@Slf4j
class MySchemaHandler implements SchemaHandler {
    private List<String> existedSchemaList = []
    private List<String> CX_SCHEMA_LIST = ['aa', 'bb']
    // private static List<String> TARGET_SCHEMA_LIST = ['cx_david', 'cx_john']
    @Override
    Collection<String> resolveSchemaNames(DataSource dataSource) {
        log.info("resolveSchemaNames(): ENTER .....")
        try (Connection connection = dataSource.getConnection()) {
            ResultSet schemas = connection.getMetaData().getSchemas()
            log.info("=========================================")
            while(schemas.next()) {
                String schemaName = schemas.getString("TABLE_SCHEM")
                log.debug("resolveSchemaNames(): schema: {}",  schemaName)
                if (schemaName.startsWith("CX_")) existedSchemaList << schemaName
            }

        }
        log.info("resolveSchemaNames(): existedSchemaList: {}", existedSchemaList)
        return  CX_SCHEMA_LIST
    }

    @Override
    void useSchema(Connection connection, String name) {
        String lowerCaseSchemaName = name.toLowerCase()
        log.info("useSchema(): name: {} ==> {}", name, lowerCaseSchemaName)
    }

    @Override
    void useDefaultSchema(Connection connection) {
        log.info("useDefaultSchema(): ENTER ...........")
    }

    @Override
    void createSchema(Connection connection, String name) {
        String lowerCaseSchemaName = name.toLowerCase()
        log.info("createSchema(): name: {} =>{}", name, lowerCaseSchemaName)
        Statement createSchemaStmt =  connection.createStatement()
        String sql = "create schema ${lowerCaseSchemaName} "
        boolean rc = createSchemaStmt.execute(sql)
        // log.info("createSchema(): ${sql} => {}", rc)
    }
}
