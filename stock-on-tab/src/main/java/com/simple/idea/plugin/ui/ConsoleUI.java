package com.simple.idea.plugin.ui;

import com.simple.idea.plugin.domain.model.vo.Data;
import com.simple.idea.plugin.domain.model.vo.GoPicture;
import com.simple.idea.plugin.domain.service.IStock;
import com.simple.idea.plugin.domain.service.impl.StockImpl;
import com.simple.idea.plugin.infrastructure.DataSetting;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-03-15 18:59
 **/
public class ConsoleUI {

    private JTabbedPane tabbedPane1;
    private JPanel one;
    private JTable table;
    private JPanel two;
    private JLabel picMin;
    private JLabel picDay;

    /**
     * 查询数据服务
     */
    private IStock stock = new StockImpl();

    /**
     * 构建表格的表头
     */
    private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"股票", "代码", "最新", "涨跌", "涨幅"});

    public ConsoleUI() {
        // 初始数据
        table.setModel(defaultTableModel);
        addRows(DataSetting.getInstance().getGids());

        // 这里是对表格添加监听事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 选中对应的行
                int row = table.getSelectedRow();
                // 拿到表格第一列的值，也是股票gid，然后找到对应的K线
                Object value = table.getValueAt(row, 1);
                // 这里就是去获取k线
                GoPicture goPicture = stock.queryGidGoPicture(value.toString());
                try {
                    // 分钟K线
                    picMin.setSize(545, 300);
                    picMin.setIcon(new ImageIcon(new URL(goPicture.getMinurl())));

                    // 当日K线
                    picDay.setSize(545, 300);
                    picDay.setIcon(new ImageIcon(new URL(goPicture.getDayurl())));
                } catch (MalformedURLException m) {
                    m.printStackTrace();
                }
            }
        });
    }

    public JTabbedPane getPanel() {
        return tabbedPane1;
    }

    /**
     * 这里是填充表格数据
     *
     * @param gids
     */
    public void addRows(List<String> gids) {
        // 查询股票的数据
        List<Data> dataList = stock.queryPresetStockData(gids);

        // 清空之前的数据
        int rowCount = defaultTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            defaultTableModel.removeRow(0);
        }

        // 添加最新的数据
        for (Data data : dataList) {
            defaultTableModel.addRow(new String[]{data.getName(), data.getGid(), data.getNowPri(), data.getIncrease(), data.getIncrePer()});
            table.setModel(defaultTableModel);
        }
    }
}
