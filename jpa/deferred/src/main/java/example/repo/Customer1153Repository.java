package example.repo;

import example.model.Customer1153;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1153Repository extends CrudRepository<Customer1153, Long> {

	List<Customer1153> findByLastName(String lastName);
}
