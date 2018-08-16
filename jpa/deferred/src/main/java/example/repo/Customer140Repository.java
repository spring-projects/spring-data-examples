package example.repo;

import example.model.Customer140;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer140Repository extends CrudRepository<Customer140, Long> {

	List<Customer140> findByLastName(String lastName);
}
