package com.m800.akka.actor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.m800.akka.actor.Aggregator.EndOfLine;
import com.m800.akka.actor.Aggregator.StartOfLine;
import com.m800.akka.actor.Aggregator.WhichLineToParse;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class FileParser extends AbstractActor {

	public static Props props(ActorRef aggregator) {
		return Props.create(FileParser.class, () -> new FileParser(aggregator));
	}

	public static class WhomToParse {
		public final File file;

		public WhomToParse(File who) {
			this.file = who;
		}
	}

	private Scanner scanner;
	private final ActorRef aggregator;

	private FileParser(ActorRef aggregator) {
		this.aggregator = aggregator;
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(WhomToParse.class, this::parse)
				.build();
	}

	private void parse(WhomToParse whomToParse) throws FileNotFoundException {
		this.scanner = new Scanner(whomToParse.file);
		aggregator.tell(new StartOfLine(whomToParse.file.getName()), getSelf());
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			aggregator.tell(new WhichLineToParse(line), getSelf());
		}
		aggregator.tell(new EndOfLine(whomToParse.file.getName()), getSelf());
	}
}
