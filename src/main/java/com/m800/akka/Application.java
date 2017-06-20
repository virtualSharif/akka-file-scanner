package com.m800.akka;

import java.io.IOException;

import com.m800.akka.actor.Aggregator;
import com.m800.akka.actor.FileParser;
import com.m800.akka.actor.FileScanner;
import com.m800.akka.actor.FileScanner.WhomToScan;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Application {

	public static void main(String[] args) throws IOException {

		final String directoryName = "src/main/resources";
		final ActorSystem system = ActorSystem.create("FileScanner");

		try {
			final ActorRef aggregator = system.actorOf(Aggregator.props(), "aggregator");
			final ActorRef fileParser = system.actorOf(FileParser.props(aggregator), "fileParser");
			final ActorRef fileScanner = system.actorOf(FileScanner.props(fileParser), "fileScanner");

			fileScanner.tell(new WhomToScan(directoryName), ActorRef.noSender());

			System.out.println(">>> Press check the output and press ENTER to exit <<<");
			System.in.read();
			System.out.println("Shutting down actor system...");
		    system.terminate();

		} finally {
			system.terminate();
		}
	}
}
