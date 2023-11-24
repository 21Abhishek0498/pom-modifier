package com.falcon.pommodifierservice.rest;

import com.falcon.pommodifierservice.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/dependency")
public class DependenciesConfigController {

    @Autowired
    private ServiceImpl serviceImpl;

    @GetMapping(value = "/pom-path/{path}")
    public ResponseEntity<Map<String, String>> getDependencies(@PathVariable(name = "path", required = true) String path) throws Exception {
       String pathToPom = "C:\\Users\\agorakh\\Documents\\git code\\demo-spring-petclinic\\pom.xml";
        Map<String, String> dependencies = new HashMap<>();
        dependencies.put("path", pathToPom);
        dependencies.put("Project",serviceImpl.readPomFile(pathToPom));
        dependencies.put("Java Version", serviceImpl.getJavaVersion(pathToPom));
        dependencies.put("Spring Boot Version",serviceImpl.getSpringBootVersion(pathToPom));
        return new ResponseEntity<>(dependencies, HttpStatus.OK);
    }
}


