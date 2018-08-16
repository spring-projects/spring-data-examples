package example.repo;

import example.model.Customer331;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer331Repository extends CrudRepository<Customer331, Long> {

	List<Customer331> findByLastName(String lastName);
}
