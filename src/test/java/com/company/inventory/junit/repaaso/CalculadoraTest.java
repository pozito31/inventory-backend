package com.company.inventory.junit.repaaso;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {
	
	Calculadora cal;
	
	@BeforeAll
	public static void primero() {
		
		System.out.println("primero");
		
	}
	
	@AfterAll
	public static void ultimo() {
		
		System.out.println("ultimo");
	}
	
	@AfterEach
	public void ultimoPorCadaPrueba() {
		
		System.out.println("ultimo por cada prueba");
		
	}
	
	@BeforeEach
	public void primeroPorCadaPrueba() {
		System.out.println("primero por cada prueba");
		cal = new Calculadora();
	}
	
	
	@Test
	@DisplayName("prueba de sumar calculadora")
	public void sumarTest() {
		
		assertEquals(2, cal.sumar(1, 1));
	}
	
	@Test
	@Disabled("Desabilita la prueba de restar")
	public void restarTest() {
		
		assertEquals(4, cal.restar(5, 1));
	}
	
	@Test
	public void MultiplicarTest() {
		
		assertEquals(4, cal.multiplicar(2, 2));
	}
	
	@Test
	public void DividirTest() {
		
		assertEquals(3, cal.dividir(6, 2));
	}

}
