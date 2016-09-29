Configuration instructions:

Built in IntelliJ Idea
Runnable using standard Java compiler v 1.8

Build and deploy instructions:

Suggested Deploy instructions:
Clone the source from Bitbucket
Import project to IntelliJ
Use IntelliJ to grade build (requires Java SE 1.8)

Standard instructions for Ubuntu:
      
./gradlew clean build 
./gradlew clean test (for running tests)
./gradlew tomcatrunWar

or to get executable
./gradlew war

the war file is in build/libs

If errors are encountered, please make sure you have:

Java Oracle 8 (JDK 8):
sudo apt-get update
sudo apt-get install default-jre
sudo apt-get install default-jdk
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

Get gradle 3.1:
sudo add-apt-repository ppa:cwchien/gradle
sudo apt-get update
sudo apt-get install gradle

Now run grade 
Grade dependencies:

dependencies {
    compile 'org.sql2o:sql2o:1.5.4'
    compile 'com.h2database:h2:1.4.191'
    compile 'com.sparkjava:spark-core:2.3'
    compile 'com.google.code.gson:gson:2.5'
    testCompile group: 'junit', name: 'junit', version: '4.11'
}

Build the project

(Get gradle builder (https://spring.io/guides/gs/gradle/
Use gradle to easily compile the project in ubuntu or other linux)

Testing instructions: ./gradlew clean test
Test Report is generated use gradle clean build
/CodeCoverageReport has test report included

host and port:

http://localhost:8080/delectable


Copyright and licensing instructions:
All code is my original work or based on java open source libraries. There is no further copyright or licensing requirements.

Known bugs:
No significant known bugs

Credits and acknowledgements:

Sql2o Open source Library used to link SQL Database
gson Open source Library by Google to convert Json