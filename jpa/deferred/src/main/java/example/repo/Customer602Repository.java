package example.repo;

import example.model.Customer602;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer602Repository extends CrudRepository<Customer602, Long> {

	List<Customer602> findByLastName(String lastName);
}
