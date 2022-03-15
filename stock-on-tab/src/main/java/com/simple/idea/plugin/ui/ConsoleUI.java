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

    private DefaultTableModel defaultTableModel = new DefaultTableModel(new Object[][]{}, new String[]{"股票", "代码", "最新", "涨跌", "涨幅"});

    public ConsoleUI() {
        // 初始数据
        table.setModel(defaultTableModel);
        addRows(DataSetting.getInstance().getGids());

        // 添加事件
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                Object value = table.getValueAt(row, 1);
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

    public void addRows(List<String> gids) {
        // 查询
        List<Data> dataList = stock.queryPresetStockData(gids);

        // 清空
        int rowCount = defaultTableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            defaultTableModel.removeRow(0);
        }

        // 添加
        for (Data data : dataList) {
            defaultTableModel.addRow(new String[]{data.getName(), data.getGid(), data.getNowPri(), data.getIncrease(), data.getIncrePer()});
            table.setModel(defaultTableModel);
        }
    }
}
