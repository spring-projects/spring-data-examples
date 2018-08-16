package example.repo;

import example.model.Customer453;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer453Repository extends CrudRepository<Customer453, Long> {

	List<Customer453> findByLastName(String lastName);
}
