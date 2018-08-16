package example.repo;

import example.model.Customer610;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer610Repository extends CrudRepository<Customer610, Long> {

	List<Customer610> findByLastName(String lastName);
}
