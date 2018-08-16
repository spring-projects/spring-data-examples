package example.repo;

import example.model.Customer352;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer352Repository extends CrudRepository<Customer352, Long> {

	List<Customer352> findByLastName(String lastName);
}
