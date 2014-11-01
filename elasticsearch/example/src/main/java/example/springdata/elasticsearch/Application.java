package example.springdata.elasticsearch;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		ExampleService example = ctx.getBean("exampleService", ExampleService.class);

		//simple search on text
		example.textSearch();

		//simple search on geo location
		example.geoSearch();

		//facets - terms on keywords
		example.termFacetOnKeywords();

		//facets - histogram on date per month
		example.histogramFacetOnDate();

		System.out.println("\nPress 'ctrl' + 'c' to exit");
		System.in.read();
	}
}
