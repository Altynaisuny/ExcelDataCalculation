package com.example.excel.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ImportExcel {

    Map<String,List<Map>> getBankListByExcel(InputStream in, String fileName)throws Exception;
}
