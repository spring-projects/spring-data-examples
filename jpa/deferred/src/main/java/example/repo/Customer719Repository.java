package example.repo;

import example.model.Customer719;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer719Repository extends CrudRepository<Customer719, Long> {

	List<Customer719> findByLastName(String lastName);
}
