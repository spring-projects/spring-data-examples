package example.repo;

import example.model.Customer1314;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1314Repository extends CrudRepository<Customer1314, Long> {

	List<Customer1314> findByLastName(String lastName);
}
