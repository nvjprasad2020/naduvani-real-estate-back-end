package com.naduvani.realestate.person;

import com.naduvani.realestate.enities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person findPersonByEmailId(String emailId) {
        return personRepository.findByEmailId( emailId);
    }
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        if (personRepository.existsById(id)) {
            updatedPerson.setId(id);
            return personRepository.save(updatedPerson);
        } else {
            // Handle non-existing person
            return null;
        }
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}
