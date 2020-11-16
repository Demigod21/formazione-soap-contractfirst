formazione-soap-contractfirst
======================================================
Author: Davide Falcone  
Technologies: Camel, CXF, SOAP, Xpath  

Summary : simple project, contract first first based, using Camel, CXF, Xpath and SOAP.


Endpoint description
-------------------------
There are 3 CXF Endpoint in this project. Every single one of them has the same function but uses different methods to handle a soap request.
1) personEndpointXpathComponent : In this endpoint we use a Java processor and Xpath camel component to retrive the info we need.  We use Xpath with local names. 
NOTE: We must specify the CXF property "dataFormat" as "MESSAGE"
2) personEndpointXpathJava : In this endpoint we use a Java processor and Xpath builder to retrive the info we need. It's clearly that it's more verbose than the first endpoint. We use Xpath with Namespaces instead of local names.
NOTE: We must specify the CXF property "dataFormat" as "MESSAGE"
3) personEndpointPojo : In this endpoint we use a Pojo generated at compilation time (InputPerson) and the automatic conversion from the Cxf component to handle the request. We use a processor called "PojoProcessor".
NOTE: We must specify the CXF property "dataFormat" as "POJO" this time


Build and Deploy on Red Hat Fuse
-------------------------

1. Run `mvn clean install` to build the project.
2. Start Red Hat Fuse 7 by running bin/fuse (on Linux) or bin\fuse.bat (on Windows).
3. In the Red Hat Fuse console, enter the following command:

        bundle:install -s mvn:org.example/formazione-soap-contractfirst/1.0-SNAPSHOT

4. Fuse should give you an id when the bundle is deployed

5. You can check that everything is ok by issuing  the command:

        bundle:list
   your bundle should be present at the end of the list


Use the bundle
---------------------

To use the application be sure to have deployed the project in Fuse as described above. 

1. Run 'mvn -Pqtest test'

Or open 'http://localhost:8181/cxf/' in a browser to see 'personEndpointXpathComponent', 'personEndpointXpathJava', 'personEndpointPojo' listed,.
Undeploy the Archive
--------------------

To stop and undeploy the bundle in Fuse:

1. Enter `bundle:list` command to retrieve your bundle id
2. To stop and uninstall the bundle enter

        bundle:uninstall <id>
