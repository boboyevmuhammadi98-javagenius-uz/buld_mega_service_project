package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Contact;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    ContactRepository contactRepository;

    /**
     * @return all object
     */
    public List<Contact> getAllContact() {
        return contactRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public Contact getContactById(long id) {
        return contactRepository.findById(id).orElse(null);
    }

    /**
     * @param contact - new object
     * @return ApiResponse class
     */
    public ApiResponse addContact(Contact contact) {
        contactRepository.save(
                new Contact(
                        contact.getAddress(),
                        contact.getAddressRu(),
                        contact.getPhoneNumber(),
                        contact.getPhoneNumberRu(),
                        contact.getEmail()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id      old object id for finding
     * @param contact new object
     * @return ApiResponse class
     */
    public ApiResponse editContact(long id, Contact contact) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (!optionalContact.isPresent())
            return new ApiResponse("contact not found", false);
        Contact savedContact = optionalContact.get();
        savedContact.setAddress(contact.getAddress());
        savedContact.setAddressRu(contact.getAddressRu());
        savedContact.setPhoneNumber(contact.getPhoneNumber());
        savedContact.setPhoneNumberRu(contact.getPhoneNumberRu());
        savedContact.setEmail(contact.getEmail());
        contactRepository.save(savedContact);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteContact(long id) {
        try {
            contactRepository.deleteById(id);
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
