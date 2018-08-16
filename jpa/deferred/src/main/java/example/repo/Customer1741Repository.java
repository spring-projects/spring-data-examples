package example.repo;

import example.model.Customer1741;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1741Repository extends CrudRepository<Customer1741, Long> {

	List<Customer1741> findByLastName(String lastName);
}
