
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
  
	
  // vworld 지도 입력 
  var map = new ol.Map({
    layers: [Hybrid, white, Satellite, Base],
    target: 'gis_map',
    view: new ol.View({
      center: ol.proj.fromLonLat([128.4, 35.7]),
      zoom: 7
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
		url : 'http://210.113.102.169:8090/geoserver/EDU8/wms?service=WMS', // 1. 레이어 URL
		params : {
			'VERSION' : '1.1.0', // 2. 버전
			'LAYERS' : 'EDU8:protected_area_layer', // 3. 작업공간:레이어 명
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
		url : 'http://210.113.102.169:8090/geoserver/EDU8/wms?service=WMS', // 1. 레이어 URL
		params : {
			'VERSION' : '1.1.0', // 2. 버전
			'LAYERS' : 'EDU8:sido_edge_layer', // 3. 작업공간:레이어 명
			'BBOX' : [746110.259983499, 1458754.04415633, 1387949.59274307, 2068443.95462902], 
			'SRS' : 'EPSG:5179', // SRID
			'FORMAT' : 'image/png' // 포맷
		},
		serverType : 'geoserver',
	})
});
   
// rnd 레이어 입력
var RnDLayer = new ol.layer.Tile({
	source : new ol.source.TileWMS({
		url : 'http://210.113.102.169:8090/geoserver/EDU8/wms?service=WMS', // 1. 레이어 URL
		params : {
			'VERSION' : '1.1.0', // 2. 버전
			'LAYERS' : 'EDU8:rnd_layer', // 3. 작업공간:레이어 명
			'BBOX' : [125.9519444, 33.44944, 126.2525, 34.95333333], 
			'SRS' : 'EPSG:4326', // SRID
			'FORMAT' : 'image/png' // 포맷
		},
		serverType : 'geoserver',
	})
});


// 레이어 입력    
map.addLayer(protectedAreaLayer);
map.addLayer(sidoEdgeLayer);
map.addLayer(RnDLayer);



//------------------------------------------------------------------------------------
// RnD 레이어 파라미터 변경 (수정중...)
var elements = document.getElementsByClassName('checkLayer');
for (var i = 0; i < elements.length; i++) {
  elements[i].addEventListener('click', function() {
    var selectedElementId = this.id;
    RnDLayerUpdate(selectedElementId);
  });
}

var RnDLayerParameter = new Array();

// RnD 레이어 파라미터 변경
function RnDLayerUpdate(id){
	 var idNumber = id.substring(0.2);
	 if(document.getElementById(id).checked){
		RnDLayerParameter.push(idNumber);
	 }else{
		var index = RnDLayerParameter.indexOf(idNumber);
		if (index !== -1) {
		  RnDLayerParameter.splice(index, 1);
		} 
	 }
	 
	var viewparams ='seselection_id:${' + RnDLayerParameter.join(",")+'}'
	 
	var params = RnDLayer.getSource().getParams();
	params.VIEWPARAMS = viewparams;   //해당 layer의 viewparams으로 설정
	
	RnDLayer.getSource().updateParams(params); //update하여 layer다시 불러오기
}


//------------------------------------------------------------------------------------
// 클러스터 기능

// RnDLayer에서 데이터를 가져올 WFS 소스 생성
var vectorSource = new ol.source.Vector({
  format: new ol.format.GeoJSON(),
  url: function(extent) {
    return 'http://210.113.102.169:8090/geoserver/EDU8/wfs?service=WFS&' +
      'version=1.1.0&request=GetFeature&typeName=EDU8:rnd_layer&' +
      'outputFormat=application/json&srsname=EPSG:4326&' +
      'bbox=' + extent.join(',') + ',EPSG:4326';
  },
  strategy: ol.loadingstrategy.bbox
});

// 클러스터 사이 간격 조절    
var distance = document.getElementById('distance');       	

// 클러스터링 옵션 설정
var clusterDistance = distance.value; // 클러스터링 거리 설정

// 클러스터링 레이어 스타일 설정
var clusterStyle = new ol.style.Style({
  image: new ol.style.Circle({
    radius: 10,
    fill: new ol.style.Fill({ color: 'rgba(0, 0, 255, 0.5)' }),
    stroke: new ol.style.Stroke({ color: 'blue', width: 1 })
  }),
  text: new ol.style.Text({
    text: '',
    fill: new ol.style.Fill({ color: '#fff' })
  })
});

// 클러스터 소스 생성
var cluster = new ol.source.Cluster({
  distance: clusterDistance,
  source: vectorSource
});

// 클러스터 레이어 생성
var clusterLayer = new ol.layer.Vector({
  source: cluster,
  style: function(feature) {
    var size = feature.get('features').length;
    clusterStyle.getText().setText(size.toString());
    return clusterStyle;
  }
});

// 지도에 클러스터 레이어 추가
map.addLayer(clusterLayer);

//------------------------------------------------------------------------------------
// 클러스터 간격 조절 기능 
distance.addEventListener('input', function() {
	cluster.setDistance(parseInt(distance.value, 10));
});

//------------------------------------------------------------------------------------
// rnd 레이어 클릭시 정보받기. info라는 id를 가진 div가 있어야 함
// 현재 iframe 이 same origin 정책으로 막혀있음 
 map.on('singleclick', function (evt) {
	    document.getElementById('gis-info').innerHTML = '';
	    
	    var viewResolution = map.getView().getResolution();
	    var url = RnDLayer.getSource().getGetFeatureInfoUrl(
	        evt.coordinate, viewResolution, 'EPSG:4326',
	        { 'INFO_FORMAT': 'text/html' }
	    );
	    
	    if (url) {
	        document.getElementById('info').innerHTML =
	            '<iframe width="100%" seamless="" src="' + url + '"></iframe>';
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



     

      
      