package example.repo;

import example.model.Customer1687;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1687Repository extends CrudRepository<Customer1687, Long> {

	List<Customer1687> findByLastName(String lastName);
}
