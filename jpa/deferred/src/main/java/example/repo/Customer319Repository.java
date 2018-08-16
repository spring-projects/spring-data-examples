package example.repo;

import example.model.Customer319;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer319Repository extends CrudRepository<Customer319, Long> {

	List<Customer319> findByLastName(String lastName);
}
