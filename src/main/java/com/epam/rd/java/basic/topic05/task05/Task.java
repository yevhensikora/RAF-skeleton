package com.epam.rd.java.basic.topic05.task05;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Task {
	public static final String FILE_NAME = "data.txt";
	private static RandomAccessFile raf;
	private static Runnable runnable;
	private static final List<Thread> threadsList = new ArrayList<>();
	private static int counter = 0;

	public static void createRAF(int numberOfThreads, int numberOfIterations, int pause) throws IOException {
		File file = new File(FILE_NAME);
		FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8, false);
		runnable = () -> {
			try {
				writeLine(writer, numberOfIterations, pause);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		startThreads(numberOfThreads);
		joinThreads();
		writer.close();
		raf = new RandomAccessFile(FILE_NAME, "rw");
		counter = 0;
		threadsList.clear();
	}

	private static void startThreads(int numberOfThreads) {
		for(int i = 0; i < numberOfThreads; ++i) {
			threadsList.add(new Thread(runnable));
		}
		for (Thread thread : threadsList) {
			thread.start();
		}
	}

	private static void joinThreads() {
		for (Thread thread : threadsList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static synchronized void writeLine(FileWriter fileWriter, int numberOfIterations, int sleepTime) throws IOException {
		for (int i = 0; i < numberOfIterations; i++) {
			fileWriter.write(String.valueOf(counter));
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		fileWriter.write(System.lineSeparator());
		counter++;
	}

	public static void main(String[] args) throws IOException {
		createRAF(1, 5, 1);
		Files.readAllLines(Paths.get(FILE_NAME))
				.stream()
				.forEach(System.out::println);

	}
}