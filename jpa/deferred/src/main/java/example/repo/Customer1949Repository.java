package example.repo;

import example.model.Customer1949;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1949Repository extends CrudRepository<Customer1949, Long> {

	List<Customer1949> findByLastName(String lastName);
}
