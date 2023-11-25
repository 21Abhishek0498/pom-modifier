package com.falcon.pommodifierservice.rest;

import com.falcon.pommodifierservice.constants.Constants;
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
    public ResponseEntity<Map<String, String>> getDependencies(@PathVariable(name = "path", required = true) String path) {
        try {
            String pathToPom = "C:\\Users\\agorakh\\Documents\\git code\\demo-spring-petclinic\\pom.xml";
            return new ResponseEntity<>(serviceImpl.getJavaVersionAndSpringVersion(pathToPom), HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("Error processing POM file: ", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/check-and-add/pom-path/{path}")
    public ResponseEntity<String> checkAndAddDependency(@PathVariable String path) {
        try {

            String pathToPom = "C:\\Users\\agorakh\\Documents\\git code\\demo-spring-petclinic\\pom.xml";

            boolean isDependencyPresent = serviceImpl.isDependencyPresent(Constants.GROUP_ID, Constants.ARTIFACT_ID,pathToPom);

            if (isDependencyPresent) {
                return new ResponseEntity<>("Dependency already present in the POM file. No changes made.", HttpStatus.OK);
            } else {
                serviceImpl.addDependencyToPom(Constants.GROUP_ID, Constants.ARTIFACT_ID, Constants.SCOPE_ID,pathToPom);
                serviceImpl.mavenBuild(pathToPom);
                return new ResponseEntity<>("Dependency added to POM file. Maven build triggered.", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return new ResponseEntity<>("Error processing request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}




