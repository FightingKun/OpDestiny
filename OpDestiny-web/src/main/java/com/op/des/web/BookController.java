package com.op.des.web;

import com.google.common.collect.Lists;
import com.op.des.web.dao.mapper.BookInfoPOMapper;
import com.op.des.web.dao.po.BookInfoPO;
import com.op.des.web.dao.po.BookInfoPOCriteria;
import com.op.des.web.vo.BookInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

/**
 * 命理典籍
 */
@RestController()
@Slf4j
public class BookController {

    public static final String FILE_DIR_HOME = "/opt/op/file/";
//    public static final String FILE_DIR_HOME = "/Users/kun/work/ops/";
    public static final String BOOK_BASE_URL = "152.136.43.219:8000/op/book/";
    @Autowired
    BookInfoPOMapper bookInfoPOMapper;

    @RequestMapping("/op/des/book/list")
    public List<BookInfoVO> listBooks() {
        BookInfoPOCriteria example = new BookInfoPOCriteria();

        List<BookInfoPO> bookInfoPOS = bookInfoPOMapper.selectByExample(example);
        return Optional.ofNullable(bookInfoPOS).orElse(Lists.newArrayList()).stream().map(item -> {
            BookInfoVO bookInfoVO = new BookInfoVO();
            bookInfoVO.setName(item.getBookName());
            bookInfoVO.setDetail(item.getBookDetail());
            bookInfoVO.setIcon(item.getBookLogo());
            bookInfoVO.setId(item.getId());
            bookInfoVO.setBookPath(item.getBookPath());
            return bookInfoVO;
        }).collect(Collectors.toList());
    }

    @PostMapping("/op/des/book/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file,
                                             @RequestParam(value = "detail") String detail,
                                             @RequestParam(value = "image") MultipartFile image) {
        if (file.isEmpty() || image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("文件为空");
        }
        try {
            File uploadDir = new File(FILE_DIR_HOME);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            //保存文件
            String filePath = FILE_DIR_HOME + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            String imagePath = FILE_DIR_HOME + image.getOriginalFilename();
            image.transferTo(new File(imagePath));

            //文件信息落库
            BookInfoPO bookInfoPO = new BookInfoPO();
            bookInfoPO.setBookDetail(detail);
            bookInfoPO.setBookLogo(BOOK_BASE_URL + image.getOriginalFilename());
            bookInfoPO.setBookName(file.getOriginalFilename());
            bookInfoPO.setBookPath(BOOK_BASE_URL + file.getOriginalFilename());
            int bookId = bookInfoPOMapper.insertSelective(bookInfoPO);
            return ResponseEntity.status(HttpStatus.OK).body("文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body("文件上传失败");
        }
    }
//
//    @RequestMapping("/read")
//    public List<BookInfoVO> read(@RequestParam("id") Long id) {
//        if (id == null || id <= 0) {
//            return Lists.newArrayList();
//        }
//        BookInfoPO bookInfoPO = bookInfoPOMapper.selectByPrimaryKey(id);
//        if (bookInfoPO == null) {
//            return Lists.newArrayList();
//        }
//
//        //书的内容
//        try {
//            String bookPathStr = bookInfoPO.getBookPath();
//            Path bookPath = Paths.get(FILE_DIR_HOME + bookPathStr);
//            String content = new String(Files.readAllBytes(bookPath));
//
//            String bookLogoStr = bookInfoPO.getBookLogo();
//            Path bookLogoPath = Paths.get(FILE_DIR_HOME + bookLogoStr);
//            byte[] imageBytes = Files.readAllBytes(bookLogoPath);
//
//
//            BookInfoVO bookInfoVO = new BookInfoVO();
//            bookInfoVO.setContent(content);
//            bookInfoVO.setName(bookInfoPO.getBookName());
//            bookInfoVO.setDetail(bookInfoPO.getBookDetail());
//            bookInfoVO.setImageLogo(imageBytes);
//            return Lists.newArrayList(bookInfoVO);
//        } catch (IOException e) {
//            return Lists.newArrayList();
//        }
//    }
}
