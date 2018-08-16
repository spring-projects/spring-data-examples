package example.repo;

import example.model.Customer750;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer750Repository extends CrudRepository<Customer750, Long> {

	List<Customer750> findByLastName(String lastName);
}
