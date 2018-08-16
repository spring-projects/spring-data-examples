package example.repo;

import example.model.Customer204;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer204Repository extends CrudRepository<Customer204, Long> {

	List<Customer204> findByLastName(String lastName);
}
