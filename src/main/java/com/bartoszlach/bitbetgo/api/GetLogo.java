/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bartoszlach.bitbetgo.api;

import java.io.File;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author bartoszlach
 */
@Controller
public class GetLogo {

    @RequestMapping(value = "/api/getLogo/{fileName:.+}") //bo z kropka nie dzialalo
    @ResponseBody
    public FileSystemResource downloadFile(@PathVariable String fileName) {
        return new FileSystemResource(new File("logos/" + fileName));
    }

}
