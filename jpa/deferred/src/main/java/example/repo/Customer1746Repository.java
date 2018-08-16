package example.repo;

import example.model.Customer1746;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1746Repository extends CrudRepository<Customer1746, Long> {

	List<Customer1746> findByLastName(String lastName);
}
