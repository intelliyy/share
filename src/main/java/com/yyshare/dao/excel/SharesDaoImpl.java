package com.yyshare.dao.excel;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.yyshare.constants.Constants;
import com.yyshare.dao.ISharesDao;
import com.yyshare.entity.Share;
import com.yyshare.exception.ShareException;
import com.yyshare.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class SharesDaoImpl implements ISharesDao {

    @Override
    public List<Share> findByTime(Date start, Date end, String type) {
        List<Share> shares = new ArrayList<>();
        String dateFormat = getDateFormat(type);
        for (; DateUtil.compare(start, end) < 0; start = DateUtil.offset(start, DateField.DAY_OF_YEAR, 1)) {
            String path = Constants.EXCEL_DATA_PATH + DateUtil.format(start, "yyyyMMdd") + "-" + type + ".xlsx";
            File f = new File(path);
            if (!f.exists()) {
                continue;
            }
            List<String>[] ls;
            try {
                ls = ExcelUtil.getData(path, dateFormat, "时间", "收盘", "ema12", "ema26", "diff", "dea", "macd");
            } catch (IOException e) {
                log.info("读取数据文件异常", e);
                throw new ShareException("读取数据文件异常");
            }
            for (int i = 0; i < ls[0].size(); i++) {
                shares.add(new Share(Double.parseDouble(ls[1].get(i)), Double.parseDouble(ls[6].get(i)), Double.parseDouble(ls[4].get(i)),
                        Double.parseDouble(ls[5].get(i)), Double.parseDouble(ls[2].get(i)), Double.parseDouble(ls[3].get(i)), type, DateUtil.parse(ls[0].get(i), dateFormat)));
            }
        }
        return shares;
    }

    @Override
    public void save(List<Share> shares) {
        for (Share share : shares) {
            String path = Constants.EXCEL_DATA_PATH + DateUtil.format(share.getTime(), "yyyyMMdd") + "-" + share.getType() + ".xlsx";
            File file = new File(path);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    log.info("创建文件异常", e);
                    throw new ShareException("保存数据异常");
                }
            }
            Workbook book;
            FileInputStream input = null;
            try {
                book = ExcelUtil.createWorkBook(input, file.getName());

            } catch (Exception e) {
                log.info("保存数据异常", e);
                throw new ShareException("保存数据异常");
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        log.info("文件关闭异常", e);
                    }
                }
            }
        }
    }

    /**
     * 获取excel的时间转换格式
     * @param type
     * @return
     */
    private String getDateFormat(String type) {
        switch (type) {
            case Constants.TYPE_101 : return "yyyy-MM-dd";
            default : return "yyyy-MM-dd HH:mm";
        }
    }
}
