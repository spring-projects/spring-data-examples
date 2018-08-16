package example.repo;

import example.model.Customer272;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer272Repository extends CrudRepository<Customer272, Long> {

	List<Customer272> findByLastName(String lastName);
}
