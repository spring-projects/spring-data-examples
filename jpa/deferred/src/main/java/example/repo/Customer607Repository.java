package example.repo;

import example.model.Customer607;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer607Repository extends CrudRepository<Customer607, Long> {

	List<Customer607> findByLastName(String lastName);
}
