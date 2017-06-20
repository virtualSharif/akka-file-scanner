package com.m800.akka;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.m800.akka.actor.Aggregator.StartOfLine;
import com.m800.akka.actor.FileParser;
import com.m800.akka.actor.FileScanner;
import com.m800.akka.actor.FileScanner.WhomToScan;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;

public class ApplicationTest {

	static ActorSystem system;
	private final String testDirectoryName = "src/test/resources/";

	@BeforeClass
	public static void setup() {
		system = ActorSystem.create();
	}

	@AfterClass
	public static void teardown() {
		TestKit.shutdownActorSystem(system);
		system = null;
	}

	@Test
	public void testFileScanner() {
		new TestKit(system) {
			{
				final TestKit testProbe = new TestKit(system);
				final ActorRef fileParser = system.actorOf(FileParser.props(testProbe.getRef()), "fileParserTest");
				final ActorRef fileScanner = system.actorOf(FileScanner.props(fileParser), "fileScannerTest");

				fileScanner.tell(new WhomToScan(testDirectoryName), ActorRef.noSender());

				StartOfLine startOfLine = testProbe.expectMsgClass(StartOfLine.class);
				assertEquals("read.txt", startOfLine.fileName);
			}
		};
	}

}
