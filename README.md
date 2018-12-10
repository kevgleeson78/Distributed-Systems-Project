# Distributed-Systems-Project Car booking service
4th year Distributed systems project
## A RESTfull architectural distributed web application, web client and jdbc system using remote method invocation between a jdbc database and jersey web service. 
## xml marshalling/ Unmarshalling is used to send data over a netwrok using the http methods:
* get to read data from the database
* post to create a new objects and send them to the database
* put to update the database
* delete to remove data from the database.

### Author: Kevin Gleeson 4th year student at [gmit](www.gmit.ie)

## Running the application

### Jar files needed click to download
1. [mysql-connector-java-5.1.40-bin](https://dev.mysql.com/downloads/connector/j/5.1.html)
2. [jstl-1.2](http://www.java2s.com/Code/Jar/j/Downloadjstl12jar.htm)
3. [eclipselink](http://www.eclipse.org/eclipselink/downloads/)
4. [javax.json-1.1](https://jar-download.com/artifacts/org.glassfish/javax.json/1.1/source-code)
5. [javax.json-api_1.1.2](https://jar-download.com/artifacts/javax.json/javax.json-api/1.1.2/source-code)
6. [javax.xml.bind_2.2.12.v201410011542](https://mvnrepository.com/artifact/javax.xml.bind/jaxb-api/2.1)
7. [jaxb-core_2.2.11.v201407311112](https://javaee.github.io/jaxb-v2/)
8. [jaxb-xjc_2.2.11.v201407311112](http://git.eclipse.org/c/eclipselink/eclipselink.runtime.git/commit/?id=db16a89a2baa1792b7af76e54642da198c6077d7)


## Cloning the project

1. Create a new folder on your computer this will be used as a workspace with eclipse.

2. Open the folder and clone this repository to it using the git command:

``` git clone https://github.com/kevgleeson78/Distributed-Systems-Project.git ```

## Eclipse setup WebClient Project
1. Start eclipse and select file -> switch workspace -> other -> browse
2. Browse to the folder with the cloned repository then click launch
3. When the workspace opens select file -> open projects from file system
4. Navigate to the folder with the cloned repository and select the web client project first
5. Setup apache server within the workspace i have used apache 9 for this
6. Change the jre to 1.8 And add then add the webClient jar on the last screen setup page.
7. Right click the project and select properties Change the java compiler compliance to 1.8
8. Right click the project and select properties and project facets. Change the java version to 1.8
  In the runtime tab select the server you have created earlier and click apply and close.
9. Expand the web content folder and then the web-inf folder.
10. Drag the jstl-1.2 jar file to the lib folder

## Eclipse setup DS_Project Project
1. select file -> open projects from file system
2. Browse to the DS_Project folder in the repository
3. Right click the project and select properties Change the java compiler compliance to 1.8
4. Right click the project and select properties and project facets. Change the java version to 1.8
  In the runtime tab select the server you have created earlier and click apply and close.
5. Right click the project and select properties -> build path -> libraries tab -> Add external jars
6. Add the remaining jars downloaded apart from the tstl-1.2.jar file then click apply and close.


## Adding the database

1. If not installed download and install [wamp](http://www.wampserver.com/en/)
2. Start the wamp server
3. Left click the wamp icon in the system tray and then select php myadmin
4. Login with root as the user name and the password blank as default.
5. Select the import tab at the top of the page.
6. Under file to import click the browse button and select the carorder sql file that was cloned with the repository.
7. At the bottom of the page click GO.
8. This will load the database with some sample entries to use.



## Running the application

1. In Eclipse Right click DS_Project -> run as-> run on server
2. Within DS_project expand ie.gmit.sw.JDBC package.
  Right click on server.java -> run as -> java application

3. Within the webClient project expand the web content folder and right click home.jsp -> run as -> run on server.

4. if the webClient resource on the left select it and move it over to the server so its with the DS_Project resource file. Click Finish.

5. Open a browser and copy the following url to the address bar and press enter. http://localhost:8080/WebClient/Home.jsp

6. The following sample order numbers from the database can be used to display update or delete an order.
* 12799
* 56764
* 49153
* 250
* 78198

7. You can also create your own order within the create order page.




