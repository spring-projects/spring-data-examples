package example.repo;

import example.model.Customer1334;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer1334Repository extends CrudRepository<Customer1334, Long> {

	List<Customer1334> findByLastName(String lastName);
}
