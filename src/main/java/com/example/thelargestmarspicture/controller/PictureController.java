package com.example.thelargestmarspicture.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.thelargestmarspicture.service.PictureService;

import lombok.RequiredArgsConstructor;

@RestController()
@RequiredArgsConstructor
public class PictureController {
    private final PictureService pictureService;

    @GetMapping(path = "/mars/pictures/largest", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getLargestPicture(@RequestParam int sol, @RequestParam(required = false) String camera) {
        return pictureService.getLargestPicture(sol, camera);
    }
}
