package com.ODIN.HUMMVEE.Controller;

import com.ODIN.HUMMVEE.Entities.Gadget;
import com.ODIN.HUMMVEE.Service.GadgetCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Gadget")
public class GadgetController {

    @Autowired
    GadgetCRUDService gadgetCRUDService;

    @GetMapping("/getAllGadgets")
    ResponseEntity<List<Gadget>>getAllGadgets()
    {
        return gadgetCRUDService.getAllGadgets();
    }

    @GetMapping("/getGadgetId/{id}")
    ResponseEntity<Gadget>getGadgetById(@PathVariable long id)
    {
        return gadgetCRUDService.getGadgetById(id);
    }

    @PostMapping("/saveGadget")
    ResponseEntity<Gadget>createRecord(@RequestBody Gadget gadget)
    {
        return gadgetCRUDService.createRecord(gadget);
    }

    @PutMapping("/updateGadget/{id}")
    ResponseEntity<Gadget>updateRecord(@PathVariable long id , @RequestBody Gadget gadget)
    {
        return gadgetCRUDService.updateRecord(id , gadget);
    }

    @DeleteMapping("/deleteGadgetRecord/{id}")
    ResponseEntity<String>deleteRecord(@PathVariable long id){
        return gadgetCRUDService.deleteRecord(id);
    }

    @GetMapping("/getHealthOfApplication")
    ResponseEntity<String>getHealthOfApplication(){
        return gadgetCRUDService.getHealthOfApplication();
    }

}
