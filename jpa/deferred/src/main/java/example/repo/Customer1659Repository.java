package example.repo;

import example.model.Customer1659;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1659Repository extends CrudRepository<Customer1659, Long> {

	List<Customer1659> findByLastName(String lastName);
}
