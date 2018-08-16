package example.repo;

import example.model.Customer659;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer659Repository extends CrudRepository<Customer659, Long> {

	List<Customer659> findByLastName(String lastName);
}
