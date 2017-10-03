window.onload=function initialize() {
		var estacion=null;
		var disponibles=null;
		var bicis=null;
		var estado=null;
		var url=null;
		var visibles = ['Operativa','En construccion','Cerrada'], marcadores = [];
		var mapOptions = {
			center: new google.maps.LatLng(-34.9205514, -57.95353690000002), 
			zoom: 15, 
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById('mapa-content'), mapOptions);
		var estaciones = "../../rest/estacion";
		
		$.getJSON(estaciones, function(json1){
			var infowindow = new google.maps.InfoWindow();
			
			$.each(json1, function(key, data){
				var ubicacion = (data.ubicacion).split(",");
				var latLng = new google.maps.LatLng(ubicacion[0], ubicacion[1]);
				var image="../../resources/img/";
				switch(data.estado.descripcion) {
			    	case "Cerrada":
			    		image += "icon-close.png";
			        	break;
			    	case "En construccion":
			    		image += "icon-const.png";
			    		break;
			    	default:
			    		image += "icon-op.png";
				}
				var marker = new google.maps.Marker({
					position: latLng,
					map: map,
					title: data.nombre,
					estado: data.estado.descripcion,
					icon: image
				});
				
				marcadores.push(marker);
				
					
				google.maps.event.addListener(marker,'click',function() {
					infowindow.close();
					estacion=data.nombre;
					disponibles=data.cantLugaresLibres;
					bicis=data.cantBicicletas;
					estado=data.estado.descripcion;
					var content="<div>" +
									"<div style='float:left; color:black; padding-left: 15px;'>" +
										"<span style='font-size:18px;font-weight:bold;'>" + estacion + "</span><hr>" +
										"Cantidad de lugares disponibles: "+ disponibles + "<br>" +
										"Cantidad de bicicletas disponibles: " + bicis + "<br>" +
										"Estado estacion: " + estado + "<br><br>" +	
									"</div>" +
								"</div>";
					infowindow.setContent(content);
					infowindow.open(map, marker);

				});
			});
		});
		
		var ocultar_marcadores = function() {  
			  for (var i = 0, length = marcadores.length; i < length; i++) {
			    marcadores[i].setVisible(visibles.indexOf(marcadores[i].estado) !== -1);
			  }
		};
		
		$('input.control').on('change', function(e) {
			 var $this = $(this), valor = $this.val();
			  if ($this.is(':checked')) {
			    // Si está marcado tendremos que añadirla a nuestra lista de visibles 
			    visibles.push(valor);
			  } else {
			    // Nos tocará borrarlo 
			    visibles.splice(visibles.indexOf(valor), 1);
			  }
			  ocultar_marcadores();
		});
}