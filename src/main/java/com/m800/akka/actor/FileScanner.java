package com.m800.akka.actor;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.m800.akka.actor.FileParser.WhomToParse;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class FileScanner extends AbstractActor {

	public static Props props(ActorRef fileParser) {
		return Props.create(FileScanner.class, () -> new FileScanner(fileParser));
	}

	public static class WhomToScan {
		public final String directoryName;

		public WhomToScan(String who) {
			this.directoryName = who;
		}
	}

	private final ActorRef fileParser;

	private FileScanner(ActorRef fileParser) {
		this.fileParser = fileParser;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(WhomToScan.class, this::scan)
				.build();
	}

	private void scan(WhomToScan whomToScan) {
		List<File> files = getFiles(whomToScan.directoryName);
		if (files != null && !files.isEmpty()) {
			files.stream()
			.filter(File::isFile)
			.forEach(file -> fileParser.tell(new WhomToParse(file), getSelf()));
		}
	}

	private List<File> getFiles(String directoryName) {
		File directory = new File(directoryName);
		return directory.isDirectory() ? Arrays.asList(directory.listFiles()) : null;
	}
}
