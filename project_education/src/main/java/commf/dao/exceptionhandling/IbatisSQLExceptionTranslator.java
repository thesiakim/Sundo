package commf.dao.exceptionhandling;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

public class IbatisSQLExceptionTranslator extends SQLErrorCodeSQLExceptionTranslator {

	@Override
	protected DataAccessException customTranslate(String task, String sql, SQLException sqlEx) {

		if (sqlEx.getSQLState() == null && sqlEx.getErrorCode() == 0) {
			if ("Error: view returned too many results.".equals(sqlEx.getMessage())) {
				throw new IncorrectResultSizeDataAccessException(sqlEx.getMessage(), 1);
			}
		}

		return null;
	}
}
