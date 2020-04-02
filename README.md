# Dragons of Mugloar (backend)

You can find documentation and description at [DragonsOfMugloar](https://www.dragonsofmugloar.com/).

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Java 14](https://openjdk.java.net/projects/jdk/14/):  OpenJDK 14 required.
   Depending on your system, you can install Gradle either from site or as a pre-packaged bundle. (Ubuntu 19.10 command)
   
       $ sudo apt install openjdk-14-jdk

2. [Gradle](https://gradle.org/): (Optional you can use wrapper instead) Use Gradle to run  and build the project.
   Depending on your system, you can install Gradle either from system repos or as a pre-packaged bundle.
       
       $ sudo apt install gradle

After installing Gradle, you should be able to run the following command to build the project.
You will only need to run this command when dependencies change in [build.gradle](build.gradle).

    gradle build
    
    
### Run tests

Unit test can be run by 
    
    gradle clean test
        
Integration test can be run by 
    
    gradle clean integrationTest 
    

All tests by one task
    
    gradle clean check       

