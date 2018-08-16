package example.repo;

import example.model.Customer801;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer801Repository extends CrudRepository<Customer801, Long> {

	List<Customer801> findByLastName(String lastName);
}
