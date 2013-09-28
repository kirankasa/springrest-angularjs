Todo app
==========
<div class="row-fluid">
  <div class="span8">
	<p>
	   This is an todo app developed by using spring rest.
	</p>
	<p>
	   Angular js is used as client side framework
	</p>
       <p>
           <b>Live Demo hosted at cloud foundry </b> http://todoapp.cfapps.io/ 
       </p>    
  </div>
</div>


Tools used :
* [Spring roo](http://projects.spring.io/spring-roo/)
* [Maven](http://maven.apache.org/)  
* [Angularjs](http://angularjs.org/)
* [MySql](http://www.mysql.com/)
* [SendGrid](http://sendgrid.com/)



Prerequisites :

<p>
* Mysql database
</p>
<p>
   	Modify database.properties accordingly.
</p>
<p>	
   	Modify hibernate.hbm2ddl.auto field in persistence.xml accordingly.
</p>

<p>
* Sendgrid
</p>
<p>
	Modify sendgrid.properties for sending emails.
</p>

Steps to run :

	git clone https://github.com/kiranreddykasa/springrest-angularjs.git
	mvn clean package tomcat:run
	http://localhost:8080/todoapp/todoes
