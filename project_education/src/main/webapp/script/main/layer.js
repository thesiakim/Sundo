// 브이월드 레이어 관련 
      
 // 브이월드 지도(base~Satelite)
let Base = new ol.layer.Tile({
   name : "Base",
   source: new ol.source.XYZ({
      url: 'http://api.vworld.kr/req/wmts/1.0.0/1E878C02-B854-3C6F-B2E7-8FFE0E33253C/Base/{z}/{y}/{x}.png'  // WMTS API 사용
   })
});
  
let white = new ol.layer.Tile({
      name : "white",
      source: new ol.source.XYZ({
         url: 'http://api.vworld.kr/req/wmts/1.0.0/1E878C02-B854-3C6F-B2E7-8FFE0E33253C/white/{z}/{y}/{x}.png'  // WMTS API 사용
      })
   });
  
let midnight = new ol.layer.Tile({
   name : "midnight",
   source: new ol.source.XYZ({
      url: 'http://api.vworld.kr/req/wmts/1.0.0/1E878C02-B854-3C6F-B2E7-8FFE0E33253C/midnight/{z}/{y}/{x}.png'  // WMTS API 사용
   })
});

let Hybrid = new ol.layer.Tile({
   name : "Hybrid",
   source: new ol.source.XYZ({
      url: 'http://api.vworld.kr/req/wmts/1.0.0/1E878C02-B854-3C6F-B2E7-8FFE0E33253C/Hybrid/{z}/{y}/{x}.png'  // WMTS API 사용
   })
});

let Satellite = new ol.layer.Tile({
   name : "Satellite",
   source: new ol.source.XYZ({
      url: 'http://api.vworld.kr/req/wmts/1.0.0/1E878C02-B854-3C6F-B2E7-8FFE0E33253C/Satellite/{z}/{y}/{x}.jpeg'  // WMTS API 사용
   })
});
  
   
  //vworld 지도 입력 
  var map = new ol.Map({
    layers: [Hybrid, white, Satellite, Base],
    target: 'gis_map',
    view: new ol.View({
      center: ol.proj.fromLonLat([127.5, 37.5]),
      zoom: 7.5
    })
  });


//------------------------------------------------------------------------------------
  
// 레이어 변경 기능

//일반지도 클릭 이벤트 핸들러
document.getElementById('vworld-base').addEventListener('click', function() {
  updateLayerByName('Base');
});

// 회색지도 클릭 이벤트 핸들러
document.getElementById('vworld-gray').addEventListener('click', function() {
  updateLayerByName('white');
});

 // 위성지도 클릭 이벤트 핸들러
document.getElementById('vworld-satbrid').addEventListener('click', function() {
  updateLayerByName('Satellite');
});

// 하이브리드 클릭 이벤트 핸들러
document.getElementById('vworld-sathybrid').addEventListener('click', function() {
  updateLayerByName('Hybrid');
});


// 레이어 업데이트
function updateLayerByName(name) {
  var layers = map.getLayers();
  for (var i = 0, len = layers.getLength(); i < len; i++) {
    var layer = layers.item(i);
    if (layer.get('name') === name) {
      layers.remove(layer);
      layers.insertAt(3, layer);
      break;
    }
  }
}
  

//------------------------------------------------------------------------------------
// 기타 레이어

// 보호구역 레이어 입력
 var protectedAreaLayer = new ol.layer.Tile({
   source : new ol.source.TileWMS({
      url : 'http://210.113.102.169:8090/geoserver/EDU5/wms?service=WMS', // 1. 레이어 URL
      params : {
         'VERSION' : '1.1.0', // 2. 버전
         'LAYERS' : 'EDU5:Protected_areas_jh', // 3. 작업공간:레이어 명
         'BBOX' : [124.606993139946, 33.104696111763, 131.954758427956, 38.5978898306375], 
         'SRS' : 'EPSG:4326', // SRID
         'FORMAT' : 'image/png' // 포맷
      },
      serverType : 'geoserver',
   })
});
      
// 시도 레이어 입력
 var sidoEdgeLayer = new ol.layer.Tile({
   source : new ol.source.TileWMS({
      url : 'http://210.113.102.169:8090/geoserver/EDU5/wms?service=WMS', // 1. 레이어 URL
      params : {
         'VERSION' : '1.1.0', // 2. 버전
         'LAYERS' : 'EDU5:ctp_rvn_jh', // 3. 작업공간:레이어 명
         'BBOX' : [746110.259983499, 1458754.04415633, 1387949.59274307, 2068443.95462902], 
         'SRS' : 'EPSG:5179', // SRID
         'FORMAT' : 'image/png' // 포맷
      },
      serverType : 'geoserver',
   })
});

