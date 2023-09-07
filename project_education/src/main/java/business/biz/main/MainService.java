package business.biz.main;

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

import business.biz.dto.MainClusterResponseDto;
import business.biz.dto.MainResponseDto;
import commf.dao.CommonDAOImpl;
import common.base.BaseService;


/**
 * @author jogyeongmin
 *	설명 : 메인회면 관련 서비스 
 */
@Service
@SuppressWarnings({"rawtypes", "unchecked"})
public class MainService extends BaseService {
	

	/** 로그 객체 */
	public static final Logger LOGGER = Logger.getLogger(MainService.class.getName());

    @Autowired
    private CommonDAOImpl dao;
    
    
    
	/**
	 * 2023-05-26 조경민
	 * 설명 : 계층별 레이어 목록 불러오기
	 */
	public List<MainResponseDto> getLayerMenu() {
		List<MainResponseDto> layerMenu;
		
		try {
			
			layerMenu = (List<MainResponseDto>) dao.list("Main.findLayerMenu");
			
		}catch(Exception e) {
			
			System.out.println(e.getMessage());
			throw new IllegalArgumentException("레이어 불러오기를 실패하였습니다.");
			
		}
		 
		return layerMenu;
	}

	
	/**
	 * 2023-05-29 조경민
	 * 설명 : 클러스터 기능을 위한 데이터 불러오기
	 */
	public List<MainClusterResponseDto> getCoordinatesList() {
		List<MainClusterResponseDto> coordinatesList;
		
		try {
			coordinatesList = (List<MainClusterResponseDto>) dao.list("Main.findClusterCoordinatesList");
			
		}catch (Exception e) {
			
			System.out.println(e.getMessage());
			throw new IllegalArgumentException("클러스터 좌표 불러오기를 실패하였습니다."); 
		}
		
		
		return coordinatesList;
	}

	

}