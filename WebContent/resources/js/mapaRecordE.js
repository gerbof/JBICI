window.onload=function initialize() {
		
		var mapOptions = {
			center: new google.maps.LatLng(-34.9205514, -57.95353690000002), 
			zoom: 14, 
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		
		var map = new google.maps.Map(document.getElementById('mapa-content'), mapOptions);
		var pos=null;
		if( $('#j_idt41\\:ubicacion').val()!=''){
			var ubicacion = $('#j_idt41\\:ubicacion').val().split(",");
			pos=new google.maps.LatLng(ubicacion[0], ubicacion[1]);
		}
		else{
			pos=new google.maps.LatLng(-34.921426, -57.954512);
		}
		
		var marker = new google.maps.Marker({
		    position: pos,
		    map: map,
		    draggable: true
		    });		
		
		google.maps.event.addListener(marker, "dragend", function(e) { 
		    var content=marker.getPosition().lat().toFixed(6) + ' , ' + marker.getPosition().lng().toFixed(6);
		    $('#j_idt41\\:ubicacion').val(content);
		    map.setCenter(marker.getPosition());
		});
}