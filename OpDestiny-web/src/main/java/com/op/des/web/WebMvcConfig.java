package com.op.des.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc 配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 配置静态资源访问映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseUlr = "file:/opt/op/file/";
//#    base-path: /Users/kun/work/static
        //本地文件上传路径
        registry.addResourceHandler("/op/book/**") // 自定义URL访问前缀，和file配置一致
                .addResourceLocations(baseUlr);
    }

}

