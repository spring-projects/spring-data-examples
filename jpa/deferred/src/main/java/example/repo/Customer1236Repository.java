package example.repo;

import example.model.Customer1236;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1236Repository extends CrudRepository<Customer1236, Long> {

	List<Customer1236> findByLastName(String lastName);
}
