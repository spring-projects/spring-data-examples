package example.repo;

import example.model.Customer1952;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1952Repository extends CrudRepository<Customer1952, Long> {

	List<Customer1952> findByLastName(String lastName);
}
