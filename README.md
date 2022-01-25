#EMD
Compile
mvn clean package
Datasets:

Add dependency in pom.xml:
        <dependency>
            <groupId>torch-clus</groupId>
            <artifactId>torch-clus</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

run experiments
java -cp ./target/DBF-1.0-SNAPSHOT.jar ballTree.experiment.main

