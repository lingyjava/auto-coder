package com.lingyuan.autocoder.common;

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.postgresql.parser.PGSQLStatementParser;
import com.google.common.base.CaseFormat;
import com.lingyuan.autocoder.api.enums.DatabaseTypeEnum;
import com.lingyuan.autocoder.api.exception.BusinessException;
import com.lingyuan.autocoder.api.model.TableColumnModel;
import com.lingyuan.autocoder.api.model.TableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LingYuan
 * @description SQL 解析工具
 */
public class SQLParseUtil {

    /**
     * 解析 DDL
     * */
    public static SQLCreateTableStatement parseDDL(String ddl) {
        return parseDDL(ddl, DatabaseTypeEnum.MYSQL.getName());
    }

    /**
     * 解析 DDL
     * */
    public static SQLCreateTableStatement parseDDL(String ddl, String dbType) {
        if (ddl == null || ddl.isEmpty()) {
            throw new BusinessException("DDL为空");
        }
        if (dbType == null || dbType.isEmpty()) {
            dbType = "mysql";
        }
        try {
            if (DatabaseTypeEnum.MYSQL.getName().equalsIgnoreCase(dbType)) {
                return (SQLCreateTableStatement) new MySqlStatementParser(ddl).parseStatement();
            }
            if (DatabaseTypeEnum.ORACLE.getName().equalsIgnoreCase(dbType)) {
                return (SQLCreateTableStatement) new OracleStatementParser(ddl).parseStatement();
            }
            if (DatabaseTypeEnum.POSTGRESQL.getName().equalsIgnoreCase(dbType)) {
                return (SQLCreateTableStatement) new PGSQLStatementParser(ddl).parseStatement();
            }
        } catch (Exception e) {
            throw new BusinessException("SQL解析失败" + e.getMessage());
        }
        throw new BusinessException("未知的数据库类型");
    }

    /**
     * SQLCreateTableStatement to TableModel
     * */
    public static TableModel buildTableModel(SQLCreateTableStatement statement) {
        if (statement == null) {
            return null;
        }
        TableModel tableModel = new TableModel();
        tableModel.setTableName(statement.getTableName().replaceAll("`", ""));
        tableModel.setDatabaseName(statement.getSchema() != null ? statement.getSchema().replaceAll("`", "") : null);

        List<TableColumnModel> columns = new ArrayList<>();
        for (SQLColumnDefinition item : statement.getColumnDefinitions()) {
            TableColumnModel column = new TableColumnModel();
            column.setColumnType(item.getDataType().getName());
            column.setColumnName(item.getName().getSimpleName().replaceAll("`", ""));
            column.setColumnComment(item.getComment() != null ? item.getComment().toString().replaceAll("'", "") : "");
            column.setPrimaryKey(item.isPrimaryKey());
            column.setFieldType(JDBCTypeConvertUtil.toJava(column.getColumnType()));
            column.setFieldName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column.getColumnName()));
            columns.add(column);
        }
        tableModel.setColumns(columns);
        return tableModel;
    }
}
