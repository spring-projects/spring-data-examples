package example.repo;

import example.model.Customer506;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer506Repository extends CrudRepository<Customer506, Long> {

	List<Customer506> findByLastName(String lastName);
}
