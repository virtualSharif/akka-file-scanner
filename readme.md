Documentation
	Project name : Akka File Scanner
	Hours took to build : Approximately 8hours

Steps to Run:

	mvn compile exec:exec

Sample Output:

     >>> Press check the output and press ENTER to exit <<<
     [INFO] [06/21/2017 02:14:19.472] [FileScanner-akka.actor.default-dispatcher-2] [akka://FileScanner/user/aggregator] file-name: read.txt, word-count: 8
     [INFO] [06/21/2017 02:14:19.473] [FileScanner-akka.actor.default-dispatcher-2] [akka://FileScanner/user/aggregator] file-name: read2.txt, word-count: 9

     Shutting down actor system...

Steps to run test:

    mvn clean install

Sample Output:

    -------------------------------------------------------
     T E S T S
    -------------------------------------------------------
    Running com.m800.akka.ApplicationTest
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.04 sec

    Results :

    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

References:

    http://doc.akka.io/docs/akka/current/java/guide/quickstart.html

Author:

    Name: sharif malik
    Email id: malik_sharif@ymail.com