// 보호구역 체크박스 클릭 이벤트 처리
var protectedAreaCheckbox = document.getElementById('proteted_area');
protectedAreaCheckbox.addEventListener('change', function() {
    if (this.checked) {
		map.addLayer(protectedAreaLayer);
    } else {
        map.removeLayer(protectedAreaLayer);
    }
});

// 시도 구분 체크박스 클릭 이벤트 처리
var ctpRvbCheckbox = document.getElementById('ctp_rvb');
ctpRvbCheckbox.addEventListener('change', function() {
    if (this.checked) {
        map.addLayer(sidoEdgeLayer);
    } else {
		map.removeLayer(sidoEdgeLayer);
    }
});

// 체크박스 요소 선택
var fisheryManagementCheckbox = document.getElementById('fishery_management');
var fisheryObservatoryCheckbox = document.getElementById('fishery_observatory');

// 체크박스 상태 변경 이벤트 리스너 추가
fisheryManagementCheckbox.addEventListener('change', toggleFisheryManagementLayer);
var fisheryManagementLayer;

// 면허어장도 레이어 표출/비표출 함수
	function toggleFisheryManagementLayer() {
	  if (fisheryManagementCheckbox.checked) {
	    // WFS 소스 생성
	    var fisheryManagementSource = new ol.source.Vector({
	      format: new ol.format.GeoJSON(),
	      url: 'http://210.113.102.169:8090/geoserver/EDU3/wfs?service=WFS&version=1.1.0&'
				+'request=GetFeature&typeName=EDU3:LF_layer&outputFormat=application/json&srsname=EPSG:4326'
	    });
	    // 면허어장도 레이어 생성
	    fisheryManagementLayer = new ol.layer.Vector({
	      source: fisheryManagementSource
	    });
	    map.addLayer(fisheryManagementLayer);
	  } else {
	    map.removeLayer(fisheryManagementLayer);
	  }
	}


fisheryObservatoryCheckbox.addEventListener('change', function() {
  if (fisheryObservatoryCheckbox.checked) {
    // 체크박스가 체크되었을 때 실시간 관측도 레이어를 지도에 추가
   map.addLayer(wfsLayer);
  } else {
    // 체크박스가 체크 해제되었을 때 실시간 관측도 레이어를 지도에서 제거
    map.removeLayer(wfsLayer);
  }
});

// WFS 소스 생성
var wfsSource = new ol.source.Vector({
  format: new ol.format.GeoJSON(),
  url: 'http://210.113.102.169:8090/geoserver/EDU5/wfs?service=WFS&' +
    'version=1.1.0&request=GetFeature&typeName=EDU5:observe_jh&' +
    'outputFormat=application/json&srsname=EPSG:4326'
});

// WFS 레이어 생성
var wfsLayer = new ol.layer.Vector({
  source: wfsSource,
  style: function(feature) {
    // feature에서 속성 값을 가져와 아이콘과 스타일을 다르게 구성
    var agency = feature.get('agency');
    var iconUrl;
    // agency 값에 따라 다른 아이콘 URL 선택
    switch (agency) {
      case '국립수산과학원':
        iconUrl = 'images/icon/nifs.png';
        break;
      case '기상청':
        iconUrl = 'images/icon/kma.png';
        break;
      case '한수원':
        iconUrl = 'images/icon/khnp.png';
        break;
      case '지자체':
        iconUrl = 'images/icon/government.png';
        break;
      case '점검중':
        iconUrl = 'images/icon/inspect.png';
        break;
      default:
        iconUrl = 'images/icon/inspect.png';
    }

    // 아이콘 스타일 생성
    var iconStyle = new ol.style.Style({
      image: new ol.style.Icon({
        src: iconUrl,
        size: [40, 40], // 아이콘 크기
        anchor: [0.5, 0.5], // 아이콘 앵커 위치
        scale: 0.5 // 아이콘 크기 비율
      })
    });

    return iconStyle;
  }
});


//------------------------------------------------------------------------------------
// 실시간 관측소 클릭시 이벤트 생성. 
// 2023-06-01 조경민

// Overlay 요소를 생성하여 feature 정보를 표시할 팝업을 추가
var overlay = new ol.Overlay({
  element: document.getElementById('real_time_inform'),
  positioning: 'bottom-center',
  stopEvent: false,
  offset: [0, -10]
});
map.addOverlay(overlay);

