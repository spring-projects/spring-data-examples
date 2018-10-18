package example.springdata.solr.test.util;

import org.junit.Test ;

/**
 * @author Jens Schauder
 */
public class SolrExampleContainerTests {

	@Test
	public void runsWithoutException() {

		SolrExampleContainer container = new SolrExampleContainer("techproducts");
		container.stop();
	}

}
