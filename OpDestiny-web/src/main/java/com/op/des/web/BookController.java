package com.op.des.web;

import com.google.common.collect.Lists;
import com.op.des.web.dao.mapper.BookInfoPOMapper;
import com.op.des.web.dao.po.BookInfoPO;
import com.op.des.web.dao.po.BookInfoPOCriteria;
import com.op.des.web.vo.BookInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/op/des/book")
public class BookController {

    public static final String FILE_DIR_HOME = "/opt/op/file/";
    @Autowired
    BookInfoPOMapper bookInfoPOMapper;

    @RequestMapping("/list")
    public List<BookInfoVO> listBooks() {
        BookInfoPOCriteria example = new BookInfoPOCriteria();

        List<BookInfoPO> bookInfoPOS = bookInfoPOMapper.selectByExample(example);
        return Optional.ofNullable(bookInfoPOS).orElse(Lists.newArrayList()).stream().map(item -> {
            BookInfoVO bookInfoVO = new BookInfoVO();
            bookInfoVO.setName(item.getBookName());
            bookInfoVO.setDetail(item.getBookDetail());
            bookInfoVO.setIcon(item.getBookLogo());
            bookInfoVO.setId(item.getId());
            return bookInfoVO;
        }).collect(Collectors.toList());
    }

    @RequestMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("detail") String detail,
                                             @RequestParam("image") MultipartFile image) {
        if (file.isEmpty() || image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("文件为空");
        }
        try {
            File uploadDir = new File(FILE_DIR_HOME);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String filePath = FILE_DIR_HOME + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            String imagePath = FILE_DIR_HOME + image.getOriginalFilename();
            file.transferTo(new File(imagePath));

            BookInfoPO bookInfoPO = new BookInfoPO();
            bookInfoPO.setBookDetail(detail);
            bookInfoPO.setBookLogo(imagePath);
            bookInfoPO.setBookName(file.getName());
            bookInfoPO.setBookPath(filePath);
            bookInfoPOMapper.insertSelective(bookInfoPO);
            return ResponseEntity.status(HttpStatus.OK).body("文件上传成功");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body("文件上传失败");
        }
    }

    @RequestMapping("/read")
    public List<BookInfoVO> read(@RequestParam("id") Long id) {
        if (id == null || id <= 0) {
            return Lists.newArrayList();
        }
        BookInfoPO bookInfoPO = bookInfoPOMapper.selectByPrimaryKey(id);
        if (bookInfoPO == null) {
            return Lists.newArrayList();
        }

        //书的内容
        try {
            String bookPathStr = bookInfoPO.getBookPath();
            Path bookPath = Paths.get(FILE_DIR_HOME + bookPathStr);
            String content = new String(Files.readAllBytes(bookPath));

            String bookLogoStr = bookInfoPO.getBookLogo();
            Path bookLogoPath = Paths.get(FILE_DIR_HOME + bookLogoStr);
            byte[] imageBytes = Files.readAllBytes(bookLogoPath);


            BookInfoVO bookInfoVO = new BookInfoVO();
            bookInfoVO.setContent(content);
            bookInfoVO.setName(bookInfoPO.getBookName());
            bookInfoVO.setDetail(bookInfoPO.getBookDetail());
            bookInfoVO.setImageLogo(imageBytes);
            return Lists.newArrayList(bookInfoVO);
        } catch (IOException e) {
            return Lists.newArrayList();
        }
    }
}
