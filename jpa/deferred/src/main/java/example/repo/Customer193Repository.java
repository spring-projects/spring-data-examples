package example.repo;

import example.model.Customer193;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer193Repository extends CrudRepository<Customer193, Long> {

	List<Customer193> findByLastName(String lastName);
}
