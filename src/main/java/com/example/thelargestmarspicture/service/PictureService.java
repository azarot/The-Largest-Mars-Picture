package com.example.thelargestmarspicture.service;

import java.util.Comparator;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PictureService {
    public final RestTemplate template;

    public byte[] getLargestPicture(int sol, String camera) {
        return template.getForObject(getUrl(sol, camera), JsonNode.class)
          .get("photos")
          .findValuesAsText("img_src")
          .parallelStream()
          .map(u -> new PictureSize(u, getContentLength(u)))
          .max(Comparator.comparing(PictureSize::size))
          .map(PictureSize::url)
          .map(this::getPictureBytes)
          .orElseThrow(IllegalStateException::new);

    }

    private long getContentLength(String photoUrl) {
        return template.getForEntity(photoUrl, null).getHeaders().getContentLength();
    }

    private static String getUrl(int sol, String camera) {
        return UriComponentsBuilder.fromHttpUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos")
          .queryParam("api_key", "DEMO_KEY")
          .queryParam("sol", sol)
          .queryParamIfPresent("camera", Optional.ofNullable(camera))
          .toUriString();
    }

    private byte[] getPictureBytes(String url) {
        return template.getForObject(url, byte[].class);
    }

}
record PictureSize(String url, long size){}
