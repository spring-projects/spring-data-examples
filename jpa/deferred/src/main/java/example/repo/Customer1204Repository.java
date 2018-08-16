package example.repo;

import example.model.Customer1204;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1204Repository extends CrudRepository<Customer1204, Long> {

	List<Customer1204> findByLastName(String lastName);
}
