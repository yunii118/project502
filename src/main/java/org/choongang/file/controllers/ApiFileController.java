package org.choongang.file.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionRestProcessor;
import org.choongang.commons.rests.JSONData;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileDeleteService;
import org.choongang.file.service.FileUploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class ApiFileController implements ExceptionRestProcessor {
    private final FileUploadService uploadService;
    private final FileDeleteService fileDeleteService;
    @PostMapping
    public JSONData<List<FileInfo>> upload(@RequestParam("file") MultipartFile[] files,
                           @RequestParam(name = "gid", required = false) String gid,
                           @RequestParam(name = "location", required = false) String location,
                                           @RequestParam(value = "imageOnly", required = false) boolean imgageOnly){

        List<FileInfo> uploadedFiles = uploadService.upload(files, gid, location, imgageOnly);
        return new JSONData<>(uploadedFiles);
    }

    @GetMapping("/{seq}")
    public void delete(@PathVariable("seq") Long seq){
        fileDeleteService.delete(seq);

    }

}
