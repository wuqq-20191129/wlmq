/*
 * Amendment History:
 *
 * Date          By             Description
 * ----------    -----------    -------------------------------------------
 * 2004-06-01    Rong Weitao    Create the class
 2005-4-05     hejianjun      修改DbHelper (String aDataSourceName),closeConnection()
 */

/*
 The DbHelper class is created to help the developer to access the database.

 Instructions for use in your script:
 1) Establish a connection to the given database URL

 DbHelper(String aDataSourceName)    - pass a DataSource Name string to get connection
 DbHelper(DataSource aDataSource)    - pass a DataSource Object to get connection
 DbHelper(String driverName, String serverName, String dbName, String userId, String password)
 - pass JDBC driver name, server name, database name, login Id, login Password to get connection.

 2) Select records from database

 Call the following method by passing a SQL SELECT statement to select records. Return TURE if records exist,
 otherwise return FALSE. The corresponding ResultSet can be accessed by calling getItemXXXValue() method.

 getFirstDocument(String sql)
 - pass a SQL SELECT statement to check the corresponding records exist or not
 e.g.  sql = "select * from TABLE";
 DbHelper.getFirstDocument(sql);

 getFirstDocument(String sql, Object[] pStmtValues)
 - pass a SQL SELECT statement and a value object(used to enter into the prepared statement) to check
 whether the corresponding records exist or not
 e.g.  // Assumes the data type for COLUMN_A is VARCHAR, for COLUMN_B is NUMBER.
 sql = "select COLUMN_A, COLUMN_B, COLUMN_C from TABLE where COLUMN_A = ? and COLUMN_B = ?";
 DbHelper.getFirstDocument(sql, new Object[]{new String(COLUMN_A_VALUE),
 new Integer(COLUMN_B_VALUE)});
 NOTE: The value must be passed by sequence.


 Call the following method by passing a Stored Procedure's execution string to select records.
 Return TRUE if ResultSet exists, otherwise return FALSE. The first ResultSet(if exists) can be accessed
 by calling getItemXXXValue() method.

 getFirstDocumentSP(String sql)
 - pass a Stored Procedure's execution string
 e.g.  sql = "execute sp_name";
 getFirstDocumentSP(sql);

 getFirstDocumentSP(String sql, Object[] pStmtValues)
 - pass a Stored Procedure's execution string and a value object(used to enter into the prepared
 statement)
 e.g. sql = "execute sp_name @parm = ?";   // assumes the data type for @parm is VARCHAR
 getFirstDocumentSP(sql, new Object[]{new String(parm_value)});

 NOTE: Only the first row record(if exists) can be accessed after calling the above getFirstXXX() method.
 In order to access the next record, please call the getNextDocument() method.

 getNextDocument()
 - Check whether the next record exists or not, make the developer ready to access the record
 by calling getItemXXXValue().


 Call the following method by passing a SQL statement to select records. Return null or a DOM object holding
 the query result (only the first ResultSet is returned if there are multiple results)

 getDOM (String sql, String aElementName, boolean rtnNull)
 - @param sql    the SQL statement to execute
 - @param aElementName   a child node's name, its parent node is "Result"
 if aElementName is null or empty, "Row" will be used as default TagName
 - @param rtnNull    indicate whether to return null or not if the query result not found, true for yes

 getDOM (String sql, Object[] pStmtValues, String aElementName, boolean rtnNull)
 - @param sql    the SQL statement to execute
 - @param pStmtValues    the values(passed by sequence) to enter into the prepared statement.
 If there are no values, use <code>null</code>
 - @param aElementName   a child node's name, its parent node is "Result"
 if aElementName is null or empty, "Row" will be used as default TagName
 - @param rtnNull    indicate whether to return null or not if the query result not found, true for yes

 Below is the returned DOM structure.
 Result
 - aElementName
 - column1Name
 - column1Value
 - column2Name
 - column2Value
 ......

 - aElementName
 - column1Name
 - column1Value
 - column2Name
 - column2Value
 ......
 ......

 NOTE: For duplicated column names, the returned DOM object will have the same TagName.

 3) Insert, delete or update records in database

 Call the following method to insert, delete or update records in the database. Return either the row count
 for INSERT, DELETE or UPDATE statements; or 0 for SQL statements that return nothing.

 executeUpdate(String sql)
 - pass a SQL INSERT, DELETE, UPDATE statement or a SQL statement that return nothing

 executeUpdate(String sql, Object[] pStmtValues)
 - pass a SQL INSERT, DELETE, UPDATE statement or a SQL statement that return nothing and a
 value object(used to enter into the prepared statement)

 Call the following method to control the transaction.
 getAutoCommit()
 - get the connection's auto-commit mode
 setAutoCommit(boolean autoCommit)
 - set the connection's auto-commit mode
 commitTran()
 - commit a transaction
 rollbackTran()
 - rollback a transaction

 4) Close a connection
 closeConnection()        - close the ResultSet, PreparedStatement and Connection

 */
