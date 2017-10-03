package model;

import java.util.ArrayList;

public class Aplicacion {
	ArrayList<Bicicleta> bicicletas;
	ArrayList<Estacion> estaciones;
	ArrayList<PerfilDeUsuario> perfiles;
	
	public ArrayList<Bicicleta> getBicicletas(){
		return bicicletas;
	}
	
	public ArrayList<Estacion> getEstaciones(){
		return estaciones;
	}
	
	public ArrayList<PerfilDeUsuario> getPerfiles(){
		return perfiles;
	}
	
	public void registrarUsuario(PerfilDeUsuario u){
		perfiles.add(u);
	}
}
