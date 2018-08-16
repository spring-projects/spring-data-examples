package example.repo;

import example.model.Customer207;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer207Repository extends CrudRepository<Customer207, Long> {

	List<Customer207> findByLastName(String lastName);
}
