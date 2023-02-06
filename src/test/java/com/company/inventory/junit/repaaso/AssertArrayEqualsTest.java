package com.company.inventory.junit.repaaso;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class AssertArrayEqualsTest {
	
	@Test
	public void miTest() {
		
		String [] arre1 = {"aa", "bb"};
		String [] arre2 = {"aa", "bb"};
		@SuppressWarnings("unused")
		String [] arre3 = {"aa", "bb", "cc"};
		
		assertArrayEquals(arre1, arre2);
	}

}
