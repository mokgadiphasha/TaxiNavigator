package com.taxiapi.Controller;

import com.taxiapi.DTO.TaxiRouteDTO;
import com.taxiapi.Responses.FileResponse;
import com.taxiapi.Responses.TaxiRoutesResponse;
import com.taxiapi.Service.AdminService.AdminServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminServiceManager service;

//done
    @GetMapping("")
    public TaxiRoutesResponse findAllRoutes(){
        return service.findAllRoutes();
    }

//done
    @PutMapping("/{id}")
    public void updateRoute(@PathVariable Long id,
                            @Validated @RequestBody TaxiRouteDTO route){
        service.updateRoute(id, route);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoute(@PathVariable Long id){
        service.deleteRoute(id);
    }

//done
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveRoute(@Validated @RequestBody TaxiRouteDTO route){
        service.saveRoute(route);
    }

//done
    @PostMapping("/template")
    public FileResponse saveRoutes(@RequestParam("file")MultipartFile file){
        return service.saveFromCSVFile(file);
    }

//done
    @GetMapping("/template")
    public ResponseEntity<Resource> getCsvTemplate(){
        ByteArrayResource resource = service.returnCsvTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=template.csv");
        headers.add(HttpHeaders.CONTENT_TYPE,"text/csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .body(resource);

}}
