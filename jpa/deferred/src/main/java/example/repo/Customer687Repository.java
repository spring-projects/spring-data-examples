package example.repo;

import example.model.Customer687;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer687Repository extends CrudRepository<Customer687, Long> {

	List<Customer687> findByLastName(String lastName);
}
