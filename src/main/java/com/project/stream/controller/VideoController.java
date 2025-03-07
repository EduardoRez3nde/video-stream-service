package com.project.stream.controller;

import com.project.stream.service.VideoStreamingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoStreamingService videoStreamingService;

    public VideoController(VideoStreamingService videoStreamingService) {
        this.videoStreamingService = videoStreamingService;
    }

    @GetMapping("/stream")
    public ResponseEntity<byte[]> streamEpisodeResponse(
            @RequestParam(value = "videoUrl", required = false) String videoUrl,
            @RequestHeader(value = "range", required = false) String rangeHeader) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "video/mp4");
        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");

        byte[] content = videoStreamingService.streamEpisodeResponse(videoUrl, rangeHeader);

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {

            long fileSize = videoStreamingService.getContentSize(VideoStreamingService.VIDEO_DIRECTORY, videoUrl);
            Map<String, Long> rangers = videoStreamingService.getRangeStartAndEnd(rangeHeader, fileSize);
            headers.add(HttpHeaders.CONTENT_RANGE, String.format("bytes %d-%d/%d",
                    rangers.get("start"), rangers.get("end"), fileSize));
            headers.setContentLength(content.length);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).headers(headers).body(content);
        }
        headers.setContentLength(content.length);
        return ResponseEntity.ok().headers(headers).body(content);
    }
}