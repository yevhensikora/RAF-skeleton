package com.epam.rd.java.basic.topic05.task05;

import java.io.*;
import java.nio.file.*;

public class Task {
	
	public static final String FILE_NAME = "data.txt";
	
	private static RandomAccessFile raf;
 	
	public static void createRAF(int numberOfThreads, int numberOfIterations, int pause) throws IOException {
		// place your code here
	}

	public static void main(String[] args) throws IOException {
		createRAF(5, 20, 10);
		
		Files.readAllLines(Paths.get(FILE_NAME))
			.stream()
			.forEach(System.out::println);
	}
}
