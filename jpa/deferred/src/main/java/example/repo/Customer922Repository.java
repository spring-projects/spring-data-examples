package example.repo;

import example.model.Customer922;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer922Repository extends CrudRepository<Customer922, Long> {

	List<Customer922> findByLastName(String lastName);
}
