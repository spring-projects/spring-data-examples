package example.repo;

import example.model.Customer849;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer849Repository extends CrudRepository<Customer849, Long> {

	List<Customer849> findByLastName(String lastName);
}
