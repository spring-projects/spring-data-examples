package example.repo;

import example.model.Customer182;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer182Repository extends CrudRepository<Customer182, Long> {

	List<Customer182> findByLastName(String lastName);
}
