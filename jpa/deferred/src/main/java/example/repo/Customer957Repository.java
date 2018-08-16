package example.repo;

import example.model.Customer957;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer957Repository extends CrudRepository<Customer957, Long> {

	List<Customer957> findByLastName(String lastName);
}
