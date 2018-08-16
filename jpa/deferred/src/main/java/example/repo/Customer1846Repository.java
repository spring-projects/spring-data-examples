package example.repo;

import example.model.Customer1846;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1846Repository extends CrudRepository<Customer1846, Long> {

	List<Customer1846> findByLastName(String lastName);
}
