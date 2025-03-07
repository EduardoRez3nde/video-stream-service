package com.project.stream.service;

import com.project.stream.service.exceptions.ResourceNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VideoStreamingService {

    private static final Long SIZE_PER_REQUEST = 5 * 1000 * 1000L; // 5MB
    public static final String VIDEO_DIRECTORY = "/app/uploads/videos";

    // https://www.zeng.dev/post/2023-http-range-and-play-mp4-in-browser/

    public byte[] streamEpisodeResponse(String videoUrl, String rangerHeader) {

        Resource videoResource = getAbsolutePath(VIDEO_DIRECTORY, videoUrl);
        long fileSize = getContentSize(videoResource);

        try (InputStream inputStream = videoResource.getInputStream()) {
            if (rangerHeader == null)
                return createPartialResponse(0, Math.min(SIZE_PER_REQUEST, fileSize), fileSize, inputStream);

            Map<String, Long> rangers = getRangeStartAndEnd(rangerHeader, fileSize);
            return createPartialResponse(rangers.get("start"), rangers.get("end"), fileSize, inputStream);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] createPartialResponse(long start, long end, long fileSize, InputStream inputStream) {
        try {
            long contentLength = end - start + 1;
            inputStream.skip(start);
            byte[] data = new byte[(int) contentLength];
            int byteRead = inputStream.read(data, 0, (int) contentLength);
            if (byteRead != contentLength)
                throw new IOException("error");
            return data;
        } catch (IOException e) {
            throw new ResourceNotFoundException("Resource Not Found");
        }
    }

    public Resource getAbsolutePath(String path, String videoUrl) {
        try {
            Path pathFile = Path.of(path, videoUrl).normalize().toAbsolutePath();
            return new UrlResource(pathFile.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getContentSize(Resource resource) {
        try {
            return resource.contentLength();
        } catch (IOException e) {
            throw new RuntimeException("Failed to determine resource size", e);
        }
    }

    public long getContentSize(String path, String contentPath) {
        Resource resource = getAbsolutePath(path, contentPath);
        return getContentSize(resource);
    }

    public Map<String, Long> getRangeStartAndEnd(String rangerHeader, long fileSize) {

        Map<String, Long> rangers = new HashMap<>(2);

        List<HttpRange> ranges = HttpRange.parseRanges(rangerHeader);
        if (ranges.isEmpty())
            throw new IllegalArgumentException("error ranges");

        HttpRange range = ranges.getFirst();
        rangers.put("start", range.getRangeStart(fileSize));
        rangers.put("end", Math.min(rangers.get("start") + SIZE_PER_REQUEST - 1, fileSize - 1));

        return rangers;
    }
}
