package commf.exception;

import java.sql.SQLException;

import com.ibatis.common.jdbc.exception.NestedSQLException;

@SuppressWarnings("all")
public class IBatisException extends NestedRuntimeException {

	private String errorMessage;
	private int errorCode;

	public IBatisException(Throwable cause){
		super(cause);
		init(cause);
	}

    public IBatisException(String message) {
        super(message);
    }

    public IBatisException(String message, Throwable cause) {
        super(message, cause);
    }

    private void init(Throwable cause) {
		if(cause instanceof org.springframework.dao.DataIntegrityViolationException) {
			if(cause.getCause() != null && cause.getCause() instanceof NestedSQLException) {

				NestedSQLException ne = (NestedSQLException)cause.getCause();

				this.errorMessage = ne.getMessage();

				if(ne.getCause() != null && ne.getCause() instanceof SQLException) {
					SQLException se = (SQLException)ne.getCause();
					if(se != null) {
						this.initCause(se);
						this.errorMessage = se.getMessage();
						this.errorCode = se.getErrorCode();
					}
				} else {
					this.initCause(ne);
				}
			}
		}
    }
	public int getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
}
