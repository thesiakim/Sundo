		// 선택된 레이어 버튼 클릭 시 동작
	  document.getElementById('category-selected').addEventListener('click', function(event) {
	    // 선택된 체크박스 개수 확인
	    var selectedCheckboxes = document.querySelectorAll('.checkLayer:checked');
	    
	    if (selectedCheckboxes.length === 0) {
	      alert('선택된 레이어가 없습니다.');
	      event.preventDefault(); // 버튼 클릭 동작 중지
	    } else {
	      // 모든 레이어 숨기기
	      hideAllLayers();
	
	      // 선택된 체크박스와 그 상위 목록 표시
	      showSelectedLayers();
	    }
	  });
	
		// 전체 레이어 버튼 클릭 시 동작
		document.getElementById('category-all').addEventListener('click', function() {
		  // 모든 레이어 표시
		  showAllLayers();
		});
		
		// 선택된 레이어 표시 함수
		  function showSelectedLayers() {
		    var checkboxes = document.getElementsByClassName('checkLayer');
	
		    for (var i = 0; i < checkboxes.length; i++) {
		      var checkbox = checkboxes[i];
		      if (checkbox.checked) {
		        showLayerHierarchy(checkbox);
		      }
		    }
		  }
	
		// 모든 레이어 숨기기 함수
		function hideAllLayers() {
		  var checkboxes = document.getElementsByClassName('checkLayer');
		  for (var i = 0; i < checkboxes.length; i++) {
		    var checkbox = checkboxes[i];
		    checkbox.parentNode.style.display = 'none';
		  }
		}
	
		// 전체 레이어 표시 함수
		function showAllLayers() {
		  var checkboxes = document.getElementsByClassName('checkLayer');
		  for (var i = 0; i < checkboxes.length; i++) {
		    var checkbox = checkboxes[i];
		    checkbox.parentNode.style.display = 'block';
		  }
		}
	
		// 선택된 체크박스와 그 상위 목록 표시 함수
		function showLayerHierarchy(checkbox) {
			
		  checkbox.parentNode.style.display = 'block';
	
		  var parentList = checkbox.parentNode.parentNode;
		  while (parentList.tagName === 'UL') {
		    parentList.parentNode.style.display = 'block';
		    parentList = parentList.parentNode.parentNode;
		  }
		}

	  
// ----------------------------------------------------------------------
	
	  // category-reset 버튼 클릭 시 이벤트 핸들러
	  document.getElementById('category-reset').addEventListener('click', function() {
	    // 모든 체크박스 요소를 선택
	    var checkboxes = document.getElementsByClassName('checkLayer');
	
	    // 체크박스 개수만큼 반복하면서 체크 해제
	    for (var i = 0; i < checkboxes.length; i++) {
	      checkboxes[i].checked = false;
	    }
	
	    // clusterLayerUpdate / showAllLayers 함수 호출
	    clusterLayerUpdate();
	    showAllLayers();
	  });

	function clusterLayerUpdate() {
	    // 레이어 파라미터 화면에서 불러와 변환 
	    var checkLayerList = document.getElementsByClassName('checkLayer');
	    var clusterLayerParameter = new Array();
	    for (var i = 0; i < checkLayerList.length; i++) {
	      if (checkLayerList[i].checked) {
	        var checkedId = checkLayerList[i].id;
	        var convertingId = checkedId.substring(10);
	        clusterLayerParameter.push(convertingId);		  
	      }
	    }
	
	    var viewparams = 'selection_id:' + clusterLayerParameter.join("\\,");
	
	    // rnd 클러스터 레이어 변경
	    var newRndVectorSource = new ol.source.Vector({
	      format: new ol.format.GeoJSON(),
	      url: function(extent) {
	        return 'http://210.113.102.169:8090/geoserver/EDU8/wfs?service=WFS&' +
	          'version=1.1.0&request=GetFeature&typeName=EDU8:rnd_layer&' +
	          'outputFormat=application/json&srsname=EPSG:4326&' +
	          'bbox=' + extent.join(',') + ',EPSG:3857&' +
	          'viewparams=' + encodeURIComponent(viewparams);
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
	          'viewparams=' + encodeURIComponent(viewparams);
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

		// 전체 레이어 표시 함수
	function showAllLayers() {
		  var checkboxes = document.getElementsByClassName('checkLayer');
		  for (var i = 0; i < checkboxes.length; i++) {
		    var checkbox = checkboxes[i];
		    checkbox.parentNode.style.display = 'block';
		  }
		}