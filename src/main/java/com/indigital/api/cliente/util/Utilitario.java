package com.indigital.api.cliente.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Utilitario {

	public final static String FORMATO_FECHA = "dd/MM/yyyy";
	
	public static int calcularEdad(Date fechaNacimiento) {
		if(fechaNacimiento == null)
			return 0;
		Period periodo = Period.between(fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
		return periodo.getYears();
	}
	
	public static double calcularPromedio(List<Integer> coleccion) {
		if(coleccion == null)
			return 0;
		if(coleccion.isEmpty())
			return 0;
		double suma = 0;
		for (Integer nro : coleccion) {
			suma+= nro;
		}
		return suma / coleccion.size();
	}
	
	public static double calcularDesviacionEstandar(List<Integer> coleccion) {
		if(coleccion == null)
			return 0;
		if(coleccion.isEmpty())
			return 0;
		double promedio = calcularPromedio(coleccion);
		double suma = 0;
		for (Integer nro : coleccion) {
			suma += Math.pow(Math.abs(nro - promedio), 2);
		}
		return Math.sqrt(suma / coleccion.size());
	}
	
	public static Date fechaProbableMuerte(Date fechaNacimiento, int promedioVida) {
		LocalDate fecNac = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate fechaMuerte = fecNac.plusYears(promedioVida);
		return java.sql.Date.valueOf(fechaMuerte);
	}
}
