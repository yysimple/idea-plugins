package com.simple.idea.plugin.know.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.Messages;
import com.simple.idea.plugin.know.data.DataSetting;
import com.simple.idea.plugin.know.data.EnumKnowConstants;
import com.simple.idea.plugin.know.util.DBHelper;

import javax.swing.*;
import java.io.File;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-02-24 13:08
 **/
public class SettingUI {

    private JPanel main;
    private JPanel fileSetting;
    private JLabel selectDesc;
    private JTextField filePathText;
    private JButton fileSelectButton;
    private JRadioButton dbRead;
    private JRadioButton allRead;
    private JRadioButton fileRead;
    private JTextField hostTextField;
    private JTextField portTextField;
    private JTextField usernameTextField;
    private JTextField databaseTextField;
    private JTextField tableTextField;
    private JTextField columnTextField;
    private JButton testConn;
    private JPasswordField passwordTextField;
    private JTextField showInfoTextField;
    private JTextField searchPairTextField;
    private DataSetting.EnumKnowFileConfig enumKnowFileConfig;
    private DataSetting.EnumKnowOptionsConfig enumKnowOptionsConfig;
    private DataSetting.EnumKnowDataSourceConfig enumKnowDataSourceConfig;

    public SettingUI(Project project) {
        // 初始化操作，默认值加入
        initDataConfig(project);
        // 回写之前缓存配置
        showConfig();

        // 给按钮添加一个选择文件的事件
        fileSelectButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.showOpenDialog(fileSetting);
            // 这里是上传一个文件路径
            File file = fileChooser.getSelectedFile();
            // 设置到组件属性中，之后通过这个属性拿到对应的文件流
            filePathText.setText(file.getPath());
            enumKnowFileConfig.setEnumKnowFilePath(file.getPath());
        });

        // 测试数据库链接
        testConn.addActionListener(e -> {
            try {
                DBHelper dbHelper = new DBHelper(this.hostTextField.getText(), Integer.parseInt(this.portTextField.getText()),
                        this.usernameTextField.getText(), this.passwordTextField.getText(), this.databaseTextField.getText());
                String mysqlVersion = dbHelper.testDatabase();
                Messages.showInfoMessage(project, "Connection successful! \r\nMySQL version : " + mysqlVersion, "OK");
            } catch (Exception exception) {
                Messages.showWarningDialog(project, "数据库连接错误,请检查配置.", "Warning");
            }
        });
    }

    private void showConfig() {
        // 回写选择文件路径
        showFilePath();
        // 回写选项
        showOptions();
        // 回写db
        showDbData();
    }

    private void initDataConfig(Project project) {
        DataSetting instance = DataSetting.getInstance(null != project ? project : ProjectManager.getInstance().getDefaultProject());
        enumKnowFileConfig = instance.getEnumKnowFileConfig();
        enumKnowDataSourceConfig = instance.getEnumKnowDataSourceConfig();
        enumKnowOptionsConfig = instance.getEnumKnowOptionsConfig();
    }

    private void showFilePath() {
        filePathText.setText(enumKnowFileConfig.getEnumKnowFilePath());
    }

    private void showOptions() {
        String readMode = enumKnowOptionsConfig.getReadMode();
        if (EnumKnowConstants.READ_FILE.equals(readMode)) {
            fileRead.setSelected(true);
        } else if (EnumKnowConstants.READ_DB.equals(readMode)) {
            dbRead.setSelected(true);
        } else if (EnumKnowConstants.READ_ALL.equals(readMode)) {
            allRead.setSelected(true);
        }
    }

    private void showDbData() {
        hostTextField.setText(enumKnowDataSourceConfig.getHost());
        portTextField.setText(enumKnowDataSourceConfig.getPort());
        usernameTextField.setText(enumKnowDataSourceConfig.getUsername());
        passwordTextField.setText(enumKnowDataSourceConfig.getPassword());
        databaseTextField.setText(enumKnowDataSourceConfig.getDatabase());
        tableTextField.setText(enumKnowDataSourceConfig.getTableName());
        columnTextField.setText(enumKnowDataSourceConfig.getColumn());
        showInfoTextField.setText(enumKnowDataSourceConfig.getShowInfo());
        searchPairTextField.setText(enumKnowDataSourceConfig.getSearchPair());
    }

    public JComponent getComponent() {
        return main;
    }

    public JTextField getUrlTextField() {
        return filePathText;
    }

    public JPanel getMain() {
        return main;
    }

    public void setMain(JPanel main) {
        this.main = main;
    }

    public JPanel getFileSetting() {
        return fileSetting;
    }

    public void setFileSetting(JPanel fileSetting) {
        this.fileSetting = fileSetting;
    }

    public JLabel getSelectDesc() {
        return selectDesc;
    }

    public void setSelectDesc(JLabel selectDesc) {
        this.selectDesc = selectDesc;
    }

    public JTextField getFilePathText() {
        return filePathText;
    }

    public void setFilePathText(JTextField filePathText) {
        this.filePathText = filePathText;
    }

    public JButton getFileSelectButton() {
        return fileSelectButton;
    }

    public void setFileSelectButton(JButton fileSelectButton) {
        this.fileSelectButton = fileSelectButton;
    }

    public JRadioButton getDbRead() {
        return dbRead;
    }

    public void setDbRead(JRadioButton dbRead) {
        this.dbRead = dbRead;
    }

    public JRadioButton getAllRead() {
        return allRead;
    }

    public void setAllRead(JRadioButton allRead) {
        this.allRead = allRead;
    }

    public JRadioButton getFileRead() {
        return fileRead;
    }

    public void setFileRead(JRadioButton fileRead) {
        this.fileRead = fileRead;
    }

    public JTextField getHostTextField() {
        return hostTextField;
    }

    public void setHostTextField(JTextField hostTextField) {
        this.hostTextField = hostTextField;
    }

    public JTextField getPortTextField() {
        return portTextField;
    }

    public void setPortTextField(JTextField portTextField) {
        this.portTextField = portTextField;
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public JPasswordField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(JPasswordField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }

    public JTextField getDatabaseTextField() {
        return databaseTextField;
    }

    public void setDatabaseTextField(JTextField databaseTextField) {
        this.databaseTextField = databaseTextField;
    }

    public JTextField getTableTextField() {
        return tableTextField;
    }

    public void setTableTextField(JTextField tableTextField) {
        this.tableTextField = tableTextField;
    }

    public DataSetting.EnumKnowFileConfig getEnumKnowFileConfig() {
        return enumKnowFileConfig;
    }

    public void setEnumKnowFileConfig(DataSetting.EnumKnowFileConfig enumKnowFileConfig) {
        this.enumKnowFileConfig = enumKnowFileConfig;
    }

    public DataSetting.EnumKnowOptionsConfig getEnumKnowOptionsConfig() {
        return enumKnowOptionsConfig;
    }

    public void setEnumKnowOptionsConfig(DataSetting.EnumKnowOptionsConfig enumKnowOptionsConfig) {
        this.enumKnowOptionsConfig = enumKnowOptionsConfig;
    }

    public DataSetting.EnumKnowDataSourceConfig getEnumKnowDataSourceConfig() {
        return enumKnowDataSourceConfig;
    }

    public void setEnumKnowDataSourceConfig(DataSetting.EnumKnowDataSourceConfig enumKnowDataSourceConfig) {
        this.enumKnowDataSourceConfig = enumKnowDataSourceConfig;
    }

    public JTextField getColumnTextField() {
        return columnTextField;
    }

    public void setColumnTextField(JTextField columnTextField) {
        this.columnTextField = columnTextField;
    }

    public JButton getTestConn() {
        return testConn;
    }

    public void setTestConn(JButton testConn) {
        this.testConn = testConn;
    }

    public JTextField getShowInfoTextField() {
        return showInfoTextField;
    }

    public void setShowInfoTextField(JTextField showInfoTextField) {
        this.showInfoTextField = showInfoTextField;
    }

    public JTextField getSearchPairTextField() {
        return searchPairTextField;
    }

    public void setSearchPairTextField(JTextField searchPairTextField) {
        this.searchPairTextField = searchPairTextField;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
