package business.biz.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import business.biz.dto.MainClusterResponseDto;
import business.biz.dto.MainResponseDto;
import business.biz.model.Member;
import common.base.BaseController;


/**
 * @author jogyeongmin
 * 설명 : 메인 화면 컨트롤러 
 *
 */
@EnableWebMvc
@Controller
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class MainController extends BaseController {


    @Autowired
    private MainService  mainService;

	@Autowired
	private HttpSession session;
	
	
	/**
	 * 2023-05-26 조경민
	 * 설명 : 메인화면 불러오기
	 */
	@GetMapping("/main")
	public String main(Model model) {
		
		// 현재 로그인 된 상태인지 확인
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		if(loginMember == null) {
			return "redirect:login.do";
		}
		
		// 레이어 목록 불러오기 
		List<MainResponseDto> layerMenu = mainService.getLayerMenu();
		
		// 메인 리스트 불러와서 모델에 저장 
		model.addAttribute("list", layerMenu);
		
		
		//메인화면 호출 
		return "/gis/main";
	}
	
	
	
	/**
	 * 2023-05-29 조경민
	 * 설명 : 클러스터를 위한 데이터 불러오기
	 * (2023-05-30 조경민 : wfs cors가 허용되어 사용하지 않음)
	 */
	@GetMapping("/main/cluster-coordinates")
	@ResponseBody
	public List<MainClusterResponseDto> getClusterCoordinates(){
		List<MainClusterResponseDto> coordinatesList = new ArrayList<>();
		
		// 클러스터 데이터를 담아 리스트로 저장
		coordinatesList = mainService.getCoordinatesList();
		
		// 응답 바디에 담아 전달
		return coordinatesList;
	}
	
	
	@GetMapping("/fmain")
	public String fmain(Model model) {
		// 레이어 목록 불러오기 
		List<MainResponseDto> layerMenu = mainService.getLayerMenu();
		
		// 메인 리스트 불러와서 모델에 저장 
		model.addAttribute("list", layerMenu);
		
		
		//메인화면 호출 
		return "/gis/first-main";
	}
	
	
	
}
