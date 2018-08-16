package example.repo;

import example.model.Customer1882;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1882Repository extends CrudRepository<Customer1882, Long> {

	List<Customer1882> findByLastName(String lastName);
}
