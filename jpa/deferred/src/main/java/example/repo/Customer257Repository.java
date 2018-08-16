package example.repo;

import example.model.Customer257;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer257Repository extends CrudRepository<Customer257, Long> {

	List<Customer257> findByLastName(String lastName);
}
