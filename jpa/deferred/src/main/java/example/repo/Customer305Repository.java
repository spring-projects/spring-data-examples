package example.repo;

import example.model.Customer305;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer305Repository extends CrudRepository<Customer305, Long> {

	List<Customer305> findByLastName(String lastName);
}
