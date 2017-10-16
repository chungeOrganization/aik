package com.aik.controller;

import com.aik.model.AikCommonProblem;
import com.aik.response.ResultResp;
import com.aik.service.CommonProblemService;
import com.aik.util.AikFileUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.Calendar;

/**
 * Description: 常见问题
 * Created by as on 2017/10/15.
 */
@RestController
@RequestMapping("/commonProblem")
public class CommonProblemController {

    private static final Logger logger = LoggerFactory.getLogger(CommonProblemController.class);

    @Value("${file.upload-root-uri}")
    private String uploadRootUri;

    private CommonProblemService commonProblemService;

    @Autowired
    public void setCommonProblemService(CommonProblemService commonProblemService) {
        this.commonProblemService = commonProblemService;
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("commonProblem/commonProblemIndex");
    }

    @RequestMapping("/goto/{num}/{size}")
    public ModelAndView queryPage(@PathVariable Integer num, @PathVariable Integer size, AikCommonProblem commonProblem) {
        ModelAndView modelAndView = new ModelAndView("commonProblem/commonProblemList");
        Page<AikCommonProblem> commonProblemPageInfo = new Page<>();
        try {
            PageHelper.startPage(num, size);
            commonProblemPageInfo = commonProblemService.findPage(commonProblem);
        } catch (Exception e) {
            // ignore
        }

        modelAndView.addObject("pageInfo", new PageInfo<>(commonProblemPageInfo));
        return modelAndView;
    }

    @RequestMapping("/view/{id}/{opt}")
    public ModelAndView view(@PathVariable Integer id, @PathVariable String opt) {
        ModelAndView modelAndView = new ModelAndView("commonProblem/commonProblemView");

        AikCommonProblem commonProblem = null;
        try {
            commonProblem = commonProblemService.findCommonProblem(id);
        } catch (Exception e) {
            // ignore
        }

        modelAndView.addObject("opt", opt);
        modelAndView.addObject("commonProblem", commonProblem);
        return modelAndView;
    }

    @RequestMapping("/edit")
    public ResultResp edit(AikCommonProblem commonProblem, @RequestParam MultipartFile file) {
        ResultResp resultResp = new ResultResp();

        try {
            if (null != file && null != file.getInputStream()) {
                String imageName = Calendar.getInstance().getTimeInMillis()
                        + file.getOriginalFilename();

                String fileUri = "commonProblem" + File.separator + imageName;
                String uploadUrl = uploadRootUri + fileUri;
                AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);

                commonProblem.setFileUrl(fileUri);
            }

            if(!commonProblemService.editCommonProblem(commonProblem)) {
                resultResp.withCode(ResultResp.RESULT_FAIL).withDataKV("msg", "编辑常见问题异常");
            }
        } catch (Exception e) {
            logger.error("编辑常见问题异常：", e);
            resultResp.withCode(ResultResp.RESULT_FAIL).withDataKV("msg", "编辑常见问题异常");
        }

        return resultResp;
    }

    @RequestMapping("/gotoAddPage")
    public ModelAndView gotoAddPage() {
        return new ModelAndView("commonProblem/commonProblemAdd");
    }

    @RequestMapping("/add")
    public ResultResp add(AikCommonProblem commonProblem, @RequestParam MultipartFile file) {
        ResultResp resultResp = new ResultResp();

        try {
            if (null != file && null != file.getInputStream()) {
                String imageName = Calendar.getInstance().getTimeInMillis()
                        + file.getOriginalFilename();

                String fileUri = "commonProblem" + File.separator + imageName;
                String uploadUrl = uploadRootUri + fileUri;
                AikFileUtils.uploadImg(file.getInputStream(), uploadUrl);

                commonProblem.setFileUrl(fileUri);
            }

            if (!commonProblemService.addCommonProblem(commonProblem)) {
                resultResp.withCode(ResultResp.RESULT_FAIL).withDataKV("msg", "新增常见问题异常");
            }
        } catch (Exception e) {
            logger.error("新增常见问题异常：", e);
            resultResp.withCode(ResultResp.RESULT_FAIL).withDataKV("msg", "新增常见问题异常");
        }

        return resultResp;
    }

    @RequestMapping("/delete/{id}")
    public ResultResp delete(@PathVariable Integer id) {
        ResultResp resultResp = new ResultResp();

        try {
            if (!commonProblemService.deleteCommonProblem(id)) {
                resultResp.withCode(ResultResp.RESULT_FAIL).withDataKV("msg", "删除常见问题异常");
            }
        } catch (Exception e) {
            logger.error("删除常见问题异常：", e);
            resultResp.withCode(ResultResp.RESULT_FAIL).withDataKV("msg", "删除常见问题异常");
        }

        return resultResp;
    }
}
