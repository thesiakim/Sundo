//------------------------------------------------------------------------------------
// 클러스터 기능

// RnDLayer,Bissn에서 데이터를 가져올 WFS 소스 생성

// 벡터소스 생성 
//rnd_layer 벡터 소스 생성 
var rndVectorSource = new ol.source.Vector({
  format: new ol.format.GeoJSON(),
  url: function(extent) {
    return 'http://210.113.102.169:8090/geoserver/EDU8/wfs?service=WFS&' +
      'version=1.1.0&request=GetFeature&typeName=EDU8:rnd_layer&' +
      'outputFormat=application/json&srsname=EPSG:4326&' +
      'bbox=' + extent.join(',') + ',EPSG:3857&'+
      'viewparams=0';
  },
  strategy: ol.loadingstrategy.bbox
});
// Marine_eis_data_layer 벡터 소스 생성 
var marineVectorSource = new ol.source.Vector({
  format: new ol.format.GeoJSON(),
  url: function(extent) {
    return 'http://210.113.102.169:8090/geoserver/EDU8/wfs?service=WFS&' +
      'version=1.1.0&request=GetFeature&typeName=EDU8:marine_eis_data_layer&' +
      'outputFormat=application/json&srsname=EPSG:4326&' +
      'bbox=' + extent.join(',') + ',EPSG:3857&'+
      'viewparams=0';
  },
  strategy: ol.loadingstrategy.bbox
});

      
// 클러스터 사이 간격 조절    
var distance = document.getElementById('distance');

// 클러스터링 옵션 설정
var clusterDistance = distance.value; // 클러스터링 거리 설정

// 클러스터 스타일 설정
// rnd_layer 스타일 설정
var rndClusterStyle = new ol.style.Style({
  image: new ol.style.Circle({
    radius: 12,
    fill: new ol.style.Fill({ color: 'rgba(0, 255, 0, 0.5)' }),
    stroke: new ol.style.Stroke({ color: 'blue', width: 1 })
  }),
  text: new ol.style.Text({
    text: '',
    fill: new ol.style.Fill({ color: '#fff' }),
 	font: 'bold 12px Arial'
  })
});

// marine_eis_data_layer 스타일 설정
var marineClusterStyle = new ol.style.Style({
  image: new ol.style.Circle({
    radius: 12,
    fill: new ol.style.Fill({ color: 'rgba(0, 0, 255, 0.5)' }),
    stroke: new ol.style.Stroke({ color: 'blue', width: 1 })
  }),
  text: new ol.style.Text({
    text: '',
    fill: new ol.style.Fill({ color: '#fff' }),
 	font: 'bold 12px Arial'
  })
});


// 클러스터 소스 생성
// rnd_layer 클러스터 소스 생성 
var rndCluster = new ol.source.Cluster({
  distance: clusterDistance,
  source: rndVectorSource
});
// marine 레이어 클러스터 소스 생성 
var marineCluster = new ol.source.Cluster({
  distance: clusterDistance,
  source: marineVectorSource
});

// 클러스터 레이어 생성
// rnd 클러스터 레이어 생성 
var rndClusterLayer = new ol.layer.Vector({
  source: rndCluster,
  style: function(feature) {
    var size = feature.get('features').length;
    rndClusterStyle.getText().setText(size.toString());
    return rndClusterStyle;
  }
});
// marine 클러스터 레이어 생성 
var marineClusterLayer = new ol.layer.Vector({
  source: marineCluster,
  style: function(feature) {
    var size = feature.get('features').length;
    marineClusterStyle.getText().setText(size.toString());
    return marineClusterStyle;
  }
});

// 지도에 클러스터 레이어 추가
map.addLayer(rndClusterLayer);
map.addLayer(marineClusterLayer);


//------------------------------------------------------------------------------------
// 레이어 파라미터 변경 함수 입력 
var elements = document.getElementsByClassName('checkLayer');
for (var i = 0; i < elements.length; i++) {
  elements[i].addEventListener('click', function() {
    clusterLayerUpdate();
  });
}



// 클러스터 레이어 업데이트 
function clusterLayerUpdate(){
	
	// 레이어 파라미터 화면에서 불러와 변환 
	var checkLayerList = document.getElementsByClassName('checkLayer');
	var clusterLayerParameter = new Array();
	for (var i = 0; i < checkLayerList.length; i++) {
  		if(checkLayerList[i].checked){
			  var checkedId = checkLayerList[i].id;
			  var convertingId = checkedId.substring(10);
			  console.log(convertingId);
			clusterLayerParameter.push(convertingId);		  
		}
	}
	
	var viewparams ='selection_id:' + clusterLayerParameter.join("\\,");
	console.log(viewparams);
	
	//rnd 클러스터 레이어 변경
	var newRndVectorSource = new ol.source.Vector({
  		format: new ol.format.GeoJSON(),
  		url: function(extent) {
    			return 'http://210.113.102.169:8090/geoserver/EDU8/wfs?service=WFS&' +
     			'version=1.1.0&request=GetFeature&typeName=EDU8:rnd_layer&' +
      			'outputFormat=application/json&srsname=EPSG:4326&' +
      			'bbox=' + extent.join(',') + ',EPSG:3857&' +
      			'viewparams='+encodeURIComponent(viewparams);
 		 },
  		strategy: ol.loadingstrategy.bbox
	});
	
	var newRndCluster = new ol.source.Cluster({
		  distance: clusterDistance,
		  source: newRndVectorSource
	});
	
	rndClusterLayer.getSource().getSource().clear();
	rndClusterLayer.setSource(newRndCluster);
	
	// marine 클러스터 레이어 변경
	var newMarineVectorSource = new ol.source.Vector({
  		format: new ol.format.GeoJSON(),
  		url: function(extent) {
    			return 'http://210.113.102.169:8090/geoserver/EDU8/wfs?service=WFS&' +
     			'version=1.1.0&request=GetFeature&typeName=EDU8:marine_eis_data_layer&' +
      			'outputFormat=application/json&srsname=EPSG:4326&' +
      			'bbox=' + extent.join(',') + ',EPSG:3857&' +
      			'viewparams='+encodeURIComponent(viewparams);
 		 },
  		strategy: ol.loadingstrategy.bbox
	});
	
	var newMarineCluster = new ol.source.Cluster({
		  distance: clusterDistance,
		  source: newMarineVectorSource
	});
	
	marineClusterLayer.getSource().getSource().clear();
	marineClusterLayer.setSource(newMarineCluster);
	
}

//------------------------------------------------------------------------------------
// 클러스터 간격 조절 기능 
distance.addEventListener('input', function() {
	marineClusterLayer.getSource().setDistance(parseInt(distance.value, 10));
	rndClusterLayer.getSource().setDistance(parseInt(distance.value, 10));
});

map.on('moveend', function() {
  clusterLayerUpdate();
});

