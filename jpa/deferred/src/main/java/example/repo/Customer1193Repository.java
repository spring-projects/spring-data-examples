package example.repo;

import example.model.Customer1193;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1193Repository extends CrudRepository<Customer1193, Long> {

	List<Customer1193> findByLastName(String lastName);
}
