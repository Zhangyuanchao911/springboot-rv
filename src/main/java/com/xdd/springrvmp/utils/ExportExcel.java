package com.xdd.springrvmp.utils;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author 91145
 * @date Excel导出工具类
 */
public class ExportExcel {


    /**
     * 导出excel工具类
     *
     * @param templatePath : 模板文件路径
     * @param fileName     : 导出的文件名称
     * @param dataMap      : 要渲染的数据
     */
    public static void downloadExport(String templatePath, String fileName, Map<String, Object> dataMap) {
        // 读取模板
        try (InputStream in = ExportExcel.class.getClassLoader().getResourceAsStream(templatePath)) {
            // 定义导入内容
            Context context = new Context(dataMap);
            System.out.println("得到input" + in.toString());
            // 执行导出方法
            JxlsHelper.getInstance().processTemplate(in, createDownloadOutputStream(fileName), context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从上下文中获取http响应并设置下载文件名称
     *
     * @param name : 下载文件名称
     */
    public static OutputStream createDownloadOutputStream(String name) throws IOException {

        ServletRequestAttributes requestAttributes;
        HttpServletResponse response;
        if ((requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes()) == null || (response = requestAttributes.getResponse()) == null) {
            throw new RuntimeException("不在请求上下文中");
        }

        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");

        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(name, "UTF-8"));
        } catch (Exception ignore) {
        }
        response.setStatus(200);

        return response.getOutputStream();

    }
}
