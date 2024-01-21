# IndexingAPI
## About
I built this application for a college project. The assignment required students to design and develop a multithreaded indexing API in Java 19+ that allowed a word index to be created from an e-book. 

### Notes about the Assignment
- The application should parse a specified e-book using virtual threads and then build an index by examining every word in each sentence.
- The index should contain a list of words that relate to the page numbers on which they appear and the dictionary defined meaning of each word.
- The API should also parse and process three files:
  - A dictionary file
  - A list of common words
  - An eBook.
- The API should exclude the common words from the index.

## Design Rationale
I made an application that comprised of classes that could be used as independent modules. 
Each module provides a function that is accessible to other modules (classes) though a constructor and or one public method.

## Workflow and Comments
- Options 1, 2, 3 and 4 collect input from the user.
  - ApplicationInterface contains MenuItems as private inner class.
  - MenuItems provides instructions to the user as per the assignment brief.
- Option 5 executes the main functions of the application in the following order:
  -ApplicationInterface calls a method that validates the user input:
    - That method is in the Utils interface that ApplicationInterface implements.
  - ApplicationInterface creates an instance of EBookIndexer which takes the file paths from steps 1, 2 and 3 as parameters in its constructor.
  - ApplicationInterface calls EBookIndexer’s public start() method.
    - EBookIndexer is a concrete class that extends FileCollector, and FileCollector extends VirtualThreadFileParser.
  - EBookIndexer’s public start() method calls collectFiles(), then buildIndex() and finally removeCommonWords();
  - collectFiles() parses the three text documents using the abstract class VirtualThreadFileParser (Ireeder 2014; Healy 2022a & 2022b; Lee 2022).
    - I let VirtualThreadFileParser collect the page-numbers and the words. However, I think I’m breaching the abstraction principle as perhaps not all extended classes would use the page-numbers data.
  - FileCollector stores the data from the files so that they can then be manipulated by EBookIndexer.
    - I had planned to extends a second class from FIleCollector that processed text from a website, but I ran out of time.
  - buildIndex() (in EBookIndexer) loops though the data in FIleCollector and adds it to a local Tree-Map using the WordDetail class as an object (Baeldung 2022; Coding Cleverly 2020).
  - buildIndex() calls the printProgress method the in the ProgressBar interface implemented by FIleCollector.
    - I let FileCollector implement ProgressBar as I had planned to extend another concrete class
    - Additionally, I could not get thread().sleep to work as it excessively slowed down the loop in buildIndex(): I could not solve that issue.
  - removeCommonWords() removes the common words list from the e-book index map (Klimek 2009).
  - ApplicationInterface then calls local method printOrder() which uses methods in the Indexable interface and the Utils interface:
    - printOrder() lets the user print the e-book index in forward or reverse order
    - save() in the Utils interface prints the index map to a file and reverseOrder() in the Indexable interface uses a comparator to change the order of the map if the user chooses that option.
  - Finally, ApplicationInterface calls printStats() which uses a method in the Indexable interface to print the five most common and five least common words in the index map.
- Option 6 quits the application by resetting the instance variable and closing the do loop that keeps the application running.

## Features
- The abstract class VirtualThreadFileParser uses virtual threads to process all three text files input by the user (Healy 2022b).
- EBookIndexer also uses virtual threads to build the e-book index (Healy 2022b).
- The interface Indexable provides a way to reverse the order of the map and sort a number of frequent words using a Comparator (Techie Delight n.d. ; Verma 2022).
- FIleCollector implements the progress bar as an interface.
- Save() in the Utils interface uses String.format() to format the output (Shubham 2020).
- ApplicationInterface uses the ConsoleColour enum to enhance the user interface.
- Validation in the Utils class prevents the app from executing if a field is not complete.

## References
Baeldung (2022) Using the Map.Entry Java, 27 June, available https://www.baeldung.com/java-map- entry [accessed26 December 2022].

Coding Cleverly (2022) Dictionary Program in Java | Using HashMap in Java | Java Project, YouTube, available https://www.youtube.com/watch?v=73zZe9j1III&ab_channel=CodingCleverly [accessed 27 December 2022].

Healy, J. (2022a) Indexing the words of a book with a map, 51848, Advanced Object Orientated Software Development, available: https://web.microsoftstream.com/video/3d84f117-a777-43c1- b176-5687520eedb4 [accessed 01 December 2022].

Healy, J. (2022b) Using Virtual Threads & Structured Concurrency, 51848, Advanced Object Orientated Software Development, available: https://web.microsoftstream.com/video/cfc481bb- 46a5-473f-b728-1e24fb94835a?referrer=https:%2F%2Fvlegalwaymayo.atu.ie%2F [accessed 15 December 2022].

Ireeder, A. (2014), Reading a text file and skipping blank lines, Stack Overflow, 06 April, available https://stackoverflow.com/questions/22889561/reading-text-file-and-skipping-blank-lines-until-eof- is-reached [accessed 18December 2022].

Klimek, B. (2009) Checking contents of a map in another map in java, Stack Overflow, 07 May, available https://stackoverflow.com/questions/833379/checking-contents-of-a-map-in-another- map-in-java [accessed 20 December 2022].

Lee, A. (2020) Java: Read a CSV file into an Array, YouTube, available https://www.youtube.com/watch?v=-Aud0cDh-J8&ab_channel=AlexLee [accessed 10 December 2022].

Shubham, S. (2020) How to pad a String in Java, Geeks For Geeks, 04 April, available https://www.geeksforgeeks.org/how-to-pad-a-string-in-java/ [accessed 17 December 2022].

Techie Delight (n.d.) Sort Map in Java by reverse ordering of its keys, available https://www.techiedelight.com/sort-map-java-reverse-ordering-keys/ [accessed 29 December 2022].

Verma, J. (2022) How to Sort a HashMap by Value in Java, Digital Ocean, 03 August, available https://www.digitalocean.com/community/tutorials/sort-hashmap-by-value-java#sorting-hashmap- by-value-simple-example [accessed 29 December 2022].
