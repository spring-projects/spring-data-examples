package example.repo;

import example.model.Customer512;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer512Repository extends CrudRepository<Customer512, Long> {

	List<Customer512> findByLastName(String lastName);
}
