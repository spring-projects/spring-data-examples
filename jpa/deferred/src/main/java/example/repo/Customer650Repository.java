package example.repo;

import example.model.Customer650;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface Customer650Repository extends CrudRepository<Customer650, Long> {

	List<Customer650> findByLastName(String lastName);
}
