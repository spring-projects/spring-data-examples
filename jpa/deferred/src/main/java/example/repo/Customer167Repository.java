package example.repo;

import example.model.Customer167;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer167Repository extends CrudRepository<Customer167, Long> {

	List<Customer167> findByLastName(String lastName);
}