package com.goldsign.systemmonitor.util;

import java.sql.*;

import java.io.InputStream;
import java.math.BigDecimal;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.DOMException;
import org.apache.log4j.Logger;
//import sun.jdbc.odbc.ee.DataSource;

/**
 * Help developer to access database
 */
public class MonitorDBHelper {

    /**
     * Mesage logger
     */
    private static Logger logger = Logger.getLogger(MonitorDBHelper.class.getName());
    /**
     * Null sql type
     */
    public static NullSQLType NULL_CHAR = new NullSQLType(Types.CHAR);
    public static NullSQLType NULL_DATE = new NullSQLType(Types.DATE);
    public static NullSQLType NULL_DECIMAL = new NullSQLType(Types.DECIMAL);
    public static NullSQLType NULL_DOUBLE = new NullSQLType(Types.DOUBLE);
    public static NullSQLType NULL_FLOAT = new NullSQLType(Types.FLOAT);
    public static NullSQLType NULL_INTEGER = new NullSQLType(Types.INTEGER);
    public static NullSQLType NULL_LONGVARCHAR = new NullSQLType(
            Types.LONGVARCHAR);
    public static NullSQLType NULL_TIME = new NullSQLType(Types.TIME);
    public static NullSQLType NULL_TIMESTAMP = new NullSQLType(Types.TIMESTAMP);
    public static NullSQLType NULL_VARCHAR = new NullSQLType(Types.VARCHAR);
    /**
     * use driver,database,login id,password to new a connection.
     */
    private DataSource ds = null;
    private Connection con = null; // database connection
    private ResultSet rs; // result set
    private PreparedStatement stmt = null; // statement

    /**
     * Constructor : Use driver name, database url, login id, password to new a
     * connection.
     *
     * @param driverName - The driver name of the database server e.g.
     * "com.sybase.jdbc2.jdbc.SybDriver"
     * @param serverName - The url to the dataabse server e.g.
     * "jdbc:sybase:Tds:123.123.123.123:5000"
     * @param dbName - The database name in the server
     * @param userId - The user Id to login the database server
     * @param password - The password of the user
     *
     * @exception SQLException - Database error occurs
     * @exception ClassNotFoundException - The driver class is not found
     */
    public MonitorDBHelper(String driverName, String serverName, String dbName,
            String userId, String password) throws SQLException,
            ClassNotFoundException {
        if (logger.isDebugEnabled()) {
            logger.debug("driverName: " + driverName);
            logger.debug("serverName: " + serverName);
            logger.debug("dbName: " + dbName);
            logger.debug("userId: " + userId);
            logger.debug("password: " + password);
        }

        Class.forName(driverName);
        con = DriverManager.getConnection(serverName + "/" + dbName, userId,
                password);
    }

