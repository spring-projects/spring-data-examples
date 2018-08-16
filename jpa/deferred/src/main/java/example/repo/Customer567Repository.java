package example.repo;

import example.model.Customer567;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer567Repository extends CrudRepository<Customer567, Long> {

	List<Customer567> findByLastName(String lastName);
}
