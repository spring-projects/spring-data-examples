package example.repo;

import example.model.Customer1460;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1460Repository extends CrudRepository<Customer1460, Long> {

	List<Customer1460> findByLastName(String lastName);
}
