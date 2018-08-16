package example.repo;

import example.model.Customer1257;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1257Repository extends CrudRepository<Customer1257, Long> {

	List<Customer1257> findByLastName(String lastName);
}