    /**
     * Constructor : Use driver name, database url, login id, password to new a
     * connection.
     *
     * @param driverName - The driver name of the database server e.g.
     * "com.sybase.jdbc2.jdbc.SybDriver"
     * @param aURL - The url to the database server e.g.
     * "jdbc:sybase:Tds:123.123.123.123:5000/sa"
     * @param userId - The user Id to login the database server
     * @param password - The password of the user
     *
     * @exception SQLException - Database error occurs
     * @exception ClassNotFoundException - The driver class is not found
     */
    public MonitorDBHelper(String driverName, String aURL, String userId,
            String password) throws SQLException, ClassNotFoundException {
        if (logger.isDebugEnabled()) {
            logger.debug("driverName: " + driverName);
            logger.debug("URL: " + aURL);
            logger.debug("userId: " + userId);
            logger.debug("password: " + password);
        }

        Class.forName(driverName);
        this.con = DriverManager.getConnection(aURL, userId, password);
    }

    /**
     * Use the given data source name aDataSourceName to look up a datbase and
     * get a connection.
     */
    public MonitorDBHelper(String aDataSourceName) throws NamingException, SQLException {
        //      this.con = new ConnectionManager().getConnection();

        //    if(this.con !=null&&!this.con.isClosed()) // add by hejj 使用单连接实例
        //        return;

        InitialContext ctx = null;
        try {
            //Logger.log(false, "Debug log : get data source with default context.");
            ctx = new InitialContext();
            System.out.println("aDataSourceName======" + aDataSourceName);
            ds = (DataSource) ctx.lookup(aDataSourceName);




            this.con = ds.getConnection();

            //  this.con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (NamingException ne) {
            throw ne;
        } catch (SQLException se) {
            throw se;
        } finally {
            ctx.close();
        }

    }

    public MonitorDBHelper(String aDataSourceName, boolean isNew)
            throws NamingException, SQLException {

        InitialContext ctx = null;
        try {
            //Logger.log(false, "Debug log : get data source with default context.");
            ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(aDataSourceName);

            this.con = ds.getConnection();
        } catch (NamingException ne) {
            throw ne;
        } catch (SQLException se) {
            throw se;
        } finally {
            ctx.close();
        }

    }

    /**
     * Use the given data source aDataSource to get a connection.
     */
    public MonitorDBHelper(DataSource aDataSource) throws SQLException {
        this.con = aDataSource.getConnection();
    }

    /**
     * Get the value of a given column in the current row of the result set. The
     * column must be string type. The value is trimmed. Empty string is
     * returned if the value is null.
     */
    public String getItemValue(String columnName) throws SQLException {
        String value = this.rs.getString(columnName);
        value = (value == null ? "" : value.trim()); //for handling null value in the table
        return value;
    }

    public String getItemValueWithoutTrim(String columnName)
            throws SQLException {
        String value = this.rs.getString(columnName);
        value = (value == null ? "" : value); //for handling null value in the table
        return value;
    }

