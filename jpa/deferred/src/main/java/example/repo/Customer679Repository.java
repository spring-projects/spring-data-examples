package example.repo;

import example.model.Customer679;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer679Repository extends CrudRepository<Customer679, Long> {

	List<Customer679> findByLastName(String lastName);
}
