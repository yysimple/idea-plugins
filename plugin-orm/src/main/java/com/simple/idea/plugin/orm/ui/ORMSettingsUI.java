package com.simple.idea.plugin.orm.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import com.simple.idea.plugin.orm.domain.model.vo.CodeGenContextVO;
import com.simple.idea.plugin.orm.domain.model.vo.ORMConfigVO;
import com.simple.idea.plugin.orm.domain.service.IProjectGenerator;
import com.simple.idea.plugin.orm.infrastructure.data.DataSetting;
import com.simple.idea.plugin.orm.infrastructure.po.Table;
import com.simple.idea.plugin.orm.infrastructure.utils.DBHelper;
import com.simple.idea.plugin.orm.module.FileChooserComponent;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述: UI布局，我们在进行各种设置的时候的一种布局入口
 *
 * @author: WuChengXing
 * @create: 2022-03-19 10:45
 **/
public class ORMSettingsUI implements Configurable {
    /**
     * 各种UI面板
     */
    private JPanel main;
    private JTextField classpath;
    private JPasswordField password;
    private JTextField projectName;
    private JTextField user;
    private JTextField database;
    private JTextField host;
    private JTextField port;
    private JTextField poPath;
    private JTextField daoPath;
    private JButton selectButton;
    private JButton poButton;
    private JButton daoButton;
    private JButton testButton;
    private JTextField xmlPath;
    private JButton xmlButton;
    private JTable table1;

    /**
     * 我们自己的一些配置信息
     */
    private ORMConfigVO config;

    /**
     * 这里包含一个项目的基本信息
     */
    private Project project;

    /**
     * 我们自己代码生成逻辑
     */
    private IProjectGenerator projectGenerator;

    public ORMSettingsUI(Project project, IProjectGenerator projectGenerator) {
        this.project = project;
        // 相当于注入文件生成的bean
        this.projectGenerator = projectGenerator;
        // 这里是拿到配置信息（这里是通过在idea的缓存中拿到的，第一次初始化插件是没有的）
        config = DataSetting.getInstance(null != project ? project : ProjectManager.getInstance().getDefaultProject()).getORMConfig();
        // 下面开始就是拿到上次初始化过后的一些值重新赋值
        this.projectName.setText(project.getName());
        this.classpath.setText(project.getBasePath());

        this.database.setText(config.getDatabase());
        this.host.setText(config.getHost());
        this.port.setText(config.getPort());
        this.poPath.setText(config.getPoPath());
        this.daoPath.setText(config.getDaoPath());
        this.xmlPath.setText(config.getXmlPath());

        // 选择PO生成目录
        this.poButton.addActionListener(e -> {
            FileChooserComponent component = FileChooserComponent.getInstance(project);
            VirtualFile baseDir = project.getBaseDir();
            VirtualFile virtualFile = component.showFolderSelectionDialog("选择PO生成目录", baseDir, baseDir);
            if (null != virtualFile) {
                ORMSettingsUI.this.poPath.setText(virtualFile.getPath());
            }
        });

        // 选择DAO生成目录
        this.daoButton.addActionListener(e -> {
            FileChooserComponent component = FileChooserComponent.getInstance(project);
            VirtualFile baseDir = project.getBaseDir();
            VirtualFile virtualFile = component.showFolderSelectionDialog("选择DAO生成目录", baseDir, baseDir);
            if (null != virtualFile) {
                ORMSettingsUI.this.daoPath.setText(virtualFile.getPath());
            }
        });

        // 选择XMl生成目录
        this.xmlButton.addActionListener(e -> {
            FileChooserComponent component = FileChooserComponent.getInstance(project);
            VirtualFile baseDir = project.getBaseDir();
            VirtualFile virtualFile = component.showFolderSelectionDialog("选择XML生成目录", baseDir, baseDir);
            if (null != virtualFile) {
                ORMSettingsUI.this.xmlPath.setText(virtualFile.getPath());
            }
        });

        // 查询数据库表列表
        this.selectButton.addActionListener(e -> {
            try {
                DBHelper dbHelper = new DBHelper(this.host.getText(), Integer.parseInt(this.port.getText()), this.user.getText(), this.password.getText(), this.database.getText());
                List<String> tableList = dbHelper.getAllTableName(this.database.getText());

                String[] title = {"", "表名"};
                Object[][] data = new Object[tableList.size()][2];
                for (int i = 0; i < tableList.size(); i++) {
                    data[i][1] = tableList.get(i);
                }

                table1.setModel(new DefaultTableModel(data, title));
                TableColumn tc = table1.getColumnModel().getColumn(0);
                tc.setCellEditor(new DefaultCellEditor(new JCheckBox()));
                tc.setCellEditor(table1.getDefaultEditor(Boolean.class));
                tc.setCellRenderer(table1.getDefaultRenderer(Boolean.class));
                tc.setMaxWidth(100);
            } catch (Exception exception) {
                Messages.showWarningDialog(project, "数据库连接错误,请检查配置.", "Warning");
            }
        });

        // 给表添加事件
        this.table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (1 == e.getClickCount()) {
                    int rowIdx = table1.rowAtPoint(e.getPoint());
                    Boolean flag = (Boolean) table1.getValueAt(rowIdx, 0);
                    Set<String> tableNames = ORMSettingsUI.this.config.getTableNames();
                    if (null != flag && flag) {
                        tableNames.add(table1.getValueAt(rowIdx, 1).toString());
                    } else {
                        tableNames.remove(table1.getValueAt(rowIdx, 1).toString());
                    }
                }
            }
        });

        // 测试数据库链接
        this.testButton.addActionListener(e -> {
            try {
                DBHelper dbHelper = new DBHelper(this.host.getText(), Integer.parseInt(this.port.getText()), this.user.getText(), this.password.getText(), this.database.getText());
                String mysqlVersion = dbHelper.testDatabase();
                Messages.showInfoMessage(project, "Connection successful! \r\nMySQL version : " + mysqlVersion, "OK");
            } catch (Exception exception) {
                Messages.showWarningDialog(project, "数据库连接错误,请检查配置.", "Warning");
            }
        });

    }

    @Override
    public @Nullable JComponent createComponent() {
        return main;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() {
        // 获取配置
        config.setUser(this.user.getText());
        config.setPassword(new String(this.password.getPassword()));
        config.setProjectName(this.projectName.getText());
        config.setClasspath(this.classpath.getText());
        config.setDatabase(this.database.getText());
        config.setHost(this.host.getText());
        config.setPort(this.port.getText() != null ? this.port.getText() : "3306");
        config.setPoPath(this.poPath.getText());
        config.setDaoPath(this.daoPath.getText());
        config.setXmlPath(this.xmlPath.getText());

        // 链接DB
        DBHelper dbHelper = new DBHelper(config.getHost(), Integer.parseInt(config.getPort()), config.getUser(), config.getPassword(), config.getDatabase());

        // 组装代码生产上下文
        CodeGenContextVO codeGenContext = new CodeGenContextVO();
        codeGenContext.setModelPackage(config.getPoPath() + "/po/");
        codeGenContext.setDaoPackage(config.getDaoPath() + "/mapper/");
        codeGenContext.setMapperDir(config.getXmlPath() + "/mapper/");
        List<Table> tables = new ArrayList<>();
        Set<String> tableNames = config.getTableNames();
        for (String tableName : tableNames) {
            tables.add(dbHelper.getTable(tableName));
        }
        codeGenContext.setTables(tables);

        // 生成代码
        projectGenerator.generation(project, codeGenContext);
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return "Config";
    }

}
