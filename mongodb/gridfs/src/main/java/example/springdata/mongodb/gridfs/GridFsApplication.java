package example.springdata.mongodb.gridfs;

import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import java.io.BufferedInputStream;
import java.io.InputStream;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereMetaData;

/**
 * Spring Boot sample application to show the usage of {@link GridFsOperations} with Spring Data MongoDB.
 *
 * @author Hartmut Lang
 */
@SpringBootApplication
public class GridFsApplication implements CommandLineRunner {

    @Autowired
    private GridFsOperations gridFsOperations;

	public static void main(String[] args) {
		SpringApplication.run(GridFsApplication.class, args);
	}

    @Override
    public void run(String... strings) throws Exception {

	    // store file
        try (InputStream is = new BufferedInputStream(new ClassPathResource("./myFile1.txt").getInputStream())) {
            gridFsOperations.store(is, "myFile1.txt");
        }

        // search by filename
        GridFSDBFile gridFsFile1 = gridFsOperations.findOne(query(whereFilename().is("myFile1.txt")));
        System.out.println("Filename: " + gridFsFile1.getFilename() +", MD5: " + gridFsFile1.getMD5());
        gridFsFile1.writeTo(System.out);

        // store file with metaData
        try (InputStream is = new BufferedInputStream(new ClassPathResource("./myCustomerFile.txt").getInputStream())) {
            Customer customerMetaData = new Customer("Hardy", "Lang");
            gridFsOperations.store(is, "myCustomerFile.txt", customerMetaData);
        }

        // search by metaData
        GridFSDBFile gridFsFile2 = gridFsOperations.findOne(query(whereMetaData("firstName").is("Hardy")));
        System.out.println("Filename: " + gridFsFile2.getFilename() +", MD5: " + gridFsFile2.getMD5());
        gridFsFile2.writeTo(System.out);
    }
}
