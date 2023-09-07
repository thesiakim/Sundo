package common.base;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import commf.message.Message;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @author ntarget
 * Create date 		: 2017-01-09
 *
 */

@SuppressWarnings("all")
public class BaseService extends EgovAbstractServiceImpl  {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public BaseService() {}

}
