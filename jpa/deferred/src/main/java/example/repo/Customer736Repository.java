package example.repo;

import example.model.Customer736;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer736Repository extends CrudRepository<Customer736, Long> {

	List<Customer736> findByLastName(String lastName);
}
