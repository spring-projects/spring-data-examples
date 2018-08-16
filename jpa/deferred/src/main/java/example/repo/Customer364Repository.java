package example.repo;

import example.model.Customer364;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer364Repository extends CrudRepository<Customer364, Long> {

	List<Customer364> findByLastName(String lastName);
}
