package com.example.catdemo;

import com.example.catdemo.models.OutgoingCatPictureDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatController {

    private CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("cat")
    public ResponseEntity<OutgoingCatPictureDTO> getCatPicture() {
        var catPicture = this.catService.getCatPicture();

        return ResponseEntity.ok(catPicture);
    }
}
