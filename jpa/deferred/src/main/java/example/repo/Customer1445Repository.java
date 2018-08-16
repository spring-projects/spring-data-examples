package example.repo;

import example.model.Customer1445;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1445Repository extends CrudRepository<Customer1445, Long> {

	List<Customer1445> findByLastName(String lastName);
}