// 클릭 이벤트 핸들러
map.on('pointermove', function(event) {
  var pixel = event.pixel;

  // 클릭한 좌표 주변의 피처 가져오기
  var features = map.getFeaturesAtPixel(pixel,{
    layerFilter: function(layer) {
      return layer === wfsLayer;
    }});

  // 폴리곤 그리기 기능이 실행 중인지 확인
  var isDrawing = map.getInteractions().getArray().some(function(interaction) {
    return interaction instanceof ol.interaction.Draw;
  });

  // 팝업 닫기 
	overlay.setPosition(undefined);

  if (!isDrawing&&features && features.length > 0) {
    var firstFeature = features[0];
    var properties = firstFeature.getProperties();
	
    // 팝업에 피처 정보를 표시
    var overlayElement = overlay.getElement();
    var observatory_nm = overlayElement.querySelector('#observatory_nm');
    var surface_class = overlayElement.querySelector('#surface_class');
    var middle_class = overlayElement.querySelector('#middle_class');
    var low_class = overlayElement.querySelector('#low_class');
    //var temp = overlayElement.querySelector('#temp');
    var salt = overlayElement.querySelector('#salt');
    var oxygen = overlayElement.querySelector('#oxygen');
    
    // null 체크 함수 
    function nullCheck(checkedElement){
      return checkedElement != null ? checkedElement : '미설치';
   }
    
   function formatTime(time) {
     var date = new Date(time);
     var year = date.getFullYear();
     var month = (date.getMonth() + 1).toString().padStart(2, '0');
     var day = date.getDate().toString().padStart(2, '0');
     var hour = date.getHours().toString().padStart(2, '0');
     var min = date.getMinutes().toString().padStart(2, '0');
     var sec = date.getSeconds().toString().padStart(2, '0');
     
     return year + '-' + month + '-' + day + ' ' + hour + ':' + min + ':' + sec;
   }

    // 수온 null 체크 함수 
    function nullCheckWtr(checkedElement){
      return checkedElement != null ? checkedElement+'℃' : '미설치';
   }
    
    // 산소 null 체크 함수 
    function nullCheckOxygen(checkedElement){
      return checkedElement != null ? checkedElement+'mg/L' : '미설치';
   }
    
    // 팝업창에 정보 입력
    observatory_nm.innerHTML = nullCheck(properties['observator']).replace(/\(.*\)/g, '')+
   '<span class="date">'+ (properties['obsdtm'] != null ? formatTime(properties['obsdtm']) : '자료없음' )+'</span>';
    surface_class.innerText = nullCheckWtr(properties['wtrtmp1']);
    middle_class.innerText = nullCheckWtr(properties['wtrtmp2']);
    low_class.innerText = nullCheckWtr(properties['wtrtmp3']);
    salt.innerText = nullCheck(properties['cdt1']);
    oxygen.innerText = nullCheckOxygen(properties['dox1']);
    
    // 팝업을 클릭한 피처의 위치에 표시 
    overlay.setPosition(event.coordinate);
  }
});
     
//------------------------------------------------------------------------------------
// 모달 팝업 요소 생성
var fishingPopup = document.getElementById('fishing_ground_inform');

// 모달 팝업의 Overlay 생성
var fishingOverlay = new ol.Overlay({
  element: fishingPopup,
  positioning: 'bottom-center',
  stopEvent: false,
  offset: [0, -10]
});

// 맵에 Overlay 추가
map.addOverlay(fishingOverlay);

