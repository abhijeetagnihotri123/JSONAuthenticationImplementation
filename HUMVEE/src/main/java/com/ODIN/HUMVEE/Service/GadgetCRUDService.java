package com.ODIN.HUMMVEE.Service;

import com.ODIN.HUMMVEE.Entities.Gadget;
import com.ODIN.HUMMVEE.Repositories.GadgetRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GadgetCRUDService {

    Logger log = LoggerFactory.getLogger(GadgetCRUDService.class);

    @Autowired
    GadgetRepository gadgetRepository;

    public ResponseEntity<List<Gadget>>getAllGadgets()
    {
        ResponseEntity<List<Gadget>>result = null;
        List<Gadget>allGadgets = null;

        try {

            allGadgets = gadgetRepository.findAll();
            result = new ResponseEntity<>(allGadgets , HttpStatus.OK);

            log.info("All records fetched {} , " , allGadgets.toString());

        }catch (Exception e){

            log.error("Some error occurred in retrieving records {} , ", e.toString());
            result = new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);
        }

        return result;
    }

    public ResponseEntity<Gadget>getGadgetById(long id){

        ResponseEntity<Gadget>result = null;
        Gadget gadget = null;

        try {

            if(gadgetRepository.findById(id).isPresent()){

                gadget = gadgetRepository.findById(id).get();
                result = new ResponseEntity<>(gadget , HttpStatus.OK);
                log.info("Gadget with {} as follows : {}", id , gadget.toString());
            }else{
                result = new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);
                log.error("Gadget with {} not found" , id);
            }

        }catch (Exception e){

            log.error("Some error in retrieving records {}" , e.toString());
            result = new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);

        }

        return result;
    }

    public ResponseEntity<Gadget>createRecord(Gadget gadget)
    {
        ResponseEntity<Gadget>result = null;
        try{
            gadgetRepository.save(gadget);
            result = new ResponseEntity<>(gadget , HttpStatus.OK);
            log.info("Record saved : {} , ", gadget.toString());

        }catch (Exception e){

            result = new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);
            log.error("Some error occurred in creating record : {}" , e.toString());
        }
        return result;
    }

    public ResponseEntity<Gadget>updateRecord(long id , Gadget gadget)
    {
        ResponseEntity<Gadget>result = null;
        try{

            if(gadgetRepository.findById(id).isPresent()){

                Gadget currentGadget = gadgetRepository.findById(id).get();
                currentGadget.setName(gadget.getName());
                currentGadget.setType(gadget.getType());
                currentGadget.setPrice(gadget.getPrice());
                gadgetRepository.save(currentGadget);

                result = new ResponseEntity<>(currentGadget , HttpStatus.OK);

                log.info("Updated Record : {}" , currentGadget.toString());

            }else{

                result = new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);
                log.error("No such record present for updation");

            }

        }catch (Exception e){

            log.error("Some error occured in record updation {}" , e.toString());
            result = new ResponseEntity<>(null , HttpStatus.BAD_REQUEST);
        }
        return result;
    }

    public ResponseEntity<String>deleteRecord(long id){
        ResponseEntity<String>result = null;
        Gadget gadget = null;

        try {

            if(gadgetRepository.findById(id).isPresent()){

                gadget = gadgetRepository.findById(id).get();
                gadgetRepository.deleteById(id);
                result = new ResponseEntity<>("Record deleted : " + gadget.toString() , HttpStatus.OK);
                log.info("Gadget with {} as to be deleted is follows : {}", id , gadget.toString());
            }else{
                result = new ResponseEntity<>("Record for deletion not found" , HttpStatus.BAD_REQUEST);
                log.error("Gadget with id {} for deletion not found" , id);
            }

        }catch (Exception e){

            log.error("Some error in deleting record {}" , e.toString());
            result = new ResponseEntity<>("Some other deletion" , HttpStatus.BAD_REQUEST);

        }

        return result;
    }


    public ResponseEntity<String> getHealthOfApplication() {

        return new ResponseEntity<>("Healthy" , HttpStatus.OK);

    }
}
