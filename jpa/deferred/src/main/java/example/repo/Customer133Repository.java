package example.repo;

import example.model.Customer133;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer133Repository extends CrudRepository<Customer133, Long> {

	List<Customer133> findByLastName(String lastName);
}
