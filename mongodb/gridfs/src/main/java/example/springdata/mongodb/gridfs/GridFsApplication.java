/*
 * Copyright 2016 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package example.springdata.mongodb.gridfs;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.util.StreamUtils;

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

        // get file or resource by filename
        GridFSFile gridFsFile1 = gridFsOperations.findOne(query(whereFilename().is("myFile1.txt")));
        System.out.println("Filename: " + gridFsFile1.getFilename() +", MD5: " + gridFsFile1.getMD5());
        GridFsResource gridRes1 = gridFsOperations.getResource("myFile1.txt");
        StreamUtils.copy(gridRes1.getInputStream(), System.out);

        // store file with metaData
        try (InputStream is = new BufferedInputStream(new ClassPathResource("./myCustomerFile.txt").getInputStream())) {
            Customer customerMetaData = new Customer("Hardy", "Lang");
            gridFsOperations.store(is, "myCustomerFile.txt", customerMetaData);
        }

        // search by metaData
        GridFSFile gridFsFile2 = gridFsOperations.findOne(query(whereMetaData("firstName").is("Hardy")));
        System.out.println("Filename: " + gridFsFile2.getFilename() +", MD5: " + gridFsFile2.getMD5());
        GridFsResource gridRes2 = gridFsOperations.getResource(gridFsFile2.getFilename());
        StreamUtils.copy(gridRes2.getInputStream(), System.out);
    }
}
