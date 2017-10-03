window.onload=function initialize() {
		var estacion=null;
		var disponibles=null;
		var bicis=null;
		var estado=null;
		var url=null;
		geocoder = new google.maps.Geocoder();
		var mapOptions = {
			center: new google.maps.LatLng(-34.9205514, -57.95353690000002), 
			zoom: 15, 
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById('mapa-content'), mapOptions);
		var estaciones = "../../rest/estacion/habilitadas";
		
		$.getJSON(estaciones, function(json1){
			var infowindow = new google.maps.InfoWindow();
			

			$.each(json1, function(key, data){
				var ubicacion = (data.ubicacion).split(",");
				var latLng = new google.maps.LatLng(ubicacion[0], ubicacion[1]);
				var image="../../resources/img/icon-op.png";

				var marker = new google.maps.Marker({
					position: latLng,
					map: map,
					title: data.nombre,
					icon: image
				});
				google.maps.event.addListener(marker,'click',function() {
					$('#info').hide();
					$('#info').empty();
					estacion=data.nombre;
					disponibles=data.cantLugaresLibres;
					bicis=data.cantBicicletas;
					estado=data.estado.descripcion;
					url='../../resources/img/station.png';
					var content="<div id='popup' name='popup'>" +
									"<div style='float:left; color:black; padding-left: 15px; padding-top: 15px;'>" +
										"<div style='padding:5px; margin-left: 84px;'>" +
											"<img src='" + url + "'/>" +
										"</div>" +
										"<span style='font-size:18px;font-weight:bold;'>" + estacion + "</span><hr>" +
										"Cantidad de lugares disponibles: "+ disponibles + "<br>" +
										"Cantidad de bicicletas disponibles: " + bicis + "<br>" +
										"Estado estacion: " + estado + "<br><br>" +
										"<form action='#'>";
										perfil = document.getElementById('perfil').value;
										if(estado == 'Operativa'){
											if(bicis>0)
												content +="<input type='button' value='Retirar' class='btn btn-success' onclick='retirar(\""+estacion+"\",\""+perfil+"\")'>";
											if (disponibles>0)
												content +="<input type='button' value='Estacionar' class='btn btn-primary' onclick='estacionar(\""+estacion+"\",\""+perfil+"\")'>";
										}
					
										content +="<input type='button' style='float: right;' value='Cerrar' class='btn btn-success' onclick='cerrarPopup()'>" +
									"</form>" +
									"</div>" +
									
								"</div>";
					$('#info').append(content);
					$('#info').show();

				});
			});
		});
}

function cerrarPopup(){
	$('#info').hide();
}

function retirar(nombre,username){
	var estaciones = "../../rest/estacion/retirar?nombre="+nombre+"&username="+username;
	$.getJSON(estaciones, function(json1){
		//mensaje('',"Bicicleta Retirada");
		alert('Operacion realizada');
		location.reload();
	})
}

function estacionar(nombre,username){
	var estaciones = "../../rest/estacion/estacionar?nombre="+nombre+"&username="+username;
	$.getJSON(estaciones, function(json1){
		//mensaje('',"Bici Estacionada");
		alert('Operacion realizada');
		location.reload();
	})
}