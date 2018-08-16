package example.repo;

import example.model.Customer60;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer60Repository extends CrudRepository<Customer60, Long> {

	List<Customer60> findByLastName(String lastName);
}
