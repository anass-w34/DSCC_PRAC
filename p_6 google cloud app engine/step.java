open eclipse
  help ---market place ------donload google cloud engine
  Click on File -> New -> Project.
 Select Google Cloud Platform -> Google App Engine Standard Java Project.
  
  Add Servlet API to Your Project:
• Right-click on your project in the Project Explorer in Eclipse.
• Select Properties.
• Go to Java Build Path and then click on the Libraries tab.

  Click Add Library.
• Choose Server Runtime (or search for Servlet API if available). Choose Tomcat v9.0 and
click on Finish
*********************************************************************
  package Binary;
  Create a new Java class:
o Right-click on src/main/java and select New -> Class.
o Name the class (e.g., BinarySearch).
*****************************************************************
  package Binary;

public class BinarySearch {

public static int binarySearch(int[] array, int target) {
int left = 0;
int right = array.length - 1;

while (left <= right) {
int mid = left + (right - left) / 2;
//Check if target is present at mid
if (array[mid] == target) {
return mid; // Target found at index mid
}

//If target is greater, ignore left half
if (array[mid] < target) {
left = mid + 1;
} else {
//If target is smaller, ignore right half
right = mid - 1;
}
}
//Target was not found
return -1;
}
}

***************************************************************
Create a new Servlet:
o Right-click on src/main/java and select New -> Servlet.
o Name the servlet (e.g., BinarySearchServlet).

  ******************************************************************

  import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Binary.BinarySearch;

@WebServlet("/search")
public class BinarySearchServlet extends HttpServlet {

protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
	// Get parameters
	String[] arrayStr = request.getParameterValues("array");
	int target = Integer.parseInt(request.getParameter("target"));

	// Convert String array to int array
	int[] array = new int[arrayStr.length];
	for (int i = 0; i < arrayStr.length; i++) {
	array[i] = Integer.parseInt(arrayStr[i]);
	}// Perform binary search
	int result = BinarySearch.binarySearch(array, target);

	// Set the result in the response
	response.setContentType("text/plain");
	if (result != -1) {
	response.getWriter().println("Target found at index: " + result);
	} else {
	response.getWriter().println("Target not found.");
	}
	}
	}

*************************************************************************************
Step 4: Deploy the Application
1. Configure Deployment Descriptor: Make sure to define your servlet in the web.xml file
under src/main/webapp/WEB-INF/.

  DSCCPract6
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── (default package)
│   │   │   │   ├── BinarySearchServlet.java
│   │   │   │   ├── HelloAppEngine.java
│   │   │   ├── Binary
│   │   │       ├── BinarySearch.java
│   │   ├── webapp
│   │       ├── META-INF
│   │       ├── WEB-INF
│   │       │   ├── lib
│   │       │   ├── appengine-web.xml
│   │       │   ├── logging.properties
│   │       │   ├── web.xml
│   │       ├── favicon.ico
│   │       ├── index.html
│
├── src
│   ├── test
│   │   ├── java
│
├── JRE System Library [JavaSE-1.8]
├── App Engine Standard Runtime [App Engine Standard Runti]
├── Web App Libraries
├── JUnit 4
├── Apache Tomcat v9.0 [Apache Tomcat v9.0]
├── build
└── test
************************************************************

  <?xml version="1.0" encoding="utf-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"

xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-
app_3_1.xsd"

version="3.1">
<welcome-file-list>
<welcome-file>index.html</welcome-file>
<welcome-file>index.jsp</welcome-file>
</welcome-file-list>
<servlet>
<servlet-name>BinarySearchServlet</servlet-name>
<servlet-class>BinarySearchServlet</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>BinarySearchServlet</servlet-name>
<url-pattern>/search</url-pattern>
</servlet-mapping>
</web-app>
  ***************************************************************************************************

  Select Run as > App Engine.
  Once the application start running, On browser URL, search for
  http://localhost:8080/search?array=1&array=2&array=3&array=4&target=3
  
  
