package commf.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import egovframework.rte.fdl.cmmn.exception.handler.ExceptionHandler;

public class CommonExceptionHandler implements ExceptionHandler {
    protected Log log = LogFactory.getLog(this.getClass());

    /**
     * 발생된 Exception을 처리한다.
     */
    public void occur(Exception ex, String packageName) {
    	log.debug("Default Exeption Handler run...............");

    	// TODO Exception 처리 추가
    	ex.printStackTrace();
    }
}
