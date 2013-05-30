package code.husky;

import java.sql.Connection;

public abstract class Database {
	
  protected boolean connected;
  protected Connection connection;
  public int lastUpdate;

  public Database() {
    this.connected = false;
    this.connection = null;
  }

  protected Statements getStatement(String query) {
    String trimmedQuery = query.trim();
    Statements[] allStatements = Statements.values();
    for(Statements statement : allStatements) {
        if(trimmedQuery.toUpperCase().startsWith(statement.name()))
            return statement;
    }
    return Statements.SELECT;
  }

  protected static enum Statements {
    SELECT, INSERT, UPDATE, DELETE, DO, REPLACE, LOAD, HANDLER, CALL, 
    CREATE, ALTER, DROP, TRUNCATE, RENAME, START, COMMIT, ROLLBACK, 
    SAVEPOINT, LOCK, UNLOCK, PREPARE, EXECUTE, DEALLOCATE, SET, SHOW, 
    DESCRIBE, EXPLAIN, HELP, USE, ANALYZE, ATTACH, BEGIN, DETACH, 
    END, INDEXED, ON, PRAGMA, REINDEX, RELEASE, VACUUM;
  }
}