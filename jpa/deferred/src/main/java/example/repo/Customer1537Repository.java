package example.repo;

import example.model.Customer1537;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1537Repository extends CrudRepository<Customer1537, Long> {

	List<Customer1537> findByLastName(String lastName);
}
