package example.repo;

import example.model.Customer1259;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1259Repository extends CrudRepository<Customer1259, Long> {

	List<Customer1259> findByLastName(String lastName);
}
