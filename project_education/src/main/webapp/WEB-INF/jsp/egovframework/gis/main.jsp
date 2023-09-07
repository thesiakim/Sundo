<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ko">

<jsp:include page="/WEB-INF/jsp/egovframework/include/gis_header.jsp"/>

<head>
   <link type="text/css" href="style/gis.css" rel="stylesheet">
   <link type="text/css" href="style/main.css" rel="stylesheet">
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
   <script defer src="script/main/main.js"></script>
</head>
   <script type="module" src="main.js"></script>
   
    <!-- OpenLayers 라이브러리 스타일시트 -->
    <link rel="stylesheet" href="https://openlayers.org/en/v4.6.4/css/ol.css" type="text/css">
    
    <!-- OpenLayers 라이브러리 자바스크립트 파일 -->
    <script src="https://openlayers.org/en/v4.6.4/build/ol.js"></script>
<body>

   <section class="gis">

      <div class="gis-wrap">
         
         <div class="gis-inner">
            <button class="gis-inner-btn"><img src="images/gis/i-menuBtn.svg" alt="" /></button>
            <div class="gis-search">
               
               <!-- 리스트 -->
               <div class="gis-list depItem">
              <div class="gis-list-tit" style="position: relative;">
                 <img src="images/gis/i-search.svg" style="position: absolute; right: 91%; top: 40%; transform: translateY(-50%);">
                 <p style="display: inline-block;">검색</p>
                 <input id="search-input" type="text" style="display: inline-block; margin-left: 10px;margin-top: -3px;">
              </div>
              
                  
                  <div>
                     <form>
                           <label id="clusterDistance">cluster distance</label>
                         <input id="distance" type="range" min="0" max="100" step="2" value="30"/>
                          <div class="separator"></div> <!-- 구분선 -->
                      </form>
                  </div>
                    <div class="gis-list-table">
                     <table id="clusterCategory">
                        <tr>
                           <td id="category-all">DB</td>
                           <td id="category-selected">선택 레이어</td>
                        </tr>    
                     </table>
                           <div><button type="submit" id="category-reset">초기화</button></div>
                     <div class="scrollable-div">
                        <ul id="foldable-list">
                           <li>해역이용영향평가GIS레이어
                              <ul>
                                 <c:forEach var="list1" items="${list}">
                                    <c:if test="${list1.level == 0}">
                                       <c:choose>
                                          <c:when test="${list1.child_yn}">
                                             <li>${list1.name}
                                                <ul>
                                                   <c:forEach var="list2" items="${list}">
                                                      <c:if test="${list2.parent_id == list1.id && list2.level == list1.level + 1}">
                                                         <c:choose>
                                                            <c:when test="${list2.child_yn}">
                                                               <li>${list2.name}
                                                                  <ul>
                                                                     <c:forEach var="list3" items="${list}">
                                                                        <c:if test="${list3.parent_id == list2.id && list3.level == list2.level + 1}">
                                                                           <c:choose>
                                                                              <c:when test="${list3.child_yn}">
                                                                                 <li>${list3.name}
                                                                                    <ul>
                                                                                       <c:forEach var="list4" items="${list}">
                                                                                          <c:if test="${list4.parent_id == list3.id && list4.level == list3.level + 1}">
                                                                                             <c:choose>
                                                                                                <c:when test="${list4.child_yn}">
                                                                                                   <li>${list4.name}</li>
                                                                                                </c:when>
                                                                                                <c:otherwise>
                                                                                                     <input type="checkbox" class="checkLayer" id="checkLayer${list4.id}">
                                                                              ${list4.name}<br>
                                                                                                </c:otherwise>
                                                                                             </c:choose>
                                                                                          </c:if>
                                                                                       </c:forEach>
                                                                                    </ul>
                                                                                 </li>
                                                                              </c:when>
                                                                              <c:otherwise>
                                                                                 <input type="checkbox" class="checkLayer" id="checkLayer${list3.id}">${list3.name}<br> 
                                                                              </c:otherwise>
                                                                               </c:choose>
                                                                        </c:if>
                                                                     </c:forEach>
                                                                  </ul>
                                                               </li>
                                                            </c:when>
                                                            <c:otherwise>
                                                               <input type="checkbox" class="checkLayer" id="checkLayer${list2.id}">${list2.name}<br>
                                                            </c:otherwise>
                                                         </c:choose>
                                                      </c:if>
                                                   </c:forEach>
                                                </ul>
                                             </li>
                                          </c:when>
                                          <c:otherwise>
                                             <input type="checkbox" class="checkLayer" id="checkLayer${list1.id}">${list1.name}<br> 
                                          </c:otherwise>
                                       </c:choose>
                                    </c:if>
                                 </c:forEach>
                              </ul>
                           </li>
                        </ul>
                        <script defer src="script/main/search.js"></script>
                     </div>   
                  </div>
               </div>
            </div>
         </div>


        <div class="gis-map" id="gis_map">
         <div class="gis-map-btn">
           <ul class="gis-map-btn-option">
            <li class="map">
              <a href="#void" style="display: flex; align-items: center;" onclick="changeMapImage()">
               <img src="images/gis/i-map.svg" id="mapImg" style="width: 20px; margin-right: 5px; margin-bottom: 48px; margin-left: 15px;">
               <span style="margin-left: -32px; margin-bottom: 10px;">배경지도</span>
              </a>
              <div class="info">
               <div class="info-find">
                 <a id="vworld-base" class="on" href="#void"><img src="images/basemap/vworld-base-thumb.png" alt="" />일반지도</a>
                 <a id="vworld-gray" class="" href="#void"><img src="images/basemap/vworld-gray-thumb.png" alt="" />회색지도</a>
                 <a id="vworld-satbrid" class="" href="#void"><img src="images/basemap/vworld-satbrid-thumb.png" alt="" />위성지도</a>
                 <a id="vworld-sathybrid" class="" href="#void"><img src="images/basemap/vworld-hybrid-thumb.PNG" alt="" />하이브리드</a>
               </div>
              </div>
            </li>
                  
            <li class="layer">
              <a href="#void" style="display: flex; align-items: center;" onclick="changeLayerImage()">
               <img src="images/gis/i-layer.svg" id="layerImg" style="width: 23px; margin-right: 5px; margin-bottom: 48px; margin-left: 14px;">
               <span style="margin-left: -30px; margin-bottom: 10px;">레이어</span>
              </a>
              <div class="info">
               <h4>레이어<button type="button" class="btn-close"></button></h4>
               <div class="info-inner">
                 <div class="inputWrap">
                  <input class="layer" type="checkbox" id="fishery_management">
                  <label for="fishery_management">면허어장도</label>
                 </div>
                 <div class="inputWrap">
                  <input class="layer" type="checkbox" id="fishery_observatory">
                  <label for="fishery_observatory">실시간 해양환경 관측소</label>
                 </div>
                 <div class="inputWrap">
                  <input class="layer" type="checkbox" id="proteted_area">
                  <label for="proteted_area">보호구역</label>
                 </div>
                 <div class="inputWrap">
                  <input class="layer" type="checkbox" id="ctp_rvb">
                  <label for="ctp_rvb">시,도 구분</label>
                 </div>
               </div>
              </div>
            </li>
                  
            <li class="legend">
              <a href="#void" style="display: flex; align-items: center;"  onclick="changeLegendImage()">
               <img src="images/gis/i-legend.svg" id="legendImg"  style="width: 19px; margin-right: 5px; margin-bottom: 48px; margin-left: 16px;">
                <span style="margin-left: -24px; margin-bottom: 10px;">범례</span>
              </a>
              <div class="info">
               <h4>범례<button type="button" class="btn-close"></button></h4>
                <div class="info-inner">
                 <ul>
                  <li>
                    <img src="images/icon/nifs.png" style="width: 20px; margin-right: 10px;"> 국립수산과학원
                  </li>
                  <li>
                    <img src="images/icon/kma.png" style="width: 20px; margin-right: 10px;"> 기상청
                  </li>
                  <li>
                    <img src="images/icon/khnp.png" style="width: 20px; margin-right: 10px;"> 한국수력원자력
                  </li>
                  <li>
                    <img src="images/icon/government.png" style="width: 20px; margin-right: 10px;"> 지자체
                  </li>
                  <li>
                    <img src="images/icon/inspect.png" style="width: 20px; margin-right: 10px;"> 점검중
                  </li>
                 </ul>
               </div>
              </div>
            </li>
                  
            <li class="draw">
              <a href="#void" style="display: flex; align-items: center;" onclick="changePenImage()">
               <img src="images/gis/pen.png" id="penImg"  style="width: 17px; margin-right: 5px; margin-bottom: 48px; margin-left: 18px;">
                <span style="margin-left: -28px; margin-bottom: 10px;">그리기</span>
              </a>
              <div class="info">
               <h4>그리기<button type="button" class="btn-close"></button></h4>
               <form>
                 <select id="type">
                  <option value="LineString">&nbsp;&nbsp;&nbsp;&nbsp;라인스트링&nbsp;&nbsp;&nbsp;&nbsp;</option> 
                  <option value="Polygon">&nbsp;&nbsp;&nbsp;&nbsp;폴리곤&nbsp;&nbsp;&nbsp;&nbsp;</option>
                 </select>
                 <button class="button" onclick="resetMap()">초기화</button>
                 <button class="button" onclick="removeInteraction()">&nbsp;종료&nbsp;</button>
               </form>
              </div>
            </li>
           </ul>
         <ul class="gis-map-btn-click">
           <li class="plus" id="zoom-in">
            <a href="#void" style="display: flex; justify-content: center; align-items: center;">
              <img src="images/gis/i-plus.svg" style="width: 20px;">
            </a>
           </li>
           <li class="minus" id="zoom-out">
            <a href="#void" style="display: flex; justify-content: center; align-items: center;">
              <img src="images/gis/i-minus.svg" style="width: 20px;">
            </a>
           </li>
         </ul>
        </div>
      </div>
     </div>
   </section>
      
      
   <!-- 실시간 해양환경 관측소 마우스 오버 팝업창 -->
   <div id="real_time_inform" class="ol-new-popup">
     <div id="mapLayer_head" class="ol-new-popup-head">
      <h1 class="overlay-title" id="observatory_nm">
        통영 학림
        <span class="date">2022-10-04 14:30</span>
       </h1>
     </div>
     <div id="mapLayer_content" class="tableWrap type3">
      <table id="observatory_inform">
        <tr>
         <th>표층수온</th>
         <th>중층수온</th>
         <th>저층수온</th>
        </tr>
        <tr>
         <td id="surface_class">23.2℃</td>
         <td id="middle_class">미설치</td>
         <td id="low_class">미설치</td>
        </tr>
        <tr>
         <th>기온</th>
         <td colspan="2" id="temp">미설치</td>
        </tr>
        <tr>
         <th>염분</th>
         <td colspan="2" id="salt">32.11</td>
        </tr>
        <tr>
         <th>용존산소</th>
         <td colspan="2" id="oxygen">7.04mg/L</td>
        </tr>
      </table>
     </div>
   </div>

      
   <!-- 면허어장도 마우스 오버 팝업창 -->
   <div id="fishing_ground_inform" class="ol-new-popup">
     <div id="mapLayer_head" class="ol-new-popup-head">
      <h1 class="overlay-title" id="license_nu">양식장면허심사평가</h1>
     </div>
     <div id="mapLayer_content" class="tableWrap type3">
      <table id="observatory_inform">
        <tr>
         <th>시도</th>
         <td id="sido_name"></td>
         <th>시군구</th>
         <td id="sigungu_name"></td>
         <th>어업시기</th>
         <td id="fishery_season"></td>
        </tr>
        <tr>
         <th>면허번호</th>
         <td id="license_no"></td>
         <th>어장번호</th>
         <td id="fishery_nu"></td>
         <th>어장면적</th>
         <td id="fishery_space"></td>
        </tr>
        <tr>
         <th>면허허가구분</th>
         <td id="license_sort"></td>
         <th>면허허가일자</th>
         <td id="license_date"></td>
         <th>양식방법</th>
         <td id="fishery_method_m"></td>
        </tr>
        <tr>
         <th>면허기간</th>
         <td colspan="3" id="license_period"></td>
         <th>양식업종류</th>
         <td id="fishery_method_l"></td>
        </tr>
        <tr>
         <th>유예기간</th>
         <td colspan="3" id="post_period"></td>
         <th>양식업구분</th>
         <td id="fishery_type"></td>
        </tr>
        <tr>
         <th>수면위치</th>
         <td colspan="3" id="surface_loc"></td>
         <th>어업생산품</th>
         <td id="fishery_product"></td>
        </tr>
      </table>
     </div>
   </div>
         
         
   <div id="custom-modal" class="custom-modal">
     <div class="custom-modal-dialog">
      <div class="custom-modal-content">
        <div class="custom-modal-header">
         <button type="button" class="btn-close" onclick="closeCustomModal()" data-bs-dismiss="modal" aria-label="Close"></button>
          <p class="custom-modal-title" id="customModalLabel" style="font-size: 14px; font-weight: normal; color: #666666;">결과보고서</p><hr>
        </div>
        <div class="custom-modal-body">
         <h2 class="custom-table-title" style="font-size: 12px; color: #555555;">지리정보 분석결과</h2>
         <table id="custom-table">
           <thead>
            <tr>
              <th>어장도명</th>
              <th>면적</th>
            </tr>
           </thead>
           <tbody id="fishery_table_tbody">
            <!-- 어장정보 위치 -->
           </tbody>
         </table>
        </div>
      </div>
     </div>
   </div>
   
   <script defer src="script/main/layer.js"></script>
   <script defer src="script/main/cluster.js"></script>
   <script defer src="script/main/category.js"></script>
   <script defer src="script/main/search.js"></script>
</body>