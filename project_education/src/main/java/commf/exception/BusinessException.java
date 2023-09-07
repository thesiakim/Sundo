package commf.exception;


//import org.apache.commons.scaffold.lang.BaseException;
@SuppressWarnings("all")
public class BusinessException extends NestedRuntimeException {
	public BusinessException(Throwable cause){
		super(cause);
	}

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