    /**
     * Check whether the value of given column is null or not. The column must
     * be string type.
     */
    public boolean itemIsNull(String columnName) throws SQLException {
        String value = this.rs.getString(columnName);
        if (value == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the value of a given column (String Type) in the current row of the
     * result set.
     *
     * @return String - The value is NOT trimmed, and null if the value in
     * database if sql null.
     */
    public String getItemTrueValue(String columnName) throws SQLException {
        String value = this.rs.getString(columnName);
        return value;
    }

    /**
     * Get the value of a given column (Short Type) in the current row of the
     * result set.
     */
    public short getItemShortValue(String columnName) throws SQLException {
        return rs.getShort(columnName);
    }

    /**
     * Get the value of a given column (int Type) in the current row of the
     * result set.
     */
    public int getItemIntValue(String columnName) throws SQLException {
        return rs.getInt(columnName);
    }

    /**
     * Get the value of a given column (Integer Type) in the current row of the
     * result set.
     */
    public Integer getItemIntegerValue(String columnName) throws SQLException,
            NumberFormatException {
        Integer integer = null;
        String value = this.rs.getString(columnName);
        value = (value == null ? "" : value.trim());

        if (value != null && !value.equals("")) {
            integer = new Integer(value);
        }

        return integer;
    }

    /**
     * Get the value of a given column (Long Type) in the current row of the
     * result set.
     */
    public long getItemLongValue(String columnName) throws SQLException {
        return rs.getLong(columnName);
    }

    /**
     * Get the value of a given column (Float Type) in the current row of the
     * result set.
     */
    public float getItemFloatValue(String columnName) throws SQLException {
        return rs.getFloat(columnName);
    }

    /**
     * Get the value of a given column (Double Type) in the current row of the
     * result set.
     */
    public double getItemDoubleValue(String columnName) throws SQLException {
        return rs.getDouble(columnName);
    }

    /**
     * Get the value of a given column (BigDecimal Type) in the current row of
     * the result set.
     */
    public BigDecimal getItemBigDecimalValue(String columnName)
            throws SQLException {
        return rs.getBigDecimal(columnName);
    }

    /**
     * Get the value of a given column (java.sql.Date Type) in the current row
     * of the result set.
     */
    public java.sql.Date getItemDateValue(String columnName)
            throws SQLException {
        java.sql.Date date = rs.getDate(columnName);
        return date;
    }

    /**
     * Get the value of a given column (java.sql.Time Type) in the current row
     * of the result set.
     */
    public java.sql.Time getItemTimeValue(String columnName)
            throws SQLException {
        java.sql.Time time = rs.getTime(columnName);
        return time;
    }

    /**
     * Get the value of a given column (java.sql.Timestamp Type) in the current
     * row of the result set.
     */
    public Timestamp getItemTimestampValue(String columnName)
            throws SQLException {
        return rs.getTimestamp(columnName);
    }

    /**
     * Get the value of a given column (java.util.Date Type) in the current row
     * of the result set.
     */
    public java.util.Date getItemDateTimeValue(String columnName)
            throws SQLException {
        java.util.Date utilDate = null;
        java.sql.Timestamp utilTimestamp = getItemTimestampValue(columnName);

        if (utilTimestamp != null) {
            utilDate = new java.util.Date(utilTimestamp.getTime()
                    + utilTimestamp.getNanos() / 1000000);
        }

        return utilDate;
    }

    /**
     * Get the value of a given column (java.sql.Time Type) in the current row
     * of the result set.
     */
    public byte[] getItemBytesValue(String columnName) throws SQLException {
        return rs.getBytes(columnName);
    }

    /**
     * Get the value of a given column (java.io.InputStream Type) in the current
     * row of the result set.
     */
    public InputStream getItemInputStreamValue(String columnName)
            throws SQLException {
        return rs.getAsciiStream(columnName);
    }

    /**
     * Run the SQL select statment and make the result set ready for developer
     * to call getItemXXXXValue() to get value.
     *
     * @param sql the SQL query string to execute
     *
     * @Return true if record exists. Developer should call getNextDocument() to
     * fetch next record. Only one result set is expected
     */
    public boolean getFirstDocument(String sql)
            throws IllegalArgumentException, IllegalStateException,
            SQLException {
        return getFirstDocument(sql, null);
    }

    /**
     * Run the SQL select statment and make the result set ready for developer
     * to call getItemXXXXValue() to get value.
     *
     * @param sql the SQL query string to execute
     * @param pStmtValues the values(passed by sequence) to enter into the
     * prepared statement. If there are no values, use <code>null</code>
     *
     * e.g. Assumes the data type for COLUMN_A is VARCHAR, for COLUMN_B is
     * NUMBER. sql = "select COLUMN_A, COLUMN_B, COLUMN_C from TABLE where
     * COLUMN_A = ? and COLUMN_B = ?"; DbHelper.getFirstDocument(sql, new
     * Object[]{new String(COLUMN_A_VALUE), new Integer(COLUMN_B_VALUE)});
     *
     * @Return true if record exists. Developer should call getNextDocument() to
     * fetch next record. Only one result set is expected
     */
    public boolean getFirstDocument(String sql, Object[] pStmtValues)
            throws IllegalArgumentException, IllegalStateException,
            SQLException {
        boolean result = false;
        retrieve(sql, pStmtValues);
        result = getNextDocument();

        return result;
    }

    /**
     * Fetch next record and make the result set ready for developer to call
     * getItemXXXXValue() to get value. Return true if next record exists.
     * Developer should call getFirstDocument() firstly before calling this
     * function.
     */
    public boolean getNextDocument() throws SQLException {
        boolean withDoc = false;

        if (this.rs == null) {
            return withDoc;
        }
        withDoc = rs.next();
        return withDoc;
    }

    /**
     * Run stored procedure or update sql statement. The method is not used now,
     * it may need to remove later.
     *
     * @param sql the SQL query or update string to execute
     *
     * @Return true if the next result is a ResultSet.
     * @Return false if it is an update count or there are no more results.
     */
    public boolean runStoreProc(String sql) throws IllegalArgumentException,
            IllegalStateException, SQLException {
        boolean result = runStoreProc(sql, null);
        return result;
    }

    /**
     * Run stored procedure or update sql statement. The method is not used now,
     * it may need to remove later.
     *
     * @param sql the SQL query or update string to execute
     * @param pStmtValues the values(passed by sequence) to enter into the
     * prepared statement. If there are no values, use <code>null</code>
     *
     * @Return true if the next result is a ResultSet.
     * @Return false if it is an update count or there are no more results.
     */
    public boolean runStoreProc(String sql, Object[] pStmtValues)
            throws IllegalArgumentException, IllegalStateException,
            SQLException {
        boolean result = false;

        stmt = this.con.prepareStatement(sql);
        if (pStmtValues != null) {
            buildStatement(stmt, pStmtValues);
        }
        result = stmt.execute();

        return result;
    }

    /**
     * Executes an SQL INSERT, UPDATE or DELETE statement. In addition, SQL
     * statements that return nothing, such as SQL DDL statements, can be
     * executed.
     *
     * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL statement
     * that returns nothing
     *
     * @Returns either the row count for INSERT, UPDATE or DELETE or 0 for SQL
     * statements that return nothing
     */
    public int executeUpdate(String sql) throws IllegalArgumentException,
            IllegalStateException, SQLException {
        int result = executeUpdate(sql, null);
        return result;
    }

    /**
     * Executes an SQL INSERT, UPDATE or DELETE statement. In addition, SQL
     * statements that return nothing, such as SQL DDL statements, can be
     * executed.
     *
     * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL statement
     * that returns nothing
     * @param pStmtValues the values(passed by sequence) to enter into the
     * prepared statement. If there are no values, use <code>null</code>
     *
     * @Returns either the row count for INSERT, UPDATE or DELETE or 0 for SQL
     * statements that return nothing
     */
    public int executeUpdate(String sql, Object[] pStmtValues)
            throws IllegalArgumentException, IllegalStateException,
            SQLException {
        int result = -1;
        synchronized (con) {

            stmt = this.con.prepareStatement(sql);
            if (pStmtValues != null) {
                buildStatement(stmt, pStmtValues);
            }
            result = stmt.executeUpdate();
        }

        return result;
    }

    public void prepareStatement(String sql) throws IllegalArgumentException,
            IllegalStateException, SQLException {

        synchronized (con) {

            this.stmt = this.con.prepareStatement(sql);

        }
    }

    public void addBatch(Object[] pStmtValues) throws IllegalArgumentException,
            IllegalStateException, SQLException {

        synchronized (con) {

            if (pStmtValues != null) {
                buildStatement(this.stmt, pStmtValues);
                this.stmt.addBatch();
            }

        }

    }

    public int executeBatch() throws IllegalArgumentException,
            IllegalStateException, SQLException {
        int n = 0;
        synchronized (con) {

            int[] ns = this.stmt.executeBatch();
            if (ns != null) {
                for (int i = 0; i < ns.length; i++) {
                    n += ns[i];
                }
            }

        }
        return n;

    }

    /**
     * Run stored procedure or update sql statement.
     *
     * @param sql the SQL query or update string to execute
     *
     * get the first resultset to local varible 'rs' Return true if record is
     * ready for developer to access
     */
    public boolean getFirstDocumentSP(String sql)
            throws IllegalArgumentException, IllegalStateException,
            SQLException {
        boolean result = getFirstDocumentSP(sql, null);
        return result;
    }

    /**
     * Run stored procedure or update sql statement.
     *
     * @param sql the SQL query or update string to execute
     * @param pStmtValues the values(passed by sequence) to enter into the
     * prepared statement. If there are no values, use <code>null</code>
     *
     * get the first resultset to local varible 'rs' Return true if record is
     * ready for developer to access
     */
    public boolean getFirstDocumentSP(String sql, Object[] pStmtValues)
            throws IllegalArgumentException, IllegalStateException,
            SQLException {
        boolean result = false;

        stmt = this.con.prepareStatement(sql);
        if (pStmtValues != null) {
            buildStatement(stmt, pStmtValues);
        }
        stmt.execute();

        // get the first resultset
        while (true) {
            setResultSet(); //get next resultset
            if (isResultSet()) {
                break;
            }
            if (!getMoreResults() && getUpdateCount() == -1) {
                break;
            }
        }

        result = getNextDocument();

        return result;
    }

    public boolean getLastDocumentSP(String sql, Object[] pStmtValues, String columnNameNeeded)
            throws IllegalArgumentException, IllegalStateException,
            SQLException {
        boolean result = false;

        stmt = this.con.prepareStatement(sql);
        if (pStmtValues != null) {
            buildStatement(stmt, pStmtValues);
        }
        stmt.execute();

// get the first resultset
        while (true) {
            setResultSet(); //get next resultset	
            if (this.isNeedResultset(columnNameNeeded)) {
                break;
            }
            if (!this.getMoreResults() && this.getUpdateCount() == -1) {
                break;
            }

        }
        if (this.rs != null) {
            result = getNextDocument();
        }

        return result;
    }

    private boolean isNeedResultset(String columnNameNeeded) throws SQLException {
        if (this.rs == null) {
            return false;
        }
        int count = this.rs.getMetaData().getColumnCount();
        String columnName;
        for (int i = 1; i <= count; i++) {
            columnName = this.rs.getMetaData().getColumnName(i);
            System.out.println("columnName=" + columnName);
            if (columnName.equals(columnNameNeeded)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Close the connection, ResultSet and PreparedStatement.
     */
    public void closeConnection() throws SQLException {
        //        if(!this.getAutoCommit())
        //        this.setAutoCommit(true);
        if (this.con != null && this.con.isClosed() == false) {
            close();
            this.con.close();
        }

    }

    public void closeConnection(boolean isClosed) throws SQLException {
        //        if(!this.getAutoCommit())
        //        this.setAutoCommit(true);
        if (this.con != null && con.isClosed() == false) {
            close();
            this.con.close();

        }
    }

    /**
     * Close the ResultSet and PreparedStatement.
     */
    public void close() throws SQLException {
        if (this.rs != null) {
            this.rs.close();
            rs = null;
        }
        if (this.stmt != null) {
            this.stmt.close();
            this.stmt = null;
        }
    }

    /**
     * Moves to a Statement's next ResultSet.
     *
     * @return true if the next result is a ResultSet
     * @return false if it is an update count or there are no more results
     */
    public boolean getMoreResults() throws SQLException {
        return stmt.getMoreResults();
    }

    public boolean getMoreResults(int current) throws SQLException {
        return stmt.getMoreResults(current);
    }

    /**
     * Check whether the ResultSet is null or not.
     */
    public boolean isResultSet() {
        boolean result = false;
        if (this.rs != null) {
            result = true;
        }
        return result;
    }

    /**
     * Get the number of updated records.
     */
    public int getUpdateCount() throws SQLException {
        return this.stmt.getUpdateCount();
    }

    /**
     * Get the ResultSet.
     */
    public void setResultSet() throws SQLException {
        if (stmt != null) {
            this.rs = stmt.getResultSet();
        }
    }

    /**
     * Get the number of columns in the ResultSet.
     */
    public int getColumnCount() throws SQLException {
        ResultSetMetaData rsmd = this.rs.getMetaData();
        int numberOfColumns = rsmd.getColumnCount();

        return numberOfColumns;
    }

    /**
     * Get a column's type. The method is not used now, it may need to remove
     * later.
     */
    public int getColumnType(int col) throws SQLException {
        ResultSetMetaData rsmd = this.rs.getMetaData();
        int colType = rsmd.getColumnType(col);

        return colType;
    }

    /**
     * Get a column's name.
     */
    public String getColumnName(int col) throws SQLException {
        String colName = "";
        ResultSetMetaData rsmd = this.rs.getMetaData();
        colName = rsmd.getColumnName(col);

        return colName;
    }

    /**
     * Get the connection's auto-commit mode.
     */
    public boolean getAutoCommit() throws SQLException {
        boolean result = false;
        if (this.con != null) {
            result = this.con.getAutoCommit();
        }
        return result;
    }

    /**
     * Set the connection's auto-commit mode.
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if (!autoCommit) {
            if (!this.con.getAutoCommit()) {
                try {
                    this.con.commit();
                } catch (SQLException e) {
                    con.rollback();
                    throw e;
                } finally {
                    this.setAutoCommit(true);
                }
            }

        }
        if (this.con != null) {
            this.con.setAutoCommit(autoCommit);
        }
    }

    /**
     * Commit a transaction.
     */
    public void commitTran() throws SQLException {
        if (this.con != null) {
            this.con.commit();
        }
    }

    /**
     * Rollback a transaction.
     */
    public void rollbackTran() throws SQLException {
        if (this.con != null) {
            this.con.rollback();
        }
    }

    /**
     * Representing a null value in a prepared statement.
     */
    public static class NullSQLType {

        // The expected sql type of the field
        private int type;

        /**
         * Default constructor. Takes the expected sql type of the field that
         * will be null.
         *
         * @param fieldType the expected field type in the db. Should be pulled
         * from java.sql.Types.
         * @see java.sql.Types
         */
        public NullSQLType(int fieldType) {
            type = fieldType;
        }

        /**
         * Get the sql type of the field.
         *
         * @return the sql type of the field
         */
        public int getFieldType() {
            return type;
        }
    }

    /**
     * Execute the passed SQL statement and return a DOM object holding all the
     * query results
     *
     * @param sql - the SQL statement to execute
     * @param aElementName - a child node's name, its parent node is "Result"
     * @param rtnNull - indicate whether to return null or not if the query
     * result not found, true for yes
     * @return a DOM object or null
     */
    public Document getDOM(String sql, String aElementName, boolean rtnNull)
            throws ParserConfigurationException, DOMException, SQLException {
        Document document;
        document = getDOM(sql, null, aElementName, rtnNull);
        return document;
    }

    /**
     * Execute the passed SQL statement and return a DOM object holding all the
     * query results
     *
     * @param sql - the SQL statement to execute
     * @param pStmtValues - the values(passed by sequence) to enter into the
     * prepared statement. If there are no values, use <code>null</code>
     * @param aElementName - a child node's name, its parent node is "Result"
     * @param rtnNull - indicate whether to return null or not if the query
     * result not found, true for yes
     * @return a DOM object or null
     */
    public Document getDOM(String sql, Object[] pStmtValues,
            String aElementName, boolean rtnNull)
            throws ParserConfigurationException, DOMException, SQLException {
        Document document;
        int colCnt = 0;
        String colName = "";
        Object colValueObject;
        String colValue = "";
        boolean isResult;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();

        // Get ResultSet according to the passed SQL statement
        isResult = getFirstDocumentSP(sql, pStmtValues);

        // Set DOM to null if no ResultSet found and developer requires to return null
        if (isResult == false && rtnNull) {
            document = null;
        } else {
            // Create root element "Result"
            Element root = (Element) document.createElement("Result");
            document.appendChild(root);

            if (isResult) {
                colCnt = getColumnCount();

                do {
                    // Validate aElementName
                    if (aElementName != null) {
                        aElementName = aElementName.trim();
                    }
                    if (aElementName == null || aElementName == "") {
                        aElementName = "Row";
                    }

                    // Create a child element using the passed element name as TagName
                    // if the ElementName is null or empty then use "Row" as default
                    Element resultItem = document.createElement(aElementName);
                    root.appendChild(resultItem);

                    for (int i = 1; i <= colCnt; i++) {
                        // Get Column Name
                        colName = getColumnName(i);
                        // Get Column value as an object
                        colValueObject = this.rs.getObject(i);

                        // Return a string column value
                        if (colValueObject == null) {
                            colValue = "";
                        } else {
                            colValue = colValueObject.toString().trim();
                        }

                        // Create child node (column and value)
                        Element colItem = (Element) document
                                .createElement(colName);
                        resultItem.appendChild(colItem);
                        colItem.appendChild(document.createTextNode(colValue));
                    }

                } while (this.rs.next());

            }
        }

        return document;

    }

    /**
     * Check whether the connection is closed.
     *
     * @return boolean - true if connection is closed or is not initilized false
     * if connection is active
     *
     * @exception SQLException
     */
    public boolean isConClosed() throws SQLException {
        boolean result = true;
        if (this.con != null) {
            result = this.con.isClosed();
        }
        return result;
    }

    /**
     * Excute select SQL statement. Returns one resultset
     *
     * @param sql the SQL query string to execute
     * @param pStmtValues the values(passed by sequence) to enter into the
     * prepared statement. If there are no values, use <code>null</code>
     */
    private void retrieve(String sql, Object[] pStmtValues)
            throws IllegalArgumentException, IllegalStateException,
            SQLException {
        stmt = con.prepareStatement(sql);
        if (pStmtValues != null) {
            buildStatement(stmt, pStmtValues);
        }
        this.rs = stmt.executeQuery();

    }

    /**
     * Build prepared statements using only a connection and an array of
     * objects.
     */
    private void buildStatement(PreparedStatement stmt, Object[] values)
            throws IllegalArgumentException, IllegalStateException,
            SQLException {

        //If we have a null value here, then bail.
        if (values == null || stmt == null) {
            throw new IllegalArgumentException(
                    "Cannot pass a null value array into buildStatement().");
        }

        //Loop through each value, determine it's corresponding SQL type,
        //and stuff that value into the prepared statement.
        Object value = null;
        for (int i = 0; i < values.length; i++) {
            value = values[i];

            //Have to handle null values seperately
            if (value != null) {

                //If the object is our representation of a null value, then handle it seperately
                if (value instanceof NullSQLType) {
                    stmt.setNull(i + 1, ((NullSQLType) value).getFieldType());
                } else {
                    if (value.getClass().getName().equals("[B")) {
                        byte[] byteArray = (byte[]) value;
                        stmt.setBytes(i + 1, byteArray);
                    } else {

                        if (value.getClass().getName().equals(
                                "java.lang.Integer")) {
                            stmt.setInt(i + 1, ((Integer) value).intValue());
                            continue;
                        }
                        if (value.getClass().getName().equals("java.lang.Long")) {
                            stmt.setLong(i + 1, ((Long) value).longValue());
                            continue;
                        }

                        stmt.setObject(i + 1, value);
                    }
                }
            } else {
                //Can't do anything with a null value.
                throw new IllegalStateException(
                        "Can't use a null value in a prepared statement.");
            }
        }

    }

    //存储过程调用　张卫清
    public CallableStatement getCallabelStatement(String produceName)
            throws Exception {
        CallableStatement stmt = this.con.prepareCall(produceName);
        return stmt;
    }
}
