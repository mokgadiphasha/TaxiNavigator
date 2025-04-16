package com.taxiapi.Controller;

import com.taxiapi.Model.TaxiRank;
import com.taxiapi.Model.TaxiRoute;
import com.taxiapi.Model.TaxiSign;
import com.taxiapi.Responses.EmptyFileResponse;
import com.taxiapi.Responses.TaxiRankResponse;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Responses.TaxiSignResponse;
import com.taxiapi.Service.AdminService.AdminServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminServiceManager service;


    @GetMapping("")
    public TaxiRoutesResponse findAllRoutes(){
        return service.findAllRoutes();
    }


    @PutMapping("")
    public void updateRoute(@Validated @RequestBody TaxiRoute route){
        service.updateRoute(route);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable Long id){
        service.deleteRoute(id);
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveRoute(@Validated @RequestBody TaxiRoute route){
        service.saveRoute(route);
    }


    @PostMapping("/routes")
    public EmptyFileResponse saveRoutes(@RequestParam("file")MultipartFile file){
        return service.saveFromCSVFile(file);
    }


    @GetMapping("/download-template")
    public ResponseEntity<Resource> getCsvTemplate(){
        try{
            InputStreamResource resource = service.returnCsvTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=template.csv");
            headers.add(HttpHeaders.CONTENT_TYPE,"text/csv");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (IOException e) {
            // use spring to control this error
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }


    }





}
