package example.repo;

import example.model.Customer949;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer949Repository extends CrudRepository<Customer949, Long> {

	List<Customer949> findByLastName(String lastName);
}
