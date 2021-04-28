package com.yyshare.dao.excel;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.yyshare.constants.Constants;
import com.yyshare.dao.ISharesDao;
import com.yyshare.entity.Share;
import com.yyshare.exception.ShareException;
import com.yyshare.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.*;

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
                ls = ExcelUtil.getData(path, dateFormat, getAllTitles());
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

    /**
     * 根据type进行分类
     * @param shares
     * @return
     */
    private Collection<List<Share>> classify(List<Share> shares) {
        Map<String, List<Share>> map = new HashMap<>();
        for (Share share : shares) {
            String key = share.getType() + "-" + DateUtil.format(share.getTime(), "yyyyMMdd");
            List<Share> ls = map.get(key);
            if (ls == null) {
                ls = new ArrayList<>();
                map.put(key, ls);
            }
            ls.add(share);
        }
        return map.values();
    }

    @Override
    public void save(List<Share> shares) {
        Collection<List<Share>> cs = classify(shares);
        for (List<Share> ls : cs) {
            saveToFile(ls);
        }
    }

    private void saveToFile(List<Share> shares) {
        Share basic = shares.get(0);
        String path = Constants.EXCEL_DATA_PATH + DateUtil.format(basic.getTime(), "yyyyMMdd") + "-" + basic.getType() + ".xlsx";
        File file = new File(path);
        boolean exist = true;
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
        FileOutputStream out = null;
        try {
            Sheet sheet;
            if (!exist || file.length() == 0) {
                book = ExcelUtil.createNewWorkBook();
                sheet = book.createSheet();
                // 创建标题
                List<String> titles = Arrays.asList(getAllTitles());
                ExcelUtil.addData(sheet.createRow(0), titles);
            } else {
                input = new FileInputStream(file);
                book = ExcelUtil.createWorkBook(input, file.getName());
                sheet = book.getSheetAt(0);
            }
            int i = 1;
            for (Share share : shares) {
                List<String> data = shareToList(share);
                ExcelUtil.addData(sheet.createRow(i++), data);
            }
            out = new FileOutputStream(file);
            book.write(out);
        } catch (Exception e) {
            log.info("保存数据异常", e);
            throw new ShareException("保存数据异常");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.info("文件输入流关闭异常", e);
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    log.info("文件输出流关闭异常", e);
                }
            }
        }
    }

    private List<String> shareToList(Share share) {
        return Arrays.asList(DateUtil.format(share.getTime(), getDateFormat(share.getType())),
                NumberUtil.roundStr(share.getPrice(), 2, RoundingMode.FLOOR),
                NumberUtil.roundStr(share.getEma12(), 2, RoundingMode.FLOOR),
                NumberUtil.roundStr(share.getEma26(), 2, RoundingMode.FLOOR),
                NumberUtil.roundStr(share.getDiff(), 2, RoundingMode.FLOOR),
                NumberUtil.roundStr(share.getDea(), 2, RoundingMode.FLOOR),
                NumberUtil.roundStr(share.getMacd(), 2, RoundingMode.FLOOR));
    }

    /**
     * 获取所有标题
     * @return
     */
    private String[] getAllTitles() {
        return new String[]{"时间", "收盘", "ema12", "ema26", "diff", "dea", "macd"};
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
