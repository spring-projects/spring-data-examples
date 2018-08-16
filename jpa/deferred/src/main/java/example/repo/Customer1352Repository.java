package example.repo;

import example.model.Customer1352;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1352Repository extends CrudRepository<Customer1352, Long> {

	List<Customer1352> findByLastName(String lastName);
}
