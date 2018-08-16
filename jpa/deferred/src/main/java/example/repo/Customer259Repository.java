package example.repo;

import example.model.Customer259;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer259Repository extends CrudRepository<Customer259, Long> {

	List<Customer259> findByLastName(String lastName);
}
