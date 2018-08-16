package example.repo;

import example.model.Customer1548;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1548Repository extends CrudRepository<Customer1548, Long> {

	List<Customer1548> findByLastName(String lastName);
}
