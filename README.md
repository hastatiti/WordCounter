# WordCounter
Maven project that displays several 'word counts' of a text file
Project has : WordCounter(main java class), WordCounterTest(test class),textFile(text file used for testing) and a pom.xml file.
Main starting point for the application :
WordCounter class via main method or WordCounterTest class via displayResultTest() method. Please replace the displayResult(“fileLocation”)’s file location.

Assumptions made :
- word is assumed to be ‘adjacent chars separated by any number of spaces’, full stop adjacent to a word are removed, decimal points are untouched.
- try catch blocks are omitted as much as possible for readability, only starting point of the program is responsible for catching an exception if no empty files or ‘null’ is used. It simply logs an error message for tidy console output.
- system.out is used for console output
