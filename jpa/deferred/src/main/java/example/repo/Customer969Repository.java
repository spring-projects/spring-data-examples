package example.repo;

import example.model.Customer969;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer969Repository extends CrudRepository<Customer969, Long> {

	List<Customer969> findByLastName(String lastName);
}
