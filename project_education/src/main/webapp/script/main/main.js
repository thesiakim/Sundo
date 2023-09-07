
var isMapImageChanged = false;
var isLayerImageChanged = false;
var isLegendImageChanged = false;
var isPenImageChanged = false;

   // 맵 아이콘 변경
   function changeMapImage() {
      var img = document.getElementById('mapImg');
       var originalSrc = 'images/gis/i-map.svg';
       var newSrc = 'images/gis/i-map-on.svg';
       if (!isMapImageChanged) {
           img.src = newSrc;
           isMapImageChanged = true;
       } else {
           img.src = originalSrc;
           isMapImageChanged = false;
       }
   }
   // 레이어 아이콘 변경
   function changeLayerImage() {
      var img = document.getElementById('layerImg');
       var originalSrc = 'images/gis/i-layer.svg';
       var newSrc = 'images/gis/i-layer-on.svg';
       if (!isLayerImageChanged) {
           img.src = newSrc;
           isLayerImageChanged = true;
       } else {
           img.src = originalSrc;
           isLayerImageChanged = false;
       }
   }
   // 범례 아이콘 변경
   function changeLegendImage() {
      var img = document.getElementById('legendImg');
       var originalSrc = 'images/gis/i-legend.svg';
       var newSrc = 'images/gis/i-legend-on.svg';
       if (!isLegendImageChanged) {
           img.src = newSrc;
           isLegendImageChanged = true;
       } else {
           img.src = originalSrc;
           isLegendImageChanged = false;
       }
   }
   // 그리기 아이콘 변경
   function changePenImage() {
      var img = document.getElementById('penImg');
       var originalSrc = 'images/gis/pen.png';
       var newSrc = 'images/gis/pen-on.png';
       if (!isPenImageChanged) {
           img.src = newSrc;
           isPenImageChanged = true;
       } else {
           img.src = originalSrc;
           isPenImageChanged = false;
       }
   }
   


