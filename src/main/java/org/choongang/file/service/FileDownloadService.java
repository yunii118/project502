package org.choongang.file.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final FileInfoService infoService;
    private final HttpServletResponse response;

    public void download(Long seq){
        FileInfo data = infoService.get(seq);
        String filePath = data.getFilePath();
        // 파일명 2바이트 인코딩으로 변경(윈도우즈에서 한글 깨짐 방지)
        String fileName = null;
        try {
            fileName = new String(data.getFileName().getBytes(), "ISO8859_1");
        } catch (UnsupportedEncodingException e) {}

        File file = new File(filePath);

        try(FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis)){

            OutputStream out = response.getOutputStream(); // 응답 바디에 출력, 1Byte 단위
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-Type", "application/octet-stream"); // 파일 종류가 어떤게 될 지 모름 - 범용적으로
            response.setIntHeader("Expires", 0); // 만료시간을 없애줌, 다운 중간에 연결 끊기지 않도록
            response.setHeader("Cache-Control", "must-revalidate"); // 동일한 파일명이라도 문제없이 다운받을 수 있게
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Length", String.valueOf(file.length()));

            while(bis.available() > 0){
                out.write(bis.read());
            }

            out.flush();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
