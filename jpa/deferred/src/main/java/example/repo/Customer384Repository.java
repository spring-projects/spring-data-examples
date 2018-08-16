package example.repo;

import example.model.Customer384;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer384Repository extends CrudRepository<Customer384, Long> {

	List<Customer384> findByLastName(String lastName);
}
