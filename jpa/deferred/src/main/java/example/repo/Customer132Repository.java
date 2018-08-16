package example.repo;

import example.model.Customer132;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer132Repository extends CrudRepository<Customer132, Long> {

	List<Customer132> findByLastName(String lastName);
}
