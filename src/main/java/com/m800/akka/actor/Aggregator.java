package com.m800.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Aggregator extends AbstractActor {

	public static Props props() {
		return Props.create(Aggregator.class, () -> new Aggregator());
	}

	public static class WhichLineToParse {
		public final String line;

		public WhichLineToParse(String line) {
			this.line = line;
		}
	}

	public static class EndOfLine {
		public final String fileName;

		public EndOfLine(String fileName) {
			this.fileName = fileName;
		}
	}

	public static class StartOfLine {
		public final String fileName;

		public StartOfLine(String fileName) {
			this.fileName = fileName;
		}
	}

	private String fileName;
	private Integer wordCount;
	private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	private Aggregator() {
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(StartOfLine.class, this::initiateWordCount)
				.match(WhichLineToParse.class, this::updateWordCount)
				.match(EndOfLine.class, this::printLog)
				.build();
	}

	private void printLog(EndOfLine endOfLine) {
		log.info("file-name: " + endOfLine.fileName + ", word-count: " + wordCount.toString());
	}

	private void initiateWordCount(StartOfLine startOfLine) {
		this.fileName = startOfLine.fileName;
		this.wordCount = 0;
	}

	private void updateWordCount(WhichLineToParse whichLineToParse) {
		this.wordCount += getNoOfWords(whichLineToParse.line);
	}

	private Integer getNoOfWords(String line) {
		return line.split(" ").length;
	}
}
