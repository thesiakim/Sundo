package business.biz.templete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import commf.dao.CommonDAOImpl;
import common.base.BaseService;


@Service
@SuppressWarnings({"rawtypes", "unchecked"})
public class SampleService extends BaseService {
	

	/** 로그 객체 */
	public static final Logger LOGGER = Logger.getLogger(SampleService.class);

    @Autowired
    private CommonDAOImpl dao;

   
	public List GetPriorityUserData(){
		return dao.list("Sample.getPriorityUserData");
	}
	  
	
	public int UpdateAutorityUp(HashMap tempM) {
		return dao.update("Sample.updateAutorityUp",tempM);
	}

	public int UpdateAutorityDown(HashMap tempM) {
		return dao.update("Sample.updateAutorityDown",tempM);
	}

//	
//	public List GetMenuList() {
//		return dao.list("MenuAuth.getMenuList");
//	}
//
//	public List GetAllAgentList() {
//		return dao.list("MenuAuth.getAllAgentList");
//	}

	public List GetRD_AuthMenuList() {
		return dao.list("MenuAuth.getRD_AuthMenuList");
	}
	
	
	// 기관 리스트 저장시 중복 체크
	public List GetDuplAgentActiveList(HashMap parmM) {
		return dao.list("MenuAuth.getDuplAgentActiveList", parmM);
	}
	
	// 기관 리스트 저장
	public List InsertAgentActiveList(HashMap parmM) {
		return dao.list("MenuAuth.insertAgentActiveList", parmM);
	}
	
	// 기관 리스트 삭제
	public int DeleteAgentActiveList(HashMap parmM) {
		return dao.delete("MenuAuth.deleteAgentActiveList", parmM);
	}
	

	// 기관 리스트 불러오기
	public List GetAgentActiveList(HashMap userInfo) {
		return dao.list("MenuAuth.getAgentActiveList", userInfo);
	}
	
	
	
	

	
	
	
    
	  

}