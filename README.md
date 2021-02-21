# jasked
JAsked. A FAQ manager system

##Version Notes

**Version 0.0.2**

Very simple. Basic operations. Basic user control. No CSS customization and no internationalization.

There is a database model dump at sr/main/sqldump/

The Wildfly Application Server have to be configured for posgresql connection. Tested with standalone-full.xml configuration file, edited as:



	<datasources>
			...
			<datasource jndi-name="java:jboss/datasources/JAskedDS" pool-name="JAskedDS" enabled="true" use-java-context="true">
                    <connection-url>jdbc:postgresql://localhost:5432/jaskeddb</connection-url>
                    <driver>postgresql</driver>
                    <security>
                        <user-name>jasked_app</user-name>
                        <password>jasked_app</password><!-- change the password-->
                    </security>
          </datasource>
          <drivers>
                    ...
                    <driver name="postgresql" module="org.postgresql"> <!-- add the jar file in the corret folder-->
                        <driver-class>org.postgresql.Driver</driver-class>
                        <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
                    </driver>
          </drivers>
                ...
	</datasources>
            

##Next steps at development:

CSS customization;

Audit record.
 



