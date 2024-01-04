<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style>
		*{
			margin:0;
			padding:0;
		}
		
		<style>
			.overlay {position:relative;bottom:85px;border-radius:6px;border: 1px solid #ccc;border-bottom:2px solid #ddd;float:left;}
			.overlay:nth-of-type(n) {border:0; box-shadow:0px 1px 2px #888;}
			.overlay {display:block;text-decoration:none;color:#000;text-align:center;border-radius:10px;font-size:14px;font-weight:bold;overflow:hidden;background: #ffffff;}
			/* .overlay:after {content:'';position:absolute;margin-left:-12px;left:50%;bottom:-11px;width:22px;height:12px;background:url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')} */
		</style>
	
	
	</style>


</head>
<body>
	<!-- 지도를 표시할 div -->
	<div id="map" style="width:100vw;height:100vh;"></div>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b2de374f674db8f269b65de46015cd0d"></script>
	
	<!-- 카카오맵 지도 api 설정 -->
	<script>
		var container = document.getElementById('map');
	    var options = {
	        center: new kakao.maps.LatLng(37.5665, 126.9780),  // 초기 중심 좌표 설정
	        level: 3
	    };
	    var map = new kakao.maps.Map(container, options);
	
	    // 여러 개의 마커 정보 배열
	    var markers = [
	        {
	            position: new kakao.maps.LatLng(35.14049, 126.916429),  // 첫 번째 마커 위치
	            content: '<div class="overlay" style="padding:8px 18px 8px 18px;"><p>송암동</p><p>513254540pt</p></div>',  // 첫 번째 오버레이 내용
	            imageSrc: 'https://cdn-icons-png.flaticon.com/512/243/243457.png'
	        },
	        {
	            position: new kakao.maps.LatLng(35.134349, 126.918263),  // 두 번째 마커 위치
	            content: '<div class="overlay" style="padding:8px 18px 8px 18px;"><p>송암동</p><p>522pt</p></div>',  // 두 번째 오버레이 내용
	            imageSrc: 'https://cdn-icons-png.flaticon.com/512/243/243457.png'
	        }
	    ];
	
	    markers.forEach(function(markerInfo) {
	        // 이미지 마커 설정
	        var markerImage = new kakao.maps.MarkerImage(markerInfo.imageSrc, new kakao.maps.Size(30, 30), { offset: new kakao.maps.Point(15, 15) });
	        var marker = new kakao.maps.Marker({
	            position: markerInfo.position,
	            image: markerImage
	        });
	        
	     	// 맵에 마커 추가
	        marker.setMap(map);  
	
	        // 커스텀 오버레이 설정
	        var customOverlay = new kakao.maps.CustomOverlay({
	            position: markerInfo.position,
	            content: markerInfo.content,
	            xAnchor: 0.49,
	            yAnchor: 1.4
	        });
	        
		    // 맵에 커스텀 오버레이 추가
	        customOverlay.setMap(map);  
	        
	    });


	</script>
	
</body>
</html>