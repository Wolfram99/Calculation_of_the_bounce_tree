package com.diplom.app.Controllers;

import com.diplom.app.Entity.Refusal;
import com.diplom.app.Service.MappingXmlToCollections;
import com.diplom.app.Service.RefusalService;
import lombok.AllArgsConstructor;
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

    @PostMapping()
    @ResponseBody
    public String getResult(@RequestBody String str) throws ParserConfigurationException, IOException, SAXException {
        MappingXmlToCollections mapper = new MappingXmlToCollections();
        return mapper.getGraph(str).sum();
    }

}