// 클릭 이벤트 핸들러
map.on('pointermove', function(event) {
  var pixel = event.pixel;

  // 클릭한 좌표 주변의 피처 가져오기
  var features = map.getFeaturesAtPixel(pixel, {
    layerFilter: function(layer) {
      return layer === fisheryManagementLayer;
    }
  });

  // 팝업 닫기
  fishingOverlay.setPosition(undefined);

  if (features && features.length > 0) {
    var firstFeature = features[0];
    var properties = firstFeature.getProperties();
	
	// null 체크 함수 
    function nullCheck(checkedElement){
      return checkedElement != null ? checkedElement : '정보없음';
   }

	 // 어장면적 null 체크 함수
    function nullCheckfishery_space(checkedElement){
      return checkedElement != null ? checkedElement+'(m²)' : '정보없음';
   }
	
    // 팝업에 피처 정보를 표시
    var license_nu = fishingPopup.querySelector('#license_nu');
    var sido_name = fishingPopup.querySelector('#sido_name');
    var sigungu_name = fishingPopup.querySelector('#sigungu_name');
    var surface_loc = fishingPopup.querySelector('#surface_loc');
    var license_no = fishingPopup.querySelector('#license_no');
    var fishery_nu = fishingPopup.querySelector('#fishery_nu');
    var fishery_space = fishingPopup.querySelector('#fishery_space');
    var license_sort = fishingPopup.querySelector('#license_sort');
    var license_date = fishingPopup.querySelector('#license_date');
    var license_period = fishingPopup.querySelector('#license_period');
    var post_period = fishingPopup.querySelector('#post_period');
    var fishery_type = fishingPopup.querySelector('#fishery_type');
    var fishery_method_l = fishingPopup.querySelector('#fishery_method_l');
    var fishery_method_m = fishingPopup.querySelector('#fishery_method_m');
    var fishery_product = fishingPopup.querySelector('#fishery_product');
    var fishery_season = fishingPopup.querySelector('#fishery_season');
	
	
    // 팝업창에 정보 입력
		license_nu.textContent = nullCheck(properties['license_nu']);
		sido_name.textContent = nullCheck(properties['sido_name']);
		sigungu_name.textContent = nullCheck(properties['sigungu_name']);
		surface_loc.textContent = nullCheck(properties['surface_loc']);
		license_no.textContent = nullCheck(properties['license_no']);
		fishery_nu.textContent = nullCheck(properties['fishery_nu']);
		fishery_space.textContent = nullCheckfishery_space(properties['fishery_space']);
		license_sort.textContent = nullCheck(properties['license_sort']);
		license_date.textContent = nullCheck(properties['license_date']);
		license_period.textContent = nullCheck(properties['license_period']);
		post_period.textContent = nullCheck(properties['post_period']);
		fishery_type.textContent = nullCheck(properties['fishery_type']);
		fishery_method_l.textContent = nullCheck(properties['fishery_method_l']);
		fishery_method_m.textContent = nullCheck(properties['fishery_method_m']);
		fishery_product.textContent = nullCheck(properties['fishery_product']);
		fishery_season.textContent = nullCheck(properties['fishery_season']);
	
    // 팝업을 클릭한 피처의 위치에 표시
    fishingOverlay.setPosition(event.coordinate);
  }
});

//------------------------------------------------------------------------------------
// 화면 줌인/ 아웃 버튼

document.getElementById('zoom-out').onclick = function() {
   var view = map.getView();
   var zoom = view.getZoom();
   view.setZoom(zoom - 1);
 };
   
document.getElementById('zoom-in').onclick = function() {
   var view = map.getView();
   var zoom = view.getZoom();
   view.setZoom(zoom + 1);
}

//------------------------------------------------------------------------------------
// 폴리곤 관련 

