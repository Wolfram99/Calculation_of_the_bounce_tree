package com.diplom.app.Controllers;

import com.diplom.app.Entity.Refusal;
import com.diplom.app.Service.MappingXmlToCollections;
import com.diplom.app.Service.RefusalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class RestControllerApi {

    private final RefusalService refusalService;

    @GetMapping()
    public List<Refusal> getAll(){
        return refusalService.findAll();
    }

    @GetMapping("/{id}")
    public Refusal getById(@PathVariable("id") Long id){
        return refusalService.findById(id);
    }

    @PostMapping()
    @ResponseBody
    public String getResult(@RequestBody String str) throws ParserConfigurationException, IOException, SAXException {
        MappingXmlToCollections mapper = new MappingXmlToCollections();
        System.out.println(mapper.getGraph(str).sum().toString());
        return mapper.getGraph(str).sum().toString();
    }

}


//    @PostMapping()
//    public ResponseEntity<Transport> create(@RequestBody TransportDTO dto){
//        return new ResponseEntity<>(transpotService.create(dto), HttpStatus.OK);
//    }
//
//    @GetMapping()
//    public ResponseEntity<List<Transport>> readAll(){
//        return new ResponseEntity<>(transpotService.readAll(), HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Transport> readById(@PathVariable("id") Long id){
//        return new ResponseEntity<>(transpotService.readById(id), HttpStatus.OK);
//    }
//
//    @PutMapping()
//    public ResponseEntity<Transport> update(@RequestBody Transport transport){
//        return new ResponseEntity<>(transpotService.update(transport), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public HttpStatus deleteById(@PathVariable("id") Long id){
//        transpotService.deleteById(id);
//        return HttpStatus.OK;
//    }
//
//    @PatchMapping("/chlen")
//    public String hui(){
//        return "соси мой хуй 152 раза!!!!!!!!!!!!";
//
//    }