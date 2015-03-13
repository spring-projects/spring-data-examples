# Spring Data JPA - Eclipselink + Static Weaving Example

This project contains an example of using Eclipselink with static weaving with Spring Data. Static weaving is
accomplished with a maven plugin that requires a persistence.xml, so a persistence.xml exists in this project.
The persistence.xml is not actually used by the Spring Boot example and is only used at build time for the maven
plugin. To use the persistence.xml file, please see instructions on how to [use a traditional persistence.xm](http://docs.spring.io/spring-boot/docs/current/reference/html/howto-data-access.html#howto-use-traditional-persistence-xml).