let drawLayer = new ol.layer.Vector({
        source: new ol.source.Vector(),
        style: new ol.style.Style({
            fill: new ol.style.Fill({
                color: 'rgba(255, 255, 255, 0.2)'
            }),
            stroke: new ol.style.Stroke({
                color: '#ffcc33',
                width: 2
            }),
            image: new ol.style.Circle({
                radius: 7,
                fill: new ol.style.Fill({
                    color: '#ffcc33'
                })
            })
        })
    });

    map.addLayer(drawLayer);

    var typeSelect = document.getElementById('type');
    var draw;
    var polygonCoordinates = []; // 위치 정보를 저장할 배열 

    function resetMap() {
        map.removeInteraction(draw);
        drawLayer.getSource().clear();
        polygonCoordinates = []; // 저장된 위치 정보 초기화
      addInteraction(); // 폴리곤 그리기 기능 다시 추가
    }

    function addInteraction() {
        var type = typeSelect.value;
        if (type !== 'None') {
            draw = new ol.interaction.Draw({
                source: drawLayer.getSource(),
                type: type
            });

            draw.on('drawend', function (event) {
                var feature = event.feature;
                var geometry = feature.getGeometry();
                var coordinates = geometry.getCoordinates();

                // 다중 폴리곤을 그릴 때는 coordinates 배열이 2차원 배열일 수 있으므로 flatten 처리
                if (type === 'Polygon') {
                    coordinates = coordinates[0];
                }

                // 위치 정보(위도, 경도)를 저장
                polygonCoordinates = coordinates.map(function(coordinate) {
                    return ol.proj.transform(coordinate, 'EPSG:3857', 'EPSG:4326');
                });

				// 꼭지점이 6개 초과인 경우에 알람 띄우기
                if (polygonCoordinates.length > 6) {
                    alert("삼각형, 사각형 또는 오각형 모양으로 그려주세요");
                    setTimeout(resetMap, 0); // 비동기적으로 resetMap() 함수 실행
                }

				// 겹친 부분의 특정 속성만 가져오기
				var intersectionFeatures = getIntersectionFeatures(feature);

                // 필요한 경우 저장된 위치 정보를 활용할 수 있습니다.
                console.log(polygonCoordinates);
				console.log(intersectionFeatures);
				// 모달 창 열기
				openCustomModal(intersectionFeatures);
            });

            map.addInteraction(draw);
        }
    }

	// 겹친 좌표를 통해 면허어장도 레이어의 다른 정보 불러오기
	function getIntersectionFeatures(polygonFeature) {
		var intersectionFeatures = [];
		var polygonGeometry = polygonFeature.getGeometry();
		var fisheryFeatures = fisheryManagementLayer.getSource().getFeatures();
		
		fisheryFeatures.forEach(function (fisheryFeature) {
			var fisheryGeometry = fisheryFeature.getGeometry();
			var fisheryExtent = fisheryGeometry.getExtent();
			
			if (polygonGeometry.intersectsExtent(fisheryExtent)) {
				var properties = fisheryFeature.getProperties();
				var selectedProperties = {
					license_nu: properties.license_nu,
					fishery_space: properties.fishery_space
				};
				intersectionFeatures.push(selectedProperties);
			}
		});
		return intersectionFeatures;
	}
	 function openCustomModal(features) {
       var customModal = document.getElementById('custom-modal');
       var customTable = document.getElementById('custom-table');
       customModal.style.display = "block";
      var tbody = customTable.querySelector('tbody');
       tbody.innerHTML = '';

      var hasResult = false;

       features.forEach(function(feature) {
         var row = document.createElement('tr');
         console.log(feature);
         var licenseNu = feature.license_nu;
         let fisherySpace = feature.fishery_space;
         
         if (fisherySpace !== null) {
            hasResult = true;   // 값이 존재하는 경우 값을 true로 변경
         
            var cell1 = document.createElement('td');
            cell1.textContent = licenseNu;
            row.appendChild(cell1);
            
            var cell2 = document.createElement('td');
            cell2.textContent = fisherySpace + "(m²)";
            row.appendChild(cell2);
            
            tbody.appendChild(row);
         }
       });
      
      if (!hasResult) {
         var row = document.createElement('tr');
         var cell = document.createElement('td');
         cell.colSpan = 2;
         cell.textContent = "해당 결과 없음";
         row.appendChild(cell);
         tbody.appendChild(row);
      }
   }
   
   
   
   function closeCustomModal() {
      var customModal = document.getElementById('custom-modal');
      customModal.style.display = "none";
   }
   
    function removeInteraction() {
        map.removeInteraction(draw);
    }

    typeSelect.onchange = function () {
        map.removeInteraction(draw);
        addInteraction();
    };
      

//------------------------------------------------------------------------------------
 /*
// 클러스터 데이터 요청(cors 보안이슈로 인한 wfs 사용하지 않는 버전)
// 2023-05-30 조경민 : 보안이슈 해결로 현재 사용하지 않음

$.ajax({
    url: '/project_education/main/cluster-coordinates.do',
    method: 'GET',
    dataType: 'json'
}).done(function(response) {
    var features = new Array(response.length);
    
    for (var i = 0; i < response.length; i++) {
        var coordinate = [response[i].r_lon , response[i].r_lat];
        features[i] = new ol.Feature(new ol.geom.Point(ol.proj.transform(coordinate,'EPSG:4326', 'EPSG:900913')));
    }
    
    var source = new ol.source.Vector({
      features: features
   });
   
   var cluster = new ol.source.Cluster({
      distance: parseInt(distance.value, 10),
      source: source
   });
   
   var styleCache = {};
   var clusterLayer = new ol.layer.Vector({
    source: cluster,
    style: function(feature) {
      var size = feature.get('features').length;
      var style = styleCache[size];
      if (!style) {
        style = new ol.style.Style({
          image: new ol.style.Circle({
            radius: 13,
            stroke: new ol.style.Stroke({
              color: '#fff'
            }),
            fill: new ol.style.Fill({
              color: '#3399CC'
            })
          }),
          text: new ol.style.Text({
            text: size.toString(),
            fill: new ol.style.Fill({
              color: '#fff'
            })
          })
        });
        styleCache[size] = style;
      }
      return style;
    }
  });
  
  map.addLayer(clusterLayer);
    
}).fail(function(error) {
    console.log(JSON.stringify(error.responseText));
});
   
    
*/
 //----------------------------------------------------------
document.addEventListener("DOMContentLoaded", function() {
           var foldableList = document.querySelector("#foldable-list");
           
           foldableList.addEventListener("click", function(event) {
               var target = event.target;
               if (target.tagName === "LI") {
                   var sublist = target.querySelector("ul");
                   if (sublist) {
                       sublist.style.display = sublist.style.display === "none" ? "block" : "none";
                   }
               }
           });
       });