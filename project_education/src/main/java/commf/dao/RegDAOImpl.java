package commf.dao;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class RegDAOImpl extends IBatisMappingDAO {

	/**
	* kml 파일 저장
	* @param query
	* @return 결과
	*/
	public boolean insertKmlFile(Map<String, Object> query){
		boolean bool = false;
		try{
			insert("usrRegLayer.insertKmlFile", query);
			bool = true;
		}catch(Exception e){
			bool = false;
		}
		return bool;
	}

	/**
	* shape파일 저장
	* @param query
	* @return 결과
	*/
	public boolean insertShpeFile(Map<String, Object> query){
		boolean bool = false;
		try{
			insert("usrRegLayer.insertShpeFile", query);
			bool = true;
		}catch(Exception e){
			bool = false;
		}
		return bool;
	}

	/**
	* shape파일 저장
	* @param query
	* @return 결과
	*/
	public boolean insertMaptilerFile(Map<String, Object> query){
		boolean bool = false;
		try{
			insert("usrRegLayer.insertMaptilerFile", query);
			bool = true;
		}catch(Exception e){
			bool = false;
		}
		return bool;
	}

	/**
    * shape파일 저장
    * @param query
    * @return 결과
    */
    public boolean insertImageFile(Map<String, Object> query){
        boolean bool = false;
        try{
            insert("usrRegLayer.insertImageFile", query);
            bool = true;
        }catch(Exception e){
            bool = false;
        }
        return bool;
    }

	/**
	* user layer목록 조회
	* @param query
	* @return 목록
	*/
	public List<Map<String, Object>> selectListUserLayer(Map<String, Object> query){
		return (List<Map<String, Object>>) list("usrRegLayer.selectListUserLayer", query);
	}

	/**
	* user layer share목록 조회
	* @param query
	* @return 목록
	*/
	public List<Map<String, Object>> selectListOtherLayer(Map<String, Object> paramMap){
		return (List<Map<String, Object>>) list("usrRegLayer.selectListOtherLayer", paramMap);
	}


	/**
	* user layer 총 개수
	* @param query
	* @return 개수
	*/
	public int selectUserLayerCount(Map<String, Object> query){
		return (Integer)select("usrRegLayer.selectUserLayerCount", query);
	}

	/**
	* user layer 총 개수
	* @param query
	* @return 개수
	*/
	public int selectOtherLayerCount(Map<String, Object> query){
		return (Integer)select("usrRegLayer.selectUserOtherCount", query);
	}

	/**
	* user layer 삭제
	* @param layer_sn
	* @return 결과
	*/
	public boolean deleteUserLayer(int layerSn){
		boolean bool = false;
		try{
			delete("usrRegLayer.deleteUserLayer", layerSn);
			bool = true;
		}catch(Exception e){
			bool = false;
		}
		return bool;
	}

	public void deleteUserLayerShareByUserLayer(int layer_sn){
		update("usrRegLayer.deleteUserLayerShareByUserLayer", layer_sn);
	}

	/**
	* kmlUrl 저장
	* @param query
	* @return 없음
	*/
	public void insertKmlUrl(Map<String, Object> query){
		insert("usrRegLayer.insertKmlUrl", query);
	}

	/**
	 * 공유 추가
	 * @param query
	 */
	public void insertUserLayerShare(Map<String, Object> query) {
		insert("usrRegLayer.insertUserLayerShare", query);

	}

	/**
	 * 사용자별 이름, 기관 알아오는
	 * @param UserIDs
	 * @return
	 */
	public List<Map<String, Object>> selectUserInfo(Map<String, Object> paramMap){
		return (List<Map<String, Object>>)list("usrRegLayer.selectUserInfo", paramMap);
	}

	/**
	 * 공유 정보 조회
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> selectUserLayerShare(Map<String, Object> paramMap){
		return (List<Map<String, Object>>)list("usrRegLayer.selectUserLayerShare", paramMap);
	}


	/**
	* user layer 삭제
	* @param layer_sn
	* @return 결과
	*/
	public boolean disconnectShareLayer(Map<String, Object> paramMap){
		boolean bool = false;
		try{
			System.out.println(paramMap.get("layer_sn"));
			System.out.println(paramMap.get("user_id"));
			update("usrRegLayer.disconnectShareLayer", paramMap);
			bool = true;
		}catch(Exception e){
			bool = false;
		}
		return bool;
	}

	/**
	* 도형 삽입
	* @param userId, contents, geom, featureType
	* @return
	*/
	public void insertUsrSpatial(Map<String, Object> paramMap){
		insert("usrRegLayer.insertUsrSpatial",paramMap);
	}

	/**
	* 권한 부여하기
	* @param figureId, authorizer, authorizeduser, authorityLevel
	* @return
	*/
	public void insertUsrSpatialAuth(Map<String, Object> paramMap){
		insert("usrRegLayer.insertUsrSpatialAuth",paramMap);
	}

	/**
	* <!-- 권한 부여 받은 여부 확인하기 위함 -->
	* @param figureId, authorizedUser
	* @return
	*/

	public int isAuthorized(Map<String, Object> paramMap){

		if(select("usrRegLayer.isAuthorized",paramMap) != null){
			return (Integer) select("usrRegLayer.isAuthorized",paramMap);
		}else{
			return 0;
		}
	}

	/**
	* whos에 따라 Figures 정보 가져오기
	* @param authorizedUser, who's
	* @return
	*/
	public List<Map<String, Object>> selectUsrSpatial(Map<String, Object> paramMap){
		return (List<Map<String, Object>>) list("usrRegLayer.selectUsrSpatial",paramMap);
	}

	public int selectUsrSpatialCnt(Map<String, Object> paramMap) {
		return (Integer) select("usrRegLayer.selectUsrSpatialCnt",paramMap);
	}

	/**
	* 도형 조회
	* @param figureId
	* @return
	*/
	public Map<String, Object> selectOneFigure(Map<String, Object> paramMap){
		return (Map<String, Object>) select("usrRegLayer.selectOneFigure", paramMap);
	}

	/**
	* FigureId 별 Attached 가져오기
	* @param figureId
	* @return
	*/
	public List<Map<String, Object>> selectAttachedByFigureId(int figureId){
		return (List<Map<String, Object>>) list("usrRegLayer.selectAttachedByFigureId",figureId);
	}

	/**
	* userId로 도형 조회
	* @param userId
	* @return
	*/
	public List<Map<String, Object>> selectFiguresByUserId(String userId){
		return (List<Map<String, Object>>)list("route_figure.selectFiguresByUserId", userId);
	}

	/**
	* 도형 삭제
	* @param figureId
	* @return
	*/
	public void deleteFigureOne(int figureId){
		delete("usrRegLayer.deleteFigureOne", figureId);
	}
	/**
	* 유저 삭제 시 도형 삭제
	* @param userId
	* @return
	*/
	public void deleteFigureByUser(String userId){
		delete("usrRegLayer.deleteFigureByUser", userId);
	}

	/**
	* 첨부파일 삭제
	* @param attachedId
	* @return
	*/
	public void deleteAttachedOne(int attachedId){
		delete("usrRegLayer.deleteAttachedOne",attachedId);
	}

	/**
	* 도형 삭제 시 첨부파일 삭제
	* @param figureId
	* @return
	*/
	public void deleteAttachedByFigureId(int figureId){
		delete("usrRegLayer.deleteAttachedByFigureId",figureId);
	}

	/**
	* 권한 삭제
	* @param figureId, authorizedUser
	* @return
	*/
	public void deleteAuthorityOne(Map<String, Object> paramMap){
		delete("usrRegLayer.deleteAuthorityOne",paramMap);
	}

	/**
	* 도형 삭제 시 권한 삭제
	* @param figureId
	* @return
	*/
	public void deleteAuthorityByFigureId(int figureId){
		delete("usrRegLayer.deleteAuthorityByFigureId",figureId);
	}

	/**
	* FigureId 별 볼 수 있는 권한을 userId, 권한 레벨
	* @param figureId
	* @return
	*/
	public List<Map<String, Object>> selectAuthorizedUserByFigureId(int figureId){
		return (List<Map<String, Object>>) list("usrRegLayer.selectAuthorizedUserByFigureId",figureId);
	}

	/**
	* <!-- 권한 부여 받은 리스트 가져오기 -->
	* @param figureId
	* @return
	*/
	public List<String> getAuthorizedUser(int figureId){
		return (List<String>) list("usrRegLayer.getAuthorizedUser",figureId);
	}

	/**
	* 권한 수정
	* @param authorityLevel, authorizedUser, figureId
	* @return
	*/
	public void updateUsrSpatialAuth(Map<String, Object> paramMap){
		update("usrRegLayer.updateRouteAuthority",paramMap);
	}

	/**
	* 도형 삽입
	* @param userId, contents, geom, featureType
	* @return
	*/
	public void insertRouteFigure(Map<String, Object> paramMap){
		insert("usrRegLayer.insertRouteFigure",paramMap);
	}

	/**
	* 첨부파일 추가하기 삽입
	* @param fileName, originName, type, size, figureId
	* @return
	*/
	public void insertRouteAttached(Map<String, Object> paramMap){
		insert("usrRegLayer.insertRouteAttached",paramMap);
	}
	/**
	* 권한 부여하기
	* @param figureId, authorizer, authorizeduser, authorityLevel
	* @return
	*/
	public void insertRouteAuthority(Map<String, Object> paramMap){
		insert("usrRegLayer.insertRouteAuthority",paramMap);
	}

	/**
	*  [2019_1004 dwlee] 도형 수정
	* @param draw_idx, geom, title
	* @return
	*/
	public void updateRouteFigure(Map<String, Object> paramMap){
		update("usrRegLayer.updateRouteFigure",paramMap);
	}

	/**
	* 공간 정보 수정
	* @param geom, figureId, type, option
	* @return
	*/
	public void updateRouteGeom(Map<String, Object> paramMap){
		update("usrRegLayer.updateRouteGeom",paramMap);
	}

	/**
	* checked 수정
	* @param routeChecked, figureId
	* @return
	*/
	public void updateRouteChecked(Map<String, Object> paramMap){
		update("usrRegLayer.updateRouteChecked",paramMap);
	}

	/**
	* 권한 수정
	* @param authorityLevel, authorizedUser, figureId
	* @return
	*/
	public void updateRouteAuthority(Map<String, Object> paramMap){
		update("usrRegLayer.updateRouteAuthority",paramMap);
	}

	/**
	* 권한 Read True로
	* @param authorizedUser, figureId
	* @return
	*/

	public void updateRouteAuthorityRead(Map<String, Object> paramMap){
		update("usrRegLayer.updateRouteAuthorityRead",paramMap);
	}


	public List<Map<String, Object>> selectListUserSelector(Map<String, Object> paramMap) {
		return (List<Map<String, Object>>)list("usrRegLayer.selectListUserSelector", paramMap);
	}

	public Integer selectCountUserSelector(Map<String, Object> paramMap) {
		return (Integer)select("usrRegLayer.selectCountUserSelector", paramMap);
	}
}
