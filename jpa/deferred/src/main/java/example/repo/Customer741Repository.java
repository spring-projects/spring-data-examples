package example.repo;

import example.model.Customer741;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer741Repository extends CrudRepository<Customer741, Long> {

	List<Customer741> findByLastName(String lastName);
}